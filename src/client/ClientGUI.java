package client;

import javax.swing.JFrame;

import client.ConnectionPanel;
import game.GamePanel;

public class ClientGUI extends JFrame {
  public final int WIDTH = 1290;
    public final int HEIGHT = 845;
    private Client client;
    private ConnectionPanel cp;
    private LoginPanel lp;
    private GamePanel gp;
    
    public ClientGUI() {
      this.setTitle("Battleship!");
      this.setSize(WIDTH, HEIGHT);
      this.setResizable(false);
      
      client = new Client();
      client.setClientGUI(this);
      cp = new ConnectionPanel(this);
      lp = new LoginPanel(this);
      gp = new GamePanel(client);
      
      this.add(gp);
      this.setContentPane(cp);
      this.setVisible(true);
    }
    
    public Client getClient() {
      return client;
    }
  
  public ConnectionPanel getConnectionPanel() {
    return cp;
  }
  
  public LoginPanel getLoginPanel() {
    return lp;
  }
  
  public void toLogin() {
    this.setContentPane(lp);
  }
  
  public void toGame() {
    this.setContentPane(gp);
    this.setVisible(true);
  }
  
  /*
   * Methods to control the GamePanel
   */
  public void updateOceanGrid(boolean hit, int row, int col) {
    gp.updateOceanGrid(hit, row, col);
  }
  
  public void displayAttackMessage() {
    gp.displayAttackMessage();
  }
  
  public void updateTargetGrid(boolean hit, int row, int col) {
    gp.updateTargetGrid(hit, row, col);
  }
  
  public void displayWaitMessage() {
    gp.displayWaitMessage();
  }
  
  public void displayWinMessage() {
    gp.displayWinMessage();
  }
  
  public void displayLoseMessage() {
    gp.displayLoseMessage();
  }
  
  public static void main(String[] args) {
    new ClientGUI();
  }
}

