package controller;

import java.util.List;

import dao.ChatUserRepository;
import model.ChatUser;

public class ChatController {
  private ChatUserRepository chatUserRepository = new ChatUserRepository();

  public void createChat(ChatUser chatUser) {
    chatUserRepository.createChatUser(chatUser);
  }

  public List<ChatUser> getAllChats() {
    return chatUserRepository.getAllChatUsers();
  }
}
