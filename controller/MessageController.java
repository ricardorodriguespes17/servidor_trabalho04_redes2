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
