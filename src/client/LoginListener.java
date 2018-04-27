package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import database.CreateAccountData;
import database.LoginData;

public class LoginListener implements ActionListener {

  ClientGUI cg;
  Client client;
  public LoginListener(ClientGUI cg) {
    this.cg = cg;
    client = cg.getClient();
  }
  
  @Override
  public void actionPerformed(ActionEvent arg0) {
    String buttonPressed = arg0.getActionCommand();
    LoginPanel lp = cg.getLoginPanel();
    if(buttonPressed.equals("Create Account")) {
      
      if(!(lp.validCreateAccount())) {
        lp.setWarning("Invalid credentials", Color.RED);
        return;
      }
      else {
        lp = cg.getLoginPanel();
        String user = lp.getUsername();
        String password = lp.getPassword();
        try {
          client.sendToServer(new CreateAccountData(user, password));
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
    
    else {
      if(!(lp.validLogin())) {
        lp.setWarning("Invalid login credentials", Color.RED);
        return;
      }
      else {
        String user = lp.getUsername();
        String password = lp.getPassword();
        try {
          client.sendToServer(new LoginData(user, password));
        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

}
