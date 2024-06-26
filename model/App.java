/* ***************************************************************
* Autor............: Ricardo Rodrigues Neto
* Matricula........: 201710560
* Inicio...........: 10/06/2024
* Ultima alteracao.: 13/06/2024
* Nome.............: App
* Funcao...........: Classe de gerenciamento da aplicação.
*************************************************************** */

package model;

import controller.ChatController;
import controller.ChatUserController;
import controller.ClientController;
import controller.MessageController;

public class App {
  private static App instance;
  private ClientController clientController = new ClientController();
  private ChatController chatController = new ChatController();
  private ChatUserController chatUserController = new ChatUserController();
  private MessageController messageController = new MessageController();

  public static App getInstance() {
    if (instance == null) {
      instance = new App();
    }
    return instance;
  }

  public static void setInstance(App instance) {
    App.instance = instance;
  }

  public ChatController getChatController() {
    return chatController;
  }

  public void setChatController(ChatController chatController) {
    this.chatController = chatController;
  }

  public ChatUserController getChatUserController() {
    return chatUserController;
  }

  public void setChatUserController(ChatUserController chatUserController) {
    this.chatUserController = chatUserController;
  }

  public MessageController getMessageController() {
    return messageController;
  }

  public void setMessageController(MessageController messageController) {
    this.messageController = messageController;
  }

  public ClientController getClientController() {
    return clientController;
  }

  public void setClientController(ClientController clientController) {
    this.clientController = clientController;
  }

  

}
