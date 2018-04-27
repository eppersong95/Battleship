package game;

import java.io.Serializable;

public class AttackResult implements Serializable {
  private boolean result;
  int row;
  int col;
  int AttackerID, AttackedID;
  
  public AttackResult(boolean result, int row, int col, int Attacker, int Attacked) {
    this.result = result;
    this.row = row;
    this.col = col;
    AttackerID = Attacker;
    AttackedID = Attacked;
  }
  
  public int getAttacker() {
    return AttackerID;
  }
  
  public int getAttacked() {
    return AttackedID;
  }
  
  public boolean getResult() {
    return result;
  }
  
  public int getRow() {
    return row;
  }
  
  public int getCol() {
    return col;
  }
}
