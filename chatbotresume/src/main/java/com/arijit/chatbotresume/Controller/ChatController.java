package com.arijit.chatbotresume.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.arijit.chatbotresume.Model.ChatMessage;
import com.arijit.chatbotresume.Model.ChatSession;
import com.arijit.chatbotresume.Model.Experience;
import com.arijit.chatbotresume.Model.Education;
import com.arijit.chatbotresume.Model.Skills;
import com.arijit.chatbotresume.Model.Projects;
import com.arijit.chatbotresume.Model.ExtraCurricular;
import com.arijit.chatbotresume.Model.Certifications;
import com.arijit.chatbotresume.Model.Honors;

import com.arijit.chatbotresume.Service.AIService;
import com.arijit.chatbotresume.Service.SessionService;
import com.arijit.chatbotresume.Service.ExperienceService;
import com.arijit.chatbotresume.Service.EducationService;
import com.arijit.chatbotresume.Service.SkillsService;
import com.arijit.chatbotresume.Service.ProjectsService;
import com.arijit.chatbotresume.Service.ExtraCurricularService;
import com.arijit.chatbotresume.Service.CertificationsService;
import com.arijit.chatbotresume.Service.HonorsService;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * ChatController ensuring:
 *  1) Immediate greeting before any user message (via /greeting endpoint).
 *  2) "start over" only returns "I've cleared our conversation..." with no extra greeting.
 *  3) Full multi-turn memory, session-based conversation, visited flags, etc.
 *  4) AI JSON is parsed server-side so the frontend gets plain text.
 */
@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = "https://arijit-resume-bot.netlify.app")
public class ChatController {

    private final AIService aiService;
    private final SessionService sessionService;

    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final SkillsService skillsService;
    private final ProjectsService projectsService;
    private final ExtraCurricularService extraCurricularService;
    private final CertificationsService certificationsService;
    private final HonorsService honorsService;

    private final ObjectMapper mapper = new ObjectMapper();

    public ChatController(
            AIService aiService,
            SessionService sessionService,
            ExperienceService experienceService,
            EducationService educationService,
            SkillsService skillsService,
            ProjectsService projectsService,
            ExtraCurricularService extraCurricularService,
            CertificationsService certificationsService,
            HonorsService honorsService
    ) {
        this.aiService = aiService;
        this.sessionService = sessionService;
        this.experienceService = experienceService;
        this.educationService = educationService;
        this.skillsService = skillsService;
        this.projectsService = projectsService;
        this.extraCurricularService = extraCurricularService;
        this.certificationsService = certificationsService;
        this.honorsService = honorsService;
    }

    /**
     * 1) The frontend calls this right when the user arrives.
     * 2) If the session is truly new (no conversation), we greet. Otherwise, return "" (no greeting).
     */
    @GetMapping("/greeting")
    public ResponseEntity<String> getGreeting(@RequestParam String sessionId) {
        ChatSession chatSession = sessionService.getOrCreateSession(sessionId);

        if (chatSession.getConversationHistory().isEmpty()) {
            String greeting = "Hello! Welcome to my interactive resume chatbot. How can I help you today?";
            chatSession.addToHistory("Bot: " + greeting);
            return ResponseEntity.ok(greeting);
        } else {
            // Already has a conversation, so no new greeting
            return ResponseEntity.ok("");
        }
    }

    /**
     * Handles all user messages. If "start over," we only return "I've cleared our conversation..."
     * with no extra greeting. Otherwise, we do the usual flow, referencing prior session state.
     */
    @PostMapping("/message")
    public ResponseEntity<String> handleMessage(
            @RequestBody ChatMessage message,
            @RequestParam String sessionId
    ) {
        ChatSession chatSession = sessionService.getOrCreateSession(sessionId);
        String userText = message.getText().trim();

        // Check for "start over"
        if (userText.equalsIgnoreCase("start over") || userText.equalsIgnoreCase("restart")) {
            sessionService.clearSession(sessionId);
            return ResponseEntity.ok("I've cleared our conversation. Feel free to begin again!");
        }

        // Optionally: "revisit sections" resets visited flags
        if (userText.toLowerCase().contains("revisit sections")) {
            chatSession.setHasSeenExperience(false);
            chatSession.setHasSeenProjects(false);
            chatSession.setHasSeenSkills(false);
            chatSession.setHasSeenCertifications(false);
            chatSession.setHasSeenHonors(false);
            chatSession.setHasSeenEducation(false);
            chatSession.setHasSeenExtraCurricular(false);
        }

        // Record user's message in session
        chatSession.addToHistory("User: " + userText);

        // Merge all resume data
        String resumeData = buildAllResumeData(
                experienceService.getAllExperiences(),
                educationService.getAllEducation(),
                skillsService.getAllSkills(),
                projectsService.getAllProjects(),
                extraCurricularService.getAllActivities(),
                certificationsService.getAllCertifications(),
                honorsService.getAllHonors()
        );

        // Build final prompt
        String finalPrompt = """
            You are my personal resume chatbot.you will give responses to the recruiter on behalf of me,the response should engage the recruiter and should be based on the previous converstion as it should engage the recruiter more and adapt to his requirements as discussed or shared in the previous questionsa and convo also the length of the responses should be according to the question and exphasis of the recruiter(not more than 7-8 lines) and the responses can contain bullet points, paragarph or anything that suits the response better but not look like hotch potch.

            Conversation so far:
            %s

            The user just asked: "%s"

            Full resume data:
            %s
            """.formatted(
                String.join("\n", chatSession.getConversationHistory()),
                userText,
                resumeData
        );

        // Call AI
        String aiJson = aiService.getAIResponse(finalPrompt);

        // Parse to plain text
        String plainResponse = parseGeminiJson(aiJson);

        // Add bot answer to session
        chatSession.addToHistory("Bot: " + plainResponse);

        // Return only the plain text to the frontend
        return ResponseEntity.ok(plainResponse);
    }

