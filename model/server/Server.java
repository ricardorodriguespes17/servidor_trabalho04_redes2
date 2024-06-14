/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: Server
* Funcao...........: Classe abstrata do Servidor.
*************************************************************** */

package model.server;

import java.util.List;

import model.App;
import model.ChatUser;
import model.Client;
import model.utils.DataManager;

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
    } else if (type.equals("UDP")) {
      return new UDPServer(port);
    }  else {
      System.out.println("> Tipo de servidor inválido");
      return null;
    }
  }

  public abstract void start();

  public abstract void stop();

  public abstract void sendDataToClient(Client client, String data);

  public void processData(String serverIp, String data) {
    String[] dataSplited = data.split("/");
    String type = dataSplited[0];

    switch (type) {
      case "send":
        DataManager.send(this, dataSplited[1], dataSplited[2], dataSplited[3]);
        break;
      case "join":
        DataManager.join(this, serverIp, dataSplited[1], dataSplited[2]);
        break;
      case "leave":
        DataManager.leave(this, serverIp, dataSplited[1], dataSplited[2]);
        break;
      case "create":
        DataManager.create(this, dataSplited[1], dataSplited[2], dataSplited[3]);
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

  public void sendDataToSingleClient(String clientIp, String data) {
    List<Client> clients = getApp().getClientController().getAllClients();

    for (Client client : clients) {
      if (client.getIp().equals(clientIp)) {
        sendDataToClient(client, data);
        return;
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
