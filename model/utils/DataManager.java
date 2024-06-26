/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 14/06/2024
* Ultima alteracao.: 26/06/2024
* Nome.............: DataManager
* Funcao...........: Classe gerencia as apdus do servidor.
*************************************************************** */

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

  public static void send(Server server, String chatId, String userIp, String messageText) {
    Message message = new Message(chatId, userIp, messageText, LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " enviou '" + messageText + "' para " + chatId);

    String response = returnSend(chatId, userIp, message.getText());
    server.sendDataToGroupClients(chatId, userIp, response);
  }

  public static void join(Server server, String serverIp, String chatName, String userIp) {
    Chat chat = app.getChatController().getChatById(chatName);
    ChatUser chatUser = new ChatUser(userIp, chatName);
    app.getChatUserController().createChatUser(chatUser);

    if (chat == null) {
      app.getChatController().createChat(new Chat(chatName));

      System.out.println("> " + userIp + " criou o grupo " + chatName);
      return;
    }

    Message message = new Message(chatName, serverIp, userIp + " entrou no grupo", LocalDateTime.now());
    app.getMessageController().createMessage(message);

    System.out.println("> " + userIp + " entrou no grupo " + chatName);

    String membersResponse = returnSend(chatName, serverIp, message.getText());
    server.sendDataToGroupClients(chatName, userIp, membersResponse);
  }

  public static void leave(Server server, String serverIp, String chatId, String userIp) {
    Chat chat = app.getChatController().getChatById(chatId);

    if (chat == null) {
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
