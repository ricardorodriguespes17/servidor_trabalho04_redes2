package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer extends Server {
  private List<ObjectOutputStream> clientOutputStreams = new ArrayList<>();
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
    ObjectInputStream input = null;
    ObjectOutputStream outputStream = null;
    try {
      input = new ObjectInputStream(clientSocket.getInputStream());
      outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      clientOutputStreams.add(outputStream);
      while (true) {
        try {
          String data = (String) input.readObject();
          readMessage(data);
        } catch (Exception e) {
          System.out.println("> Erro: Classe não encontrada ao ler mensagem");
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
      clientOutputStreams.remove(outputStream);
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

        String completedMessage = "> " + user + " diz: '" + message + "' para " + groupId;
        System.out.println(completedMessage);
        sendMessageToAllClients(completedMessage);
        break;
      default:
        System.out.println("> Tipo de mensagem inválida");
        break;
    }

  }

  @Override
  public void sendMessageToAllClients(String message) {
    for (ObjectOutputStream outputStream : clientOutputStreams) {
      try {
        outputStream.writeObject(message);
        outputStream.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
