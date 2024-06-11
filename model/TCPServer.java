package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
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
        new Thread(() -> {
          handleClient(clientSocket);
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

  private void handleClient(Socket clientSocket) {
    ObjectInputStream input = null;
    ObjectOutputStream outputStream = null;
    try {
      input = new ObjectInputStream(clientSocket.getInputStream());
      outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
      clientOutputStreams.add(outputStream);
      while (true) {
        try {
          String data = (String) input.readObject();
          readData(outputStream, data);
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

  private void readData(ObjectOutputStream sender, String data) throws Exception {
    String[] dataSplited = data.split("/");
    String type = dataSplited[0];
    String chatId = dataSplited[1];
    String user = dataSplited[2];
    String error = "";

    switch (type) {
      case "send":
        String messageText = "";
        for (int i = 3; i < dataSplited.length; i++) {
          messageText += dataSplited[i] + " ";
        }
        messageText = messageText.trim();

        LocalDateTime localDateTime = LocalDateTime.now();

        Message message = new Message(chatId, user, messageText, localDateTime);
        this.getApp().getMessageController().createMessage(message);
        break;
      case "join":
        break;
      case "leave":
        break;
      default:
        error = "error/Tipo de mensagem inválida";
        sendDataToAllClients(sender, error);
        return;
    }

    sendDataToAllClients(sender, data);
  }

  @Override
  public void sendDataToAllClients(ObjectOutputStream sender, String message) {
    for (ObjectOutputStream outputStream : clientOutputStreams) {
      if (!outputStream.equals(sender)) {
        try {
          outputStream.writeObject(message);
          outputStream.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
