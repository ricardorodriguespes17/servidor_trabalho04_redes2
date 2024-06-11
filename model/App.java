package model;

import controller.ChatController;
import controller.ChatUserController;
import controller.MessageController;
import controller.UserController;

public class App {
  private static App instance;
  private UserController userController = new UserController();
  private ChatController chatController = new ChatController();
  private ChatUserController chatUserController = new ChatUserController();
  private MessageController messageController = new MessageController();

  public static App getInstance() {
    if (instance == null) {
      instance = new App();
    }
    return instance;
  }

  public static void setInstance(App instance) {
    App.instance = instance;
  }

  public UserController getUserController() {
    return userController;
  }

  public void setUserController(UserController userController) {
    this.userController = userController;
  }

  public ChatController getChatController() {
    return chatController;
  }

  public void setChatController(ChatController chatController) {
    this.chatController = chatController;
  }

  public ChatUserController getChatUserController() {
    return chatUserController;
  }

  public void setChatUserController(ChatUserController chatUserController) {
    this.chatUserController = chatUserController;
  }

  public MessageController getMessageController() {
    return messageController;
  }

  public void setMessageController(MessageController messageController) {
    this.messageController = messageController;
  }

}
