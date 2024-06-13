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
import java.util.ArrayList;
import java.util.List;

import model.utils.DataManager;

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
    this.getApp().getClientController().createClient(client);

    while (true) {
      String data = "";
      try {
        data = (String) client.getInputStream().readObject();
        System.out.println("> Cliente " + client.getIp() + " enviou: " + data);
      } catch (Exception e) {
        break;
      }

      processData(client.getServerIp(), data);
    }

    System.out.println("> Cliente " + client.getIp() + " desconectado.");
    client.close();
    connectedClients.remove(clientSocket);
  }

  private void processData(String serverIp, String data) {
    String[] dataSplited = data.split("/");
    String type = dataSplited[0];

    switch (type) {
      case "send":
        DataManager.send(dataSplited[1], dataSplited[2], dataSplited[3]);
        break;
      case "join":
        DataManager.join(dataSplited[1], dataSplited[2]);
        break;
      case "leave":
        DataManager.leave(dataSplited[1], dataSplited[2]);
        break;
      case "create":
        DataManager.create(dataSplited[1], dataSplited[2], dataSplited[3]);
        break;
      default:
        DataManager.returnError("Tipo de entrada inválida");
        break;
    }
  }

  public void sendDataToGroupClients(String chatId, String senderIp, String data) {
    List<Client> clients = this.getApp().getClientController().getAllClients();

    for (Client client : clients) {
      ChatUser chatUser = this.getApp().getChatUserController().getChatUserByIds(chatId, client.getIp());

      if (chatUser != null && !client.getIp().equals(senderIp)) {
        sendDataToClient(client, data);
      }
    }
  }

  public void sendDataToClient(String clientIp, String data) {
    List<Client> clients = this.getApp().getClientController().getAllClients();

    for (Client client : clients) {
      if (!client.getIp().equals(clientIp)) {
        sendDataToClient(client, data);
      }
    }
  }

  public void sendDataToClient(Client client, String data) {
    boolean isConnected = client.getSocket().isConnected();
    boolean isClosed = client.getSocket().isClosed();

    if (isConnected && !isClosed) {
      try {
        client.getOutputStream().writeObject(data);
        client.getOutputStream().flush();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("> (" + data + ") para " + client.getIp());
    }
  }
}
