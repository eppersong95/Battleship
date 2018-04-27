package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ServerGUI extends JFrame {
  private Server server;
  private JPanel buttonPanel;
  private JButton listen;
  private JButton stopListen;
  private JButton close;
  private JLabel status;
  
  public ServerGUI(int port, int timeout) {
    //format frame
    this.setTitle("Battleship Server");
    this.setLayout(new BorderLayout());
    this.setSize(300,90);
    
    //create server
    server = new Server(port, timeout);
    
    //create status
    status = new JLabel();
    setStatus("Not connected", Color.RED);
    this.add(status, BorderLayout.NORTH);
    
    ServerListener listener = new ServerListener(this);
    //create, format buttons
    buttonPanel = new JPanel(new FlowLayout());
    listen = new JButton("Listen");
    listen.addActionListener(listener);
    close = new JButton("Close");
    close.addActionListener(listener);
    stopListen = new JButton("Stop Listening");
    stopListen.addActionListener(listener);
    buttonPanel.add(listen);
    buttonPanel.add(stopListen);
    buttonPanel.add(close);
    this.add(buttonPanel, BorderLayout.SOUTH);
  
    //set window visible
    this.setVisible(true);
  }
  
  public void setStatus(String msg, Color color) {
	  int buff = 32;
	  String temp = "Not Connected";
	  int baseLen = temp.length();
	  int actualLen = msg.length();
	  String buffer = "";
	
	  for(int i = 0; i < 32 + (baseLen-actualLen); i++) {
		  buffer+= " ";
	  }
	
	  String message = buffer + msg;
	  status.setText(message);
	  status.setForeground(color);
  }
  
  public Server getServer() {
    return server;
  }
  
  public static void main(String[] args) {
    int port = Integer.parseInt(args[0]);
    int timeout = Integer.parseInt(args[1]);
    new ServerGUI(port, timeout);
  }
}
