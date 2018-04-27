package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionListener implements ActionListener {

  private ClientGUI cg;
  
  public ConnectionListener(ClientGUI cg) {
    this.cg = cg;
    
  }
  @Override
  public void actionPerformed(ActionEvent arg0) {
    Client client = cg.getClient();
    ConnectionPanel cp = cg.getConnectionPanel();
    String host = cp.getHost();
    int port = cp.getPort();
    boolean isConnect = false;
    
    try {
      isConnect = client.connectToServer(host, port);
    } catch(Exception e) {
      e.printStackTrace();
      isConnect = false;
    }
    if(isConnect) {
      cp.setWarning("Connected", Color.GREEN);
      cg.toLogin();
    }
    else {
      cp.setWarning("Unable to connect to server", Color.RED);
      cg.setVisible(true);
    }
  }

}
