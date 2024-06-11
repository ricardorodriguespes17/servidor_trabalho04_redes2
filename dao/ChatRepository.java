/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: ChatRepository
* Funcao...........: Classe do repositorio dos dados de Chat.
*************************************************************** */

package dao;

import java.util.ArrayList;
import java.util.List;

import model.Chat;

public class ChatRepository {
  private static final List<Chat> chats = new ArrayList<>();

  public void createChat(Chat chat) {
    chats.add(chat);
  }

  public List<Chat> getAllChats() {
    return new ArrayList<>(chats);
  }

  public Chat getChatById(String id) {
    for (Chat chat : chats) {
      if (chat.getId().equals(id)) {
        return chat;
      }
    }

    return null;
  }
}
