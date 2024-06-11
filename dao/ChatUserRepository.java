/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: ChatUserRepository
* Funcao...........: Classe do repositorio da relação Chat-Usuário.
*************************************************************** */

package dao;

import java.util.ArrayList;
import java.util.List;

import model.ChatUser;

public class ChatUserRepository {
  private static final List<ChatUser> chatUsers = new ArrayList<>();

  public void createChatUser(ChatUser message) {
    chatUsers.add(message);
  }

  public List<ChatUser> getAllChatUsers() {
    return new ArrayList<>(chatUsers);
  }

  public ChatUser getChatUserByIds(String chatId, String userIp) {
    for (ChatUser chatUser : chatUsers) {
      boolean sameChatId = chatUser.getChatId().equals(chatId);
      boolean sameUserIp = chatUser.getUserIp().equals(userIp);
      if (sameChatId && sameUserIp) {
        return chatUser;
      }
    }

    return null;
  }

  public List<ChatUser> getChatUsersByUser(String userIp) {
    List<ChatUser> result = new ArrayList<>();

    for (ChatUser chatUser : chatUsers) {
      boolean sameUserIp = chatUser.getUserIp().equals(userIp);
      if (sameUserIp) {
        result.add(chatUser);
      }
    }

    return result;
  }

  public List<ChatUser> getChatUsersByChat(String chatId) {
    List<ChatUser> result = new ArrayList<>();

    for (ChatUser chatUser : chatUsers) {
      boolean sameChatId = chatUser.getChatId().equals(chatId);
      if (sameChatId) {
        result.add(chatUser);
      }
    }

    return result;
  }

  public void deleteChatUser(String chatId, String userIp) {
    List<ChatUser> newChatUsers = new ArrayList<>(chatUsers);

    for (ChatUser chatUser : newChatUsers) {
      boolean sameChatId = chatUser.getChatId().equals(chatId);
      boolean sameUserIp = chatUser.getUserIp().equals(userIp);
      if (sameChatId && sameUserIp) {
        chatUsers.remove(chatUser);
      }
    }
  }
}
