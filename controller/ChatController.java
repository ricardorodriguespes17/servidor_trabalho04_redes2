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
