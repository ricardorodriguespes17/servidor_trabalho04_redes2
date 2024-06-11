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
