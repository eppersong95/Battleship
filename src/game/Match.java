package game;

import java.io.Serializable;

public class Match implements Serializable {
  private int user1, user2;
  private boolean user1Ready, user2Ready;
  private int user1Points, user2Points;
  private int attacker;
  
  public Match(int user1, int user2){
    this.user1 = user1;
    this.user2 = user2;
    
    this.user1Ready = true;
    
    user1Points = 0;
    user2Points = 0;
    
    attacker = user1; //user 1 will attack first
  }
  
  public void setUser2Ready() {
    user2Ready = true; //both are ready, now begin the match
  }
  
  public int getUser1() {
    return user1;
  }
  
  public int getUser2() {
    return user2;
  }
  
  public int getAttacker() {
    return attacker;
  }
  
  public void addUser1Point() {
    user1Points++;
  }
  
  public void addUser2Point() {
    user2Points++;
  }
  
  public int getUser1Points() {
    return user1Points;
  }
  
  public int getUser2Points() {
    return user2Points;
  }
}
