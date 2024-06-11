/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: ChatUserController
* Funcao...........: Classe controladora do CRUD do ChatUser.
*************************************************************** */

package controller;

import java.util.List;

import dao.ChatUserRepository;
import model.ChatUser;

public class ChatUserController {
  private ChatUserRepository chatUserRepository = new ChatUserRepository();

  public void createChatUser(ChatUser chatUser) {
    chatUserRepository.createChatUser(chatUser);
  }

  public List<ChatUser> getAllChatUsers() {
    return chatUserRepository.getAllChatUsers();
  }

  public List<ChatUser> getChatUsersByChat(String chatId) {
    return chatUserRepository.getChatUsersByChat(chatId);
  }

  public List<ChatUser> getChatUsersByUser(String userIp) {
    return chatUserRepository.getChatUsersByChat(userIp);
  }

  public ChatUser getChatUserByIds(String chatId, String userIp) {
    return chatUserRepository.getChatUserByIds(chatId, userIp);
  }

  public void deleteChatUser(String chatId, String userIp) {
    chatUserRepository.deleteChatUser(chatId, userIp);
  }
}
