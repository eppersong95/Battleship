package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Database {
  private String driver;
  private String path;
  private String user;
  private String password;
  private Connection con;
  
  public Database() {
    driver = "com.mysql.jdbc.Driver";
    ReadFile();
    
    try {
      Class.forName(driver);
      con = DriverManager.getConnection(path, user, password);
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    
    this.InitializeDB();
  }
  
  private void ReadFile() {
    try {
      File file = new File("db.properties");
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      String[] info;
      
      while((line=br.readLine())!=null) {
        info = line.split(",");
        path = info[0];
        user = info[1];
        password = info[2];
      }
      
      br.close();
      fr.close();
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(0);
    } 
  }
  
  private void InitializeDB() {
    ArrayList<String> queries = new ArrayList<String>();
    try {
      File file = new File("config.sql");
      FileReader fr = new FileReader(file);
      BufferedReader br = new BufferedReader(fr);
      String line;
      
      while((line=br.readLine())!=null) {
        queries.add(line);
      }
      
      br.close();
      fr.close();
    } catch(IOException e) {
      e.printStackTrace();
      System.exit(0);
    } 
    
    try {
      Statement stmt = con.createStatement();
      String query1 = queries.get(0);
      String query2 = queries.get(1);
      String query3 = queries.get(2);
      stmt.executeUpdate(query1);
      stmt.executeUpdate(query2);
      stmt.executeUpdate(query3);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      System.out.println("Database already created.");
    }
  }
  
  
  public boolean CreateAccount(CreateAccountData cd) {
    int temp = 0;
    String username = cd.getUsername();
    String password = cd.getPassword();
    String query = "INSERT INTO Battleship.Users VALUES('" +
                   username + "',aes_encrypt('" + password + "', 'battleship'));";    
    try {
      Statement stmt = con.createStatement();
      temp = stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    if(temp != 0) {
      return true;
    }
    return false;
  }
  
  public boolean Login(LoginData ld) {
    ResultSet rs = null;
    String username = ld.getUsername();
    String password = ld.getPassword();
    int temp = 0;
    
    String query = "SELECT * FROM Battleship.Users WHERE (username = '" + username + 
        "') AND (password = aes_encrypt('" + password + "', 'battleship'));";
    try {
      Statement stmt = con.createStatement();
      rs = stmt.executeQuery(query);
      while(rs.next()) {
        temp++;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
     
    return (temp!=0);
  }
}
