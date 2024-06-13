/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: TCP Server
* Funcao...........: Classe do Servidor TCP.
*************************************************************** */

package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Server {
  private List<Socket> connectedClients = new ArrayList<>();
  private ServerSocket serverSocket;
  private App app;

  public TCPServer(int port) {
    super(port);
    app = App.getInstance();
  }

  @Override
  public void start() {
    try {
      serverSocket = new ServerSocket(port);
      System.out.println("> TCP Server está rodando na porta " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("> Cliente " + clientSocket.getInetAddress().getHostAddress() + " conectado.");
        new Thread(() -> {
          try {
            handleClient(clientSocket);
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      }
    } catch (IOException e) {
      connectedClients.clear();
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        System.out.println("> TCP Server encerrado.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleClient(Socket clientSocket) throws IOException {
    Client client = new Client(clientSocket);
    app.getClientController().createClient(client);
    
    while (true) {
      String data = "";
      try {
        data = (String) client.getInputStream().readObject();
        System.out.println("> Cliente " + client.getIp() + " enviou: " + data);
      } catch (Exception e) {
        break;
      }

      String response = readData(client.getServerIp(), data);

      if(response != null) {
        sendDataToAllClients(client.getIp(), response);
      }
    }

    System.out.println("> Cliente " + client.getIp() + " desconectado.");
    client.close();
    connectedClients.remove(clientSocket);
  }

  private String readData(String serverIp, String data) {
    String[] dataSplited = data.split("/");
    String type = dataSplited[0];
    String chatId = dataSplited[1];
    String user = dataSplited[2];
    String error = "";

    Chat chat = this.getApp().getChatController().getChatById(chatId);
    Message message = null;
    LocalDateTime localDateTime = LocalDateTime.now();

    switch (type) {
      case "send":
        String messageText = "";
        for (int i = 3; i < dataSplited.length; i++) {
          messageText += dataSplited[i] + " ";
        }
        messageText = messageText.trim();
        message = new Message(chatId, user, messageText, localDateTime);
        this.getApp().getMessageController().createMessage(message);
        System.out.println("> " + user + " enviou '" + messageText + "' para " + chatId);
        break;
      case "join":
        if (chat == null) {
          System.out.println("> " + user + " criou o grupo " + chatId);
          this.getApp().getChatController().createChat(new Chat(chatId, "Redes", null));
        } else {
          System.out.println("> " + user + " entrou no grupo " + chatId);

          Chat newChat = new Chat(chatId, "Grupo", null);
          this.getApp().getChatController().createChat(newChat);

          ChatUser chatUser = new ChatUser(user, chatId);
          this.getApp().getChatUserController().createChatUser(chatUser);

          message = new Message(chatId, serverIp, user + " entrou no grupo", localDateTime);
          this.getApp().getMessageController().createMessage(message);
        }
        break;
      case "leave":
        if (chat != null) {
          System.out.println("> " + user + " saiu do grupo " + chatId);

          this.getApp().getChatUserController().deleteChatUser(chatId, user);

          message = new Message(chatId, serverIp, user + " saiu do grupo", localDateTime);
          this.getApp().getMessageController().createMessage(message);
        }
        break;
      default:
        error = "error/Tipo de mensagem inválida";
        return error;
    }

    if (message != null) {
      String responseData = createDataResponse(message);
      return responseData;
    }

    return null;
  }

  private String createDataResponse(Message message) {
    // send/{chatId}/{userIp}/{messageText}
    String response = "send/" + message.getChatId() + "/" + message.getUserIp() + "/" + message.getText();

    return response;
  }

  @Override
  public void sendDataToAllClients(String clientIp, String data) {
    List<Client> clients = app.getClientController().getAllClients();

    for (Client client : clients) {
      if (!client.getIp().equals(clientIp) && client.getSocket().isConnected()) {
        System.out.println("> (" + data + ") para " + client.getIp());
        try {
          client.getOutputStream().writeObject(data);
          client.getOutputStream().flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
