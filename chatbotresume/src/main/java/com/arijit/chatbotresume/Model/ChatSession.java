package com.arijit.chatbotresume.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single conversation context for the resume chatbot.
 * Stores sessionId, conversation history, and any session-specific flags.
 * You can expand it to track more data (e.g., user preferences) as needed.
 */
public class ChatSession {

    // A unique string that lets you associate requests with this specific session
    private final String sessionId;

    // Stores the messages exchanged in this conversation. For multi-turn memory,
    // the chatbot can reference these previous user or bot messages.
    private final List<String> conversationHistory = new ArrayList<>();

    // Example of flags that track whether the user has already accessed a particular section.
    // Adjust or extend these booleans as you like for your resume logic.
    private boolean hasSeenExperience;
    private boolean hasSeenProjects;
    private boolean hasSeenSkills;
    private boolean hasSeenCertifications;
    private boolean hasSeenHonors;
    private boolean hasSeenEducation;
    private boolean hasSeenExtraCurricular;

    /**
     * Creates a new ChatSession with the given session ID.
     *
     * @param sessionId A unique identifier for this chat session.
     */
    public ChatSession(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return The unique chat session ID.
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @return A list of all messages (user or bot) exchanged so far in the session.
     */
    public List<String> getConversationHistory() {
        return conversationHistory;
    }

    /**
     * Adds a new message (user or bot) to the conversation history.
     *
     * @param message The text of the message being appended.
     */
    public void addToHistory(String message) {
        conversationHistory.add(message);
    }

    public boolean isHasSeenExperience() {
        return hasSeenExperience;
    }

    public void setHasSeenExperience(boolean hasSeenExperience) {
        this.hasSeenExperience = hasSeenExperience;
    }

    public boolean isHasSeenProjects() {
        return hasSeenProjects;
    }

    public void setHasSeenProjects(boolean hasSeenProjects) {
        this.hasSeenProjects = hasSeenProjects;
    }

    public boolean isHasSeenSkills() {
        return hasSeenSkills;
    }

    public void setHasSeenSkills(boolean hasSeenSkills) {
        this.hasSeenSkills = hasSeenSkills;
    }

    public boolean isHasSeenCertifications() {
        return hasSeenCertifications;
    }

    public void setHasSeenCertifications(boolean hasSeenCertifications) {
        this.hasSeenCertifications = hasSeenCertifications;
    }

    public boolean isHasSeenHonors() {
        return hasSeenHonors;
    }

    public void setHasSeenHonors(boolean hasSeenHonors) {
        this.hasSeenHonors = hasSeenHonors;
    }

    public boolean isHasSeenEducation() {
        return hasSeenEducation;
    }

    public void setHasSeenEducation(boolean hasSeenEducation) {
        this.hasSeenEducation = hasSeenEducation;
    }

    public boolean isHasSeenExtraCurricular() {
        return hasSeenExtraCurricular;
    }

    public void setHasSeenExtraCurricular(boolean hasSeenExtraCurricular) {
        this.hasSeenExtraCurricular = hasSeenExtraCurricular;
    }
}
