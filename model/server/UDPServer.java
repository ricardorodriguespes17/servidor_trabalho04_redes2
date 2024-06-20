/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 14/06/2024
* Ultima alteracao.: 20/06/2024
* Nome.............: UDP Server
* Funcao...........: Classe do Servidor UDP.
*************************************************************** */

package model.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import model.Client;

public class UDPServer extends Server {
  private DatagramSocket socket;

  public UDPServer(int port) {
    super(port);
  }

  @Override
  public void start() {
    try {
      socket = new DatagramSocket(this.port);
      System.out.println("> UDP Server está rodando na porta " + port);
    } catch (SocketException e) {
      e.printStackTrace();
    }

    byte[] receivedData = new byte[1024];

    while (true) {
      DatagramPacket receivedDatagramPacket = new DatagramPacket(receivedData, receivedData.length);
      String clientIp = "";

      try {
        socket.receive(receivedDatagramPacket);
        clientIp = receivedDatagramPacket.getAddress().getHostAddress();
      } catch (IOException e) {
        e.printStackTrace();
      }

      System.out.println("> Client UDP conectado com ip: " + clientIp);

      try {
        handleClient(receivedDatagramPacket);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void stop() {
    if (socket != null && !socket.isClosed()) {
      socket.close();
      System.out.println("> UDP Server encerrado.");
    }
  }

  @Override
  public void sendDataToClient(Client client, String data) {

  }

  public void handleClient(DatagramPacket clientDatagramPacket) throws IOException {
    Client client = new Client(clientDatagramPacket);
    if(getApp().getClientController().getClientByIp(client.getIp()) == null) {
      getApp().getClientController().createClient(client);
    }

    String data = "";

    try {
      data = new String(clientDatagramPacket.getData());
      System.out.println("> Cliente UDP" + client.getIp() + " enviou: " + data);
    } catch (Exception e) {
      e.printStackTrace();
    }

    processData(null, data);

    System.out.println("> Cliente UDP " + client.getIp() + " desconectado.");
    getApp().getClientController().removeClient(client.getIp());
    client.close();
  }

}
