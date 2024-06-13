/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 13/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: ClientController
* Funcao...........: Classe controladora do CRUD do Client.
*************************************************************** */

package controller;

import java.util.List;

import dao.ClientRepository;
import model.Client;

public class ClientController {
  private ClientRepository clientRepository = new ClientRepository();

  public void createClient(Client chatUser) {
    clientRepository.createClient(chatUser);
  }

  public List<Client> getAllClients() {
    return clientRepository.getAllClients();
  }

  public Client getClientByIp(String clientIp) {
    return clientRepository.getClientByIp(clientIp);
  }

  public void removeClient(String clientIp) {
    clientRepository.removeClient(clientIp);
  }
}
