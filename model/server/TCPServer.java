/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: TCP Server
* Funcao...........: Classe do Servidor TCP.
*************************************************************** */

package model.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.Client;

public class TCPServer extends Server {
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

  @Override
  public void sendDataToClient(Client client, String data) {
    boolean isConnected = client.getSocket().isConnected();
    boolean isClosed = client.getSocket().isClosed();

    if (isConnected && !isClosed) {
      System.out.println("> Enviando (" + data + ") para " + client.getIp());

      try {
        client.getOutputStream().writeObject(data);
        client.getOutputStream().flush();
      } catch (IOException e) {
        System.out.println("> Erro ao enviar");
        e.printStackTrace();
      }
    }
  }

  public void handleClient(Socket clientSocket) throws IOException {
    Client client = new Client(clientSocket);
    getApp().getClientController().createClient(client);

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
    getApp().getClientController().removeClient(client.getIp());
    client.close();
  }
}
