package com.notflix.servicenotifications.messages;

public class NotificationMessage {
  private String[] users;
  private String message;
  private String data;

  public NotificationMessage(String[] users, String message, String data) {
    this.data = data;
    this.users = users;
    this.message = message;
  }

  public NotificationMessage() { }

  public String[] getUsers() { return users; }
  public void setUsers(String[] users) { this.users = users; }

  public String getData() { return data; }
  public void setData(String data) { this.data = data; }

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }
}
