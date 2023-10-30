package com.notflix.servicenotifications.socket;

public class SocketMessage {
  String message;
  String data;

  public SocketMessage(String message, String data) {
    this.message = message;
    this.data = data;
  }

  public SocketMessage() { }

  @Override
  public String toString() {
    return "SocketMessage [data=" + data + ", message=" + message + "]";
  }

  public String getMessage() { return message; }
  public String setMessage(String message) { return this.message = message; }

  public String getData() { return data; }
  public String setData(String data) { return this.data = data; }
}
