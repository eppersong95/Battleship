package game;

import java.io.Serializable;

public class AttackLocation implements Serializable {
  private int row, col;
  private int userID, competetorID;
  
  public AttackLocation(int row, int col, int userID, int competetorID){
    this.row = row;
    this.col = col;
    this.userID = userID;
    this.competetorID = competetorID;
  }
  
  public int getRow() {
    return row;
  }
  
  public int getCol() {
    return col;
  }
  
  public int getAttackerID() {
    return userID;
  }
  
  public int getAttackedID() {
    return competetorID;
  }
}

