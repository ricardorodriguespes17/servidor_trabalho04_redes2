package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
    }
  }

  @Override
  public void stop() {
    try {
      if (serverSocket != null && !serverSocket.isClosed()) {
        serverSocket.close();
        System.out.println("TCP Server encerrado.");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleClient(Socket clientSocket) {
    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

      String clientInput;
      while ((clientInput = in.readLine()) != null) {
        System.out.println("Received: " + clientInput);
        out.println("Echo: " + clientInput);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        clientSocket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
