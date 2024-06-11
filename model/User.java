/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: User
* Funcao...........: Classe do objeto Usuário.
*************************************************************** */

package model;

public class User {
  private String ip;
  private String name;

  public User(String ip, String name) {
    this.ip = ip;
    this.name = name;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
