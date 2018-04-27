package database;

import java.io.Serializable;

public class LoginResult implements Serializable {
  private boolean result;
  private int userID;
  
  public LoginResult(boolean res, int id) {
    result = res;
    userID = id;
  }
  
  public boolean getResult() {
    return result;
  }
  
  public int getUserID() {
    return userID;
  }
}

