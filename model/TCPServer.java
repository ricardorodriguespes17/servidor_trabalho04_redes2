package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        handleClient(clientSocket);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
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

  private void handleClient(Socket clientSocket) throws IOException, ClassNotFoundException {
    ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
    String data = (String) input.readObject();
    try {
      readMessage(data);
    } catch (Exception e) {
      System.out.println("> Erro: Não foi possível ler a mensagem");
    }
  }

  private void readMessage(String data) throws Exception {
    String[] dataSplited = data.split("/");
    String type = dataSplited[0];
    String groupId = dataSplited[1];
    String user = dataSplited[2];

    switch (type) {
      case "send":
        String message = "";
        for (int i = 3; i < dataSplited.length; i++) {
          message += dataSplited[i] + " ";
        }
        message = message.trim();
        System.out.println("> " + user + " diz: '" + message + "' para " + groupId);
        break;
      default:
        System.out.println("> Tipo de mensagem inválida");
        break;
    }
  }

}
