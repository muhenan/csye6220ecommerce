package com.henanmu.csye6220ecommerce.util;

public class MessageModel {
    private String message;

    private MessageModel(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static MessageModel create(String message) {
        return new MessageModel(message);
    }
}

