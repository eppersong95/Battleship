package server;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerListener implements ActionListener {
  private ServerGUI sg;
  
  public ServerListener(ServerGUI sg) {
    this.sg = sg;
  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    String buttonPressed = arg0.getActionCommand();
    Server server = sg.getServer();
    
    if(buttonPressed.equals("Listen")) {
      try {
        server.listen();
        sg.setStatus("Server Listening", Color.GREEN);
      } catch (IOException e) {
        sg.setStatus("Unable to start server", Color.RED);
      }
    }
    
    else if (buttonPressed.equals("Stop Listening")) {
      if(server.isListening()) {
        server.stopListening();
        sg.setStatus("Server stopped listening", Color.RED);
      }
      else {
        sg.setStatus("Server not started", Color.RED);
      }
    }
    
    else {
      try {
        server.close();
        sg.setStatus("Server closed", Color.RED);
      } catch (IOException e) {
        sg.setStatus("Unable to close server", Color.RED);
      }
    }
    
  }
}
