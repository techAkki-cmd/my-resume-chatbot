package com.arijit.chatbotresume.Service;

import com.arijit.chatbotresume.Model.ChatSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores ChatSession objects. In a real app, you can persist them in DB or Redis.
 */
@Service
public class SessionService {

    private final Map<String, ChatSession> sessions = new ConcurrentHashMap<>();

    public ChatSession getOrCreateSession(String sessionId) {
        return sessions.computeIfAbsent(sessionId, ChatSession::new);
    }

    public void clearSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
