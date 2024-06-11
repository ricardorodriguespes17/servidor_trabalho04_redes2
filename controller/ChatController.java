/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: ChatController
* Funcao...........: Classe controladora do CRUD do Chat.
*************************************************************** */

package controller;

import java.util.List;

import dao.ChatRepository;
import model.Chat;

public class ChatController {
  private ChatRepository chatRepository = new ChatRepository();

  public void createChat(Chat chatUser) {
    chatRepository.createChat(chatUser);
  }

  public List<Chat> getAllChats() {
    return chatRepository.getAllChats();
  }

  public Chat getChatById(String chatId) {
    return chatRepository.getChatById(chatId);
  }
}
