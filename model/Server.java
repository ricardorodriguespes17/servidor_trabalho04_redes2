package model;

import java.io.ObjectOutputStream;

public abstract class Server {
  protected int port;

  public Server(int port) {
    this.port = port;
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

  public abstract void sendMessageToAllClients(ObjectOutputStream sender, String message);

  public abstract void stop();

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
