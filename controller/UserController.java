/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: UserController
* Funcao...........: Classe controladora do CRUD do User.
*************************************************************** */

package controller;

import java.util.List;

import dao.ChatRepository;
import model.Chat;

public class UserController {
  private ChatRepository chatRepository = new ChatRepository();

  public void createChat(Chat chat) {
    chatRepository.createChat(chat);
  }

  public List<Chat> getAllChats() {
    return chatRepository.getAllChats();
  }

  public Chat getChatById(String id) {
    return chatRepository.getChatById(id);
  }
}