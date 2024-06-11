package model;

public class ChatUser {
  private String userIp;
  private String chatId;

  public ChatUser(String userIp, String chatId) {
    this.userIp = userIp;
    this.chatId = chatId;
  }

  public String getUserIp() {
    return userIp;
  }

  public void setUserIp(String userIp) {
    this.userIp = userIp;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }
}
