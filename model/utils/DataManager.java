package model.utils;

import java.time.LocalDateTime;

import model.App;
import model.Chat;
import model.ChatUser;
import model.Message;

public class DataManager {
  private static App app = App.getInstance();

  private static String returnSend(String chatId, String userIp, String message) {
    return "send/" + chatId + "/" + userIp + "/" + message;
  }

  private static String returnChat(String chatId, String chatName) {
    return "chat/" + chatId + "/" + chatName;
  }

  public static String returnError(String reason) {
    return "error/" + reason;
  }

  public static String send(String chatId, String userIp, String messageText) {
    Message message = new Message(chatId, userIp, messageText, LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " enviou '" + messageText + "' para " + chatId);
    
    return returnSend(chatId, userIp, message.getText());
  }

  public static String join(String chatId, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if(chat == null)
      return returnError("Não há grupo associado a esse código");

    ChatUser chatUser = new ChatUser(userIp, chatId);
    app.getChatUserController().createChatUser(chatUser);

    Message message = new Message(chatId, App.SERVER_IP, userIp + " entrou no grupo", LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " entrou no grupo " + chatId);
    
    return returnSend(chatId, userIp, message.getText());
  }

  public static String create(String chatId, String chatName, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if(chat != null)
      return returnError("Não foi possível criar o grupo");

    app.getChatController().createChat(new Chat(chatId, chatName, null));

    System.out.println("> " + userIp + " criou o grupo " + chatId + " vulgo " + chatName);
    
    return returnChat(chatId, chatName);
  }

  public static String leave(String chatId, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if(chat == null)
      return returnError("Não há grupo associado a esse código");

    app.getChatUserController().deleteChatUser(chatId, userIp);

    Message message = new Message(chatId, App.SERVER_IP, userIp + " saiu do grupo", LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " saiu do grupo " + chatId);
    
    return returnSend(chatId, userIp, message.getText());
  }
}
