package client;

import java.awt.Color;
import java.io.IOException;

import game.Match;
import game.MatchEndedData;
import game.AttackLocation;
import game.AttackResult;
import database.CreateAccountResult;
import database.LoginResult;
import game.User;
import ocsf.client.AbstractClient;

public class Client extends AbstractClient {

  private ClientGUI cg;
  private User user; //database needs to send userID with logindata
  
  public Client() {
    super("localhost", 8300);
  }
  
  public void setClientGUI(ClientGUI cg) {
    this.cg = cg;
  }
  
  public boolean connectToServer(String host, int port) {
    
    try {
      this.setHost(host);
      this.setPort(port);
      this.openConnection();
    } catch (IOException e) {
      //e.printStackTrace();
      return false;
    }
    
    return true;
  }
  
  public void setUser(int id) {
    user = new User(id);
  }
  
  public void fillShipGrid(int[][] array) {
      user.fillShipGrid(array);
    }
  
  public int getUserID() {
      return user.getUserID();
    }
    
    public int getCompetetorID() {
      return user.getCompetetorID();
    }
    
    public boolean canAttack() {
      return user.getReady();
    }

  @Override
  protected void handleMessageFromServer(Object arg0) {
    if(arg0 instanceof LoginResult) {
      String x = "Login Failed";
      Color y = Color.RED;
      LoginResult temp = (LoginResult)arg0;
      boolean res = temp.getResult();
      if(res) {
        setUser(temp.getUserID());
        cg.toGame();
      }
      
      LoginPanel lp = cg.getLoginPanel();
      lp.setWarning(x, y);
    }
    
    else if(arg0 instanceof CreateAccountResult) {
      String x = "Username already in use";
      Color y = Color.RED;
      CreateAccountResult temp = (CreateAccountResult)arg0;
      boolean res = temp.getResult();
      if(res) {
        x = "Account creation successful";
        y = Color.GREEN;
      }
      
      LoginPanel lp = cg.getLoginPanel();
      lp.setWarning(x, y);
    }
    
    else if (arg0 instanceof AttackLocation) {
      AttackLocation al = (AttackLocation)arg0;
      AttackResult ar = new AttackResult(user.isHit(al.getRow(), al.getCol()),
            al.getRow(), al.getCol(), al.getAttackerID(), al.getAttackedID());
      
      //the user has been attacked and can attack now
      user.setReady(true);
      cg.updateOceanGrid(ar.getResult(), ar.getRow(), ar.getCol());
      cg.displayAttackMessage();
        
        //send a message to the server as an AttackResult
        sendMessage(ar);
    }
    
    else if (arg0 instanceof AttackResult) { 
        AttackResult ar = (AttackResult)arg0;
        
        //the user just attacked, wait to attack again
        user.setReady(false);
        
        cg.updateTargetGrid(ar.getResult(), ar.getRow(), ar.getCol());
        cg.displayWaitMessage();
      }
    
      else if (arg0 instanceof Match) {
        //if the attacker is for this client, update the gamepanel to let them know to attack
        //or else let them know to wait
        Match match = (Match)arg0;
        if (match.getAttacker() == user.getUserID()) {
          user.setReady(true);
         cg.displayAttackMessage();
        }
        else {
          user.setReady(false);
          cg.displayWaitMessage();
        }
      }//end instanceof match
    
      else if (arg0 instanceof MatchEndedData) {
        MatchEndedData med = (MatchEndedData)arg0;
        
        if (med.getWinnerID() == user.getUserID())
          cg.displayWinMessage();
        else
          cg.displayLoseMessage();
        
        
      }//end instanceof MatchEndedData
    
  }
  
    public void sendMessage(Object msg) {
      try {
      this.sendToServer(msg);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }
}

