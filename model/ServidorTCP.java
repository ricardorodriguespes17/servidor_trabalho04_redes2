package model;

import java.net.*;
import java.io.*;

public class ServidorTCP {
  public static void main(String[] args) {
    int port = 6789;
    ServerSocket server;
    try {
      server = new ServerSocket(port);
      System.out.println("> Server is running");

      Socket connection = null;
      connection = server.accept();

      ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
      System.out.println((String) input.readObject());
    } catch (IOException e) {
      System.out.println("Não foi possível estabelecer conexão");
    } catch (ClassNotFoundException e) {
      System.out.println("Não foi possível ler a mensagem recebida");
    }

  }
}
