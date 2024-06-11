/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: TCP Server
* Funcao...........: Classe do Servidor TCP.
*************************************************************** */

package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Server {
  private List<Socket> connectedClients = new ArrayList<>();
  private ServerSocket serverSocket;

  public TCPServer(int port) {
    super(port);
  }

  @Override
  public void start() {
    try {
      serverSocket = new ServerSocket(port);
      App.SERVER_IP = serverSocket.getInetAddress().getHostAddress();
      System.out.println("> TCP Server está rodando na porta " + port);

      while (true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("> Cliente " + clientSocket.getInetAddress().getHostAddress() + " conectado.");
        new Thread(() -> {
          handleClient(clientSocket);
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

  private void handleClient(Socket clientSocket) {
    ObjectInputStream input = null;
    ObjectOutputStream outputStream = null;
    try {
      input = new ObjectInputStream(clientSocket.getInputStream());
      outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      connectedClients.add(clientSocket);
      while (true) {
        try {
          String data = (String) input.readObject();
          System.out.println("> Cliente " + clientSocket.getInetAddress().getHostAddress() + " enviou: " + data);
          readData(clientSocket, data);
        } catch (Exception e) {
          connectedClients.remove(clientSocket);
          System.out.println("> Cliente " + clientSocket.getInetAddress().getHostAddress() + " desconectado.");
          break;
        }
      }
    } catch (IOException e) {
      System.out.println("> Erro ao lidar com o cliente: " + e.getMessage());
    } finally {
      // Fecha os fluxos de entrada e saída
      if (input != null) {
        try {
          input.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      // Remove o fluxo de saída associado ao cliente desconectado da lista
      connectedClients.remove(clientSocket);
    }
  }

  private void readData(Socket clientSocket, String data) throws Exception {
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

          message = new Message(chatId, App.SERVER_IP, user + " entrou no grupo", localDateTime);
          this.getApp().getMessageController().createMessage(message);
        }
        break;
      case "leave":
        if (chat != null) {
          System.out.println("> " + user + " saiu do grupo " + chatId);

          this.getApp().getChatUserController().deleteChatUser(chatId, user);

          message = new Message(chatId, App.SERVER_IP, user + " saiu do grupo", localDateTime);
          this.getApp().getMessageController().createMessage(message);
        }
        break;
      default:
        error = "error/Tipo de mensagem inválida";
        sendDataToAllClients(clientSocket, error);
        return;
    }

    if (message != null) {
      String responseData = createDataResponse(message);
      sendDataToAllClients(clientSocket, responseData);
    }
  }

  private String createDataResponse(Message message) {
    // send/{chatId}/{userIp}/{messageText}
    String response = "send/" + message.getChatId() + "/" + message.getUserIp() + "/" + message.getText();

    return response;
  }

  @Override
  public void sendDataToAllClients(Socket sender, String message) {
    for (Socket socket : connectedClients) {
      if (!socket.equals(sender)) {
        try {
          ObjectOutputStream clientOutputStream = new ObjectOutputStream(socket.getOutputStream());
          clientOutputStream.writeObject(message);
          clientOutputStream.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
