package com.notflix.servicechat.controller;



import java.util.Date;

import org.apache.hc.core5.http.Message;

public class MessageType {

    public String message;
    public String chatId;
    public String senderEmail;
    public String senderName;
    public Date timestamp;


    public MessageType(String message, String chatId, String senderEmail, String senderName, Date timestamp) {
        this.message = message;
        this.chatId = chatId;
        this.senderEmail = senderEmail;
        this.senderName = senderName;
        this.timestamp = timestamp;
    }
    public MessageType() {
    }
    // to string
    @Override
    public String toString() {
        return "MessageType [chatId=" + chatId + ", message=" + message + ", senderEmail=" + senderEmail
                + ", senderName=" + senderName + ", timestamp=" + timestamp + "]";
    }
}
