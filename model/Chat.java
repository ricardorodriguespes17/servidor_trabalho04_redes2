/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 20/06/2024
* Nome.............: Chat
* Funcao...........: Classe do objeto Chat/Grupo.
*************************************************************** */

package model;

public class Chat {
  private String name;

  public Chat(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