    /**
     * Builds a single text block from all your resume tables (Experience, etc.).
     * Adjust field names to match your actual model classes.
     */
    private String buildAllResumeData(
            List<Experience> experiences,
            List<Education> educationList,
            List<Skills> skillsList,
            List<Projects> projectsList,
            List<ExtraCurricular> extraList,
            List<Certifications> certList,
            List<Honors> honorsList
    ) {
        StringBuilder sb = new StringBuilder();

        // EXPERIENCE
        sb.append("===== EXPERIENCE =====\n");
        if (experiences.isEmpty()) {
            sb.append("No experience data found.\n\n");
        } else {
            for (Experience exp : experiences) {
                sb.append("Title: ").append(exp.getTitle())
                        .append("\nCompany: ").append(exp.getCompany())
                        .append("\nLocation: ").append(exp.getLocation())
                        .append("\nStart Date: ").append(exp.getStartDate())
                        .append("\nEnd Date: ").append(exp.getEndDate())
                        .append("\nResponsibilities: ").append(exp.getResponsibilities())
                        .append("\n\n");
            }
        }

        // EDUCATION
        sb.append("===== EDUCATION =====\n");
        if (educationList.isEmpty()) {
            sb.append("No education data found.\n\n");
        } else {
            for (Education edu : educationList) {
                sb.append("Degree: ").append(edu.getDegree())
                        .append("\nInstitute: ").append(edu.getInstitute())
                        .append("\nStart Year: ").append(edu.getStartYear())
                        .append("\nExpected Graduation Year: ").append(edu.getExpectedGraduationYear())
                        .append("\nRelevant Coursework: ").append(edu.getRelevantCoursework())
                        .append("\n\n");
            }
        }

        // SKILLS
        sb.append("===== SKILLS =====\n");
        if (skillsList.isEmpty()) {
            sb.append("No skills data found.\n\n");
        } else {
            for (Skills skill : skillsList) {
                sb.append("Skill Name: ").append(skill.getSkillName())
                        .append("\nSkill Type: ").append(skill.getSkillType())
                        .append("\n\n");
            }
        }

        // PROJECTS
        sb.append("===== PROJECTS =====\n");
        if (projectsList.isEmpty()) {
            sb.append("No projects data found.\n\n");
        } else {
            for (Projects proj : projectsList) {
                sb.append("Project Name: ").append(proj.getProjectName())
                        .append("\nDescription: ").append(proj.getDescription())
                        .append("\nTechnologies Used: ").append(proj.getTechnologiesUsed())
                        .append("\nProject Link: ").append(proj.getProjectLink())
                        .append("\n\n");
            }
        }

        // EXTRA-CURRICULAR
        sb.append("===== EXTRA-CURRICULAR =====\n");
        if (extraList.isEmpty()) {
            sb.append("No extracurricular data found.\n\n");
        } else {
            for (ExtraCurricular xc : extraList) {
                sb.append("Position: ").append(xc.getPosition())
                        .append("\nOrganization: ").append(xc.getOrganization())
                        .append("\nResponsibilities: ").append(xc.getResponsibilities())
                        .append("\n\n");
            }
        }

        // CERTIFICATIONS
        sb.append("===== CERTIFICATIONS =====\n");
        if (certList.isEmpty()) {
            sb.append("No certifications data found.\n\n");
        } else {
            for (Certifications cert : certList) {
                sb.append("Certification Name: ").append(cert.getCertificationName())
                        .append("\nOrganization: ").append(cert.getOrganization())
                        .append("\nCompletion Status: ").append(cert.getCompletionStatus())
                        .append("\nCompletion Date: ").append(cert.getCompletionDate())
                        .append("\n\n");
            }
        }

        // HONORS
        sb.append("===== HONORS =====\n");
        if (honorsList.isEmpty()) {
            sb.append("No honors data found.\n\n");
        } else {
            for (Honors honor : honorsList) {
                sb.append("Title: ").append(honor.getTitle())
                        .append("\nDescription: ").append(honor.getDescription())
                        .append("\n\n");
            }
        }

        return sb.toString();
    }

    /**
     * Extracts text from Gemini JSON so the frontend sees plain text only.
     * If the structure isn't recognized, we fallback to raw JSON.
     */
    private String parseGeminiJson(String json) {
        try {
            JsonNode root = mapper.readTree(json);
            if (root.has("candidates")) {
                JsonNode c0 = root.get("candidates").get(0);
                if (c0.has("content")) {
                    JsonNode content = c0.get("content");
                    if (content.has("parts")) {
                        JsonNode parts = content.get("parts");
                        if (parts.isArray() && parts.size() > 0) {
                            return parts.get(0).get("text").asText();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json; // fallback if parse fails
    }
}
