/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: Server
* Funcao...........: Classe abstrata do Servidor.
*************************************************************** */

package model;

import java.util.List;

public abstract class Server {
  protected int port;
  private App app;

  public Server(int port) {
    this.port = port;
    app = App.getInstance();
  }

  public static Server createServer(String type, int port) {
    if (type.equals("TCP")) {
      return new TCPServer(port);
    } else {
      System.out.println("Tipo de servidor inválido");
      return null;
    }
  }

  public abstract void start();

  public abstract void stop();

  public abstract void sendDataToClient(Client client, String data);

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

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public App getApp() {
    return app;
  }

  public void setApp(App app) {
    this.app = app;
  }
}
