package com.arijit.chatbotresume.Model;

public class ChatMessage {
    private String text;

    public ChatMessage() {}

    public ChatMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}