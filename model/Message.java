package model;

import java.time.LocalDateTime;

public class Message {
  private String chatId;
  private String userIp;
  private String text;
  private LocalDateTime dateTime;

  public Message(String chatId, String userIp, String text, LocalDateTime dateTime) {
    this.chatId = chatId;
    this.userIp = userIp;
    this.text = text;
    this.dateTime = dateTime;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

}
