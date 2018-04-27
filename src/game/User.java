package game;

public class User {
  private int[][] shipGrid;
  private int userID;
  private int competetor; 
  private boolean ready; //game ready
  
  
  public User(int userID){
    shipGrid = new int[11][11];
    this.userID = userID;
    ready = false;
    
    /*
     * For the purpose of testing this there are only 2 possible users, so it can be assumed that their opponent is
     * the other user.
     */
    if (userID == 1) {
      competetor = 2;
    }else {
      competetor = 1;
    }
  }
  
  public void fillShipGrid(int[][] array) {
    shipGrid = array;
  }
  
  public int getUserID() {
    return userID;
  }
  
  public int getCompetetorID() {
    return competetor;
  }
  
  public boolean isHit(int row, int col) {
    if (shipGrid[row][col] == 1)
      return true;
    else return false;
  }
  
  public boolean getReady() {
    return ready;
  }
  
  public void setReady(boolean val) {
    ready = val;
  }
}

