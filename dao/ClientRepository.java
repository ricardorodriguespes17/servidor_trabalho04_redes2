/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 13/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: ClientRepository
* Funcao...........: Classe do repositorio dos dados de Client.
*************************************************************** */

package dao;

import java.util.ArrayList;
import java.util.List;

import model.Client;

public class ClientRepository {
  private static final List<Client> clients = new ArrayList<>();

  public void createClient(Client client) {
    clients.add(client);
  }

  public Client getClientByIp(String clientIp) {
    for(Client client : getAllClients()) {
      if(client.getIp().equals(clientIp)) {
        return client;
      }
    }

    return null;
  }

  public List<Client> getAllClients() {
    return new ArrayList<>(clients);
  }

  public void removeClient(String clientIp) {
    for(Client client : getAllClients()) {
      if(client.getIp().equals(clientIp)) {
        clients.remove(client);
      }
    }
  } 
}
