/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: ChatUser
* Funcao...........: Classe da relação Chat-Usuário.
*************************************************************** */

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
