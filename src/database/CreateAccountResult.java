package database;

import java.io.Serializable;

public class CreateAccountResult implements Serializable {
  private boolean result;
  
  public CreateAccountResult(boolean res) {
    result = res;
  }
  
  public boolean getResult() {
    return result;
  }
}
