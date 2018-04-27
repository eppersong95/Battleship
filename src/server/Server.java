package server;

import java.io.IOException;
import java.util.HashMap;

import game.AttackLocation;
import game.AttackResult;
import game.Match;
import game.MatchData;
import game.MatchEndedData;
import database.CreateAccountData;
import database.CreateAccountResult;
import database.Database;
import database.LoginData;
import database.LoginResult;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

//Server to handle Battleship games
public class Server extends AbstractServer {
  private Database db;
  private int clientID;
  private HashMap<Integer, ConnectionToClient> users;
  private HashMap<Integer, Match> matches;
  
  //constructor which sets only port
  public Server(int port) {
    super(port);
    clientID = 0;
    db = new Database();
    users = new HashMap<Integer, ConnectionToClient>();
      matches = new HashMap<Integer, Match>();
  }
  
  //constructor which sets port and timeout
  public Server(int port, int timeout) {
    super(port);
    this.setTimeout(timeout);
    db = new Database();
    users = new HashMap<Integer, ConnectionToClient>();
      matches = new HashMap<Integer, Match>();
  }

  //method for handling messages from clients
  @Override
  protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
    if (arg0 instanceof LoginData) {
      LoginData temp = (LoginData)arg0;
      boolean result = db.Login(temp);
      if(result) {
        clientID++;
        users.put(clientID, arg1);
      }
      try {
        arg1.sendToClient(new LoginResult(result, clientID));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    else if (arg0 instanceof CreateAccountData) {
      CreateAccountData temp = (CreateAccountData)arg0;
      boolean result = db.CreateAccount(temp);
      try {
        arg1.sendToClient(new CreateAccountResult(result));
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    else if (arg0 instanceof AttackLocation) {
        /*
         * Send the location that was attacked to the attacked user, wait for a result
         * in the form of AttackResult.
         */
        AttackLocation al = (AttackLocation)arg0;
        sendMessage(arg0, users.get(al.getAttackedID()));
      }
      /*
       * Send the result of the attack back to the attacker
       */
      else if (arg0 instanceof AttackResult) {
        AttackResult ar = (AttackResult)arg0;
        sendMessage(arg0, users.get(ar.getAttacker()));
        
        //update points
        if (ar.getResult()) {
          if (matches.get(ar.getAttacker()).getUser1() == ar.getAttacker())
            matches.get(ar.getAttacker()).addUser1Point(); //user 1 attacked successfully
          else
            matches.get(ar.getAttacker()).addUser2Point(); //user 2 attacked successfully
        }
          
        //check to see if there is a winner
        //server ends the match and takes the user back to the lobby, probably after a few seconds delay to 
        //display the message
        if (matches.get(ar.getAttacker()).getUser1Points() == 17) { //user 1 wins
          MatchEndedData med = new MatchEndedData(matches.get(ar.getAttacker()).getUser1(),
              matches.get(ar.getAttacker()).getUser2());
          
          sendMessage(med, users.get(med.getWinnerID()));
          sendMessage(med, users.get(med.getLoserID()));
        }
        if (matches.get(ar.getAttacker()).getUser2Points() == 17) { //user 2 wins
          MatchEndedData med = new MatchEndedData(matches.get(ar.getAttacker()).getUser2(),
              matches.get(ar.getAttacker()).getUser1());
          
          sendMessage(med, users.get(med.getWinnerID()));
          sendMessage(med, users.get(med.getLoserID()));
        }
      }
      else if (arg0 instanceof MatchData) {
        //create a new match if one does not exist for the confirmed user
        MatchData md = (MatchData)arg0;
        if (!matches.containsKey(md.getConfirmed())) {
          
          //a match does not exist for the confirmed user so create a new match
          Match match = new Match(md.getConfirmed(), md.getCompetetor());
          
          //save the match in the matches hashmap for the confirmed user ID and their competetor
          matches.put(md.getConfirmed(), match);
          matches.put(md.getCompetetor(), match);
          
          //don't do anything until the competetor is ready to play
        }else {
          //the match already exists, so set User2 to ready
          Match match = matches.get(md.getConfirmed());
          match.setUser2Ready();
          
          //now the game can begin, let both users know
          //send the clients the match, user1 attacks first
          sendMessage(match, users.get(match.getUser1()));
          sendMessage(match, users.get(match.getUser2()));
        }
      }//end if MatchData
    
  }
  
    protected void sendMessage(Object msg, ConnectionToClient receiver) {
      try {
      receiver.sendToClient(msg);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    }
    
    
}
