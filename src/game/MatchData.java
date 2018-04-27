package game;

import java.io.Serializable;

public class MatchData implements Serializable{
  private int confirmedUser, competetor;
  
  public MatchData(int confirmedUser, int competetor){
    this.confirmedUser = confirmedUser;
    this.competetor = competetor;
  }
  
  public int getConfirmed() {
    return confirmedUser;
  }
  
  public int getCompetetor() {
    return competetor;
  }
}
