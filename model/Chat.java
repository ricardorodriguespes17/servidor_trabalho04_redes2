/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 11/06/2024
* Nome.............: Chat
* Funcao...........: Classe do objeto Chat/Grupo.
*************************************************************** */

package model;

public class Chat {
  private String id;
  private String name;
  private String description;

  public Chat(String id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
