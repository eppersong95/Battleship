package database;

import java.io.Serializable;

public class CreateAccountData implements Serializable {
  private String username;
  private String password;
  
  public CreateAccountData(String un, String pw) {
    username = un;
    password = pw;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
}

