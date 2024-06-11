/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: UserRepository
* Funcao...........: Classe do repositorio dos dos de Usuário.
*************************************************************** */

package dao;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
  private static final List<User> users = new ArrayList<>();

  public void createUser(User user) {
    users.add(user);
  }

  public List<User> getAllUsers() {
    return new ArrayList<>(users);
  }

  public User getUserById(String ip) {
    for (User user : users) {
      if (user.getIp().equals(ip)) {
        return user;
      }
    }
    return null;
  }

  public void updateUser(User updatedUser) {
    for (User user : users) {
      if (user.getIp().equals(updatedUser.getIp())) {
        user = updatedUser;
      }
    }
  }
}
