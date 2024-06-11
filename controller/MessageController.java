/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: MessageController
* Funcao...........: Classe controladora do CRUD do Message.
*************************************************************** */

package controller;

import java.util.List;

import dao.MessageRepository;
import model.Message;

public class MessageController {
  private MessageRepository messageRepository = new MessageRepository();

  public void createMessage(Message message) {
    messageRepository.createMessage(message);
  }

  public List<Message> getAllMessages() {
    return messageRepository.getAllMessages();
  }

  public List<Message> getMessagesByChatId(String chatId) {
    return messageRepository.getMessagesByChatId(chatId);
  }
}
