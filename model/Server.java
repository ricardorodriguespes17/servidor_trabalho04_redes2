/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: Server
* Funcao...........: Classe abstrata do Servidor.
*************************************************************** */

package model;

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

  public abstract void sendDataToAllClients(String clientIp, String message);

  public abstract void stop();

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
