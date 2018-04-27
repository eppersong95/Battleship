package game;

import java.io.Serializable;

public class MatchEndedData implements Serializable {
  private int winnerID, loserID;
  
  /*
   * Whoever implements the server: decide what to do in the case of a forfeit. Should the forfeiter lose?
   */
  
  public MatchEndedData(int winnerID, int loserID) {
    this.winnerID = winnerID;
    this.loserID = loserID;
  }
  
  public int getWinnerID() {
    return winnerID;
  }
  
  public int getLoserID() {
    return loserID;
  }
}
