/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: MessageRepository
* Funcao...........: Classe do repositorio dos dos de Mesnsagens.
*************************************************************** */

package dao;

import java.util.ArrayList;
import java.util.List;

import model.Message;

public class MessageRepository {
  private static final List<Message> messages = new ArrayList<>();

  public void createMessage(Message message) {
    messages.add(message);
  }

  public List<Message> getAllMessages() {
    return new ArrayList<>(messages);
  }

  public List<Message> getMessagesByChatId(String chatId) {
    List<Message> result = new ArrayList<>();

    for (Message message : messages) {
      if (message.getChatId().equals(chatId)) {
        result.add(message);
      }
    }

    return result;
  }
}
