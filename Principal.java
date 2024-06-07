/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 07/06/2024
* Ultima alteracao.: 07/06/2024
* Nome.............: Principal
* Funcao...........: Inicia o servidor.
*************************************************************** */

import model.Server;

public class Principal {
  public static void main(String[] args) {
    Server tcpServer = Server.createServer("TCP", 6789);

    // new Thread(tcpServer::start).start();
  }
}
