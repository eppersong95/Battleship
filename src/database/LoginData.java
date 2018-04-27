package database;

import java.io.Serializable;

public class LoginData implements Serializable {
  private String username;
  private String password;
  
  public LoginData(String user, String pw) {
    username = user;
    password = pw;
  }
  
  public String getUsername() {
    return username;
  }
  
  public String getPassword() {
    return password;
  }
}

