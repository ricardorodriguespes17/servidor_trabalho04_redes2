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
    data = data.replace('(', ' ');
    data = data.replace(')', ' ');

    String[] dataSplited = data.split(" ");
    String type = dataSplited[0];
    String[] contentSplited = dataSplited[1].split(",");
    String groupId = contentSplited[0];
    String user = contentSplited[1];

    switch (type) {
      case "send":
        String message = contentSplited[2];
        System.out.println("> " + user + " diz: " + message + " para " + groupId);
        break;
      default:
        System.out.println("> Tipo de mensagem inválida");
        break;
    }
  }

}
