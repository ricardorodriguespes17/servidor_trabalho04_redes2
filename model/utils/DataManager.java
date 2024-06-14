package model.utils;

import java.time.LocalDateTime;

import model.App;
import model.Chat;
import model.ChatUser;
import model.Message;
import model.server.Server;

public class DataManager {
  private static App app = App.getInstance();

  private static String returnSend(String chatId, String senderIp, String message) {
    return "send/" + chatId + "/" + senderIp + "/" + message;
  }

  private static String returnChat(String chatId, String chatName) {
    return "chat/" + chatId + "/" + chatName;
  }

  public static String returnError(String reason) {
    return "error/" + reason;
  }

  public static void send(Server server, String chatId, String userIp, String messageText) {
    Message message = new Message(chatId, userIp, messageText, LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " enviou '" + messageText + "' para " + chatId);

    String response = returnSend(chatId, userIp, message.getText());
    server.sendDataToGroupClients(chatId, userIp, response);
  }

  public static void join(Server server, String serverIp, String chatId, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if (chat == null) {
      String error = returnError("Não há grupo associado a esse código");
      server.sendDataToSingleClient(userIp, error);
      return;
    }

    ChatUser chatUser = new ChatUser(userIp, chatId);
    app.getChatUserController().createChatUser(chatUser);

    Message message = new Message(chatId, serverIp, userIp + " entrou no grupo", LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " entrou no grupo " + chatId);

    String membersResponse = returnSend(chatId, serverIp, message.getText());
    server.sendDataToGroupClients(chatId, userIp, membersResponse);

    String userResponse = returnChat(chatId, chat.getName());
    server.sendDataToSingleClient(userIp, userResponse);
  }

  public static void create(Server server, String chatId, String chatName, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if (chat != null) {
      String error = returnError("Não foi possível criar o grupo");
      server.sendDataToSingleClient(userIp, error);
      return;
    }

    app.getChatController().createChat(new Chat(chatId, chatName, null));

    ChatUser chatUser = new ChatUser(userIp, chatId);
    app.getChatUserController().createChatUser(chatUser);

    System.out.println("> " + userIp + " criou o grupo " + chatId + " vulgo " + chatName);
  }

  public static void leave(Server server, String serverIp, String chatId, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if (chat == null) {
      String error = returnError("Não há grupo associado a esse código");
      server.sendDataToSingleClient(userIp, error);
      return;
    }

    app.getChatUserController().deleteChatUser(chatId, userIp);

    Message message = new Message(chatId, serverIp, userIp + " saiu do grupo", LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " saiu do grupo " + chatId);

    String response = returnSend(chatId, serverIp, message.getText());
    server.sendDataToGroupClients(chatId, userIp, response);
  }
}
