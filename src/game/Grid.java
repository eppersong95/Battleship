package game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Grid extends JPanel{
  //reminder: Java uses [row][col]
  private int[][] gridArray;
  private Boolean carrier, battleship, submarine, destroyer, patrolboat; //to insure all ships are placed on the grid
  
  //used to hold position of ship
  private int rot_carrier, rot_battleship, rot_submarine, rot_destroyer, rot_patrolboat;
  private int row_carrier = -1, row_battleship = -1, row_submarine = -1, row_destroyer = -1, row_patrolboat = -1;
  private int col_carrier = -1, col_battleship = -1, col_submarine = -1, col_destroyer = -1, col_patrolboat = -1;
  
  Grid(){
    gridArray = new int[11][11];
    carrier = false;
    battleship = false;
    submarine = false;
    destroyer = false;
    patrolboat = false;
    
    //set it to a grid layout
    this.setLayout(new GridLayout(11,11,2,2));
    
    JLabel lblEven, lblOdd;
    BufferedImage gridEven = null, gridOdd = null;
    
    //read image files
    try {
      gridEven = ImageIO.read(Grid.class.getResource("/_game/images/gridEven.png"));
      gridOdd = ImageIO.read(Grid.class.getResource("/_game/images/gridOdd.png"));
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    this.setOpaque(false);
    
    //place those images in the grid
    for (int i = 0; i < 121; i++) {
      /*
       * Place the labels
       */
      if (i > 0 && i < 11) { //Top row, A-J
        try {
          BufferedImage buff = null;
          buff = ImageIO.read(Grid.class.getResource("/_game/GridLabels/0" + i + ".png"));
          JLabel lbl = new JLabel(new ImageIcon(buff));
          this.add(lbl);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      /*
       * Add side labels
       */
      else if (i > 0 && i%11 == 0) {
        try {
          BufferedImage buff = null;
          buff = ImageIO.read(Grid.class.getResource("/_game/GridLabels/" + i + ".png"));
          JLabel lbl = new JLabel(new ImageIcon(buff));
          this.add(lbl);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      
      /*
       * Don't add labels
       */
      else {
        if (i%2 == 0) {
          lblEven = new JLabel(new ImageIcon(gridEven));
          lblEven.setName("" + i);
          this.add(lblEven);
        }
        else {
          lblOdd = new JLabel(new ImageIcon(gridOdd));
          lblOdd.setName("" + i);
          this.add(lblOdd);
        }
      }
  
    }

    
  }

  private void updateGridLocation(String ship) {
    int length = 0, col = 0, row = 0, rot = 0;
    
    //get where we're working with
    switch (ship) {
    case "aircraftCarrier": 
      length = 5;
      col = col_carrier;
      row = row_carrier;
      rot = rot_carrier;
      break;
    case "battleship":
      length = 4; 
      col = col_battleship;
      row = row_battleship;
      rot = rot_battleship;
      break;
    case "submarine":
      length = 3; 
      col = col_submarine;
      row = row_submarine;
      rot = rot_submarine;
      break;
    case "destroyer":
      length = 3; 
      col = col_destroyer;
      row = row_destroyer;
      rot = rot_destroyer;
      break;
    case "patrolboat": 
      length = 2; 
      col = col_patrolboat;
      row = row_patrolboat;
      rot = rot_patrolboat;
      break;
    }
    
    if (row != -1) {      
      //clear the old location by setting everything = 0;
      if (rot == 0 || rot == 180) { //vertical
        for (int i = 0; i < length; i++) {
          gridArray[row + i][col] = 0;
        }
      } else { //horizontal
        for (int i = 0; i < length; i++) {
          gridArray[row][col + i] = 0;
        }
      }
    }
  }
  
  public Boolean placeShip(Ship ship) {
    int row = ship.getRow();
    int col = ship.getCol();
    int rotation = ship.getRotation();
    int length = ship.getLength();
    int i;
    
    /*
     * Check for overlap
     */
    if (gridArray[row][col] == 0) { //there is nothing in the origin cell
      if (rotation == 0 || rotation == 180) { //ship is vertical, check below origin for length \/
        for (i = 1; i < length; i++) {
          if (gridArray[row + i][col] != 0) //the cell is not empty
            return false; //do not place the ship, overlap
        }
      } else { //ship is horizontal, check beside it for length -->
        for (i = 1; i < length; i++) {
          if (gridArray[row][col + i] != 0)
            return false; //do not place ship, overlap
        }
      }
    }else {
      return false;
    }
    
    /*
     * everything was good, officially place the ship
     */
    updateGridLocation(ship.getName());
    if (rotation == 0 || rotation == 180) { //ship is vertical, check below origin for length \/
      for (i = 0; i < length; i++) {
        gridArray[row + i][col] = 1;
      }
    } else { //ship is horizontal, check beside it for length -->
      for (i = 0; i < length; i++) {
        gridArray[row][col + i] = 1;
      }
    }
    
    /*
     * update the boolean for placed and update the location variables
     */
    switch (ship.getName()) {
    case "aircraftCarrier": carrier = true;
      row_carrier = row;
      col_carrier = col;
      rot_carrier = rotation;
      break;
    case "battleship": battleship = true;
      row_battleship = row;
      col_battleship = col;
      rot_battleship = rotation;
      break;
    case "submarine": submarine = true;
      row_submarine = row;
      col_submarine = col;
      rot_submarine = rotation;
      break;
    case "destroyer": destroyer = true;
      row_destroyer = row;
      col_destroyer = col;
      rot_destroyer = rotation;
      break;
    case "patrolboat": patrolboat = true;
      row_patrolboat = row;
      col_patrolboat = col;
      rot_patrolboat = rotation;
      break;
    }
    
    return true; //ship is placed!
  }

  public void moveOutofBounds(String name) {
    switch (name) {
    case "aircraftCarrier": carrier = false;
      row_carrier = -1;
      col_carrier = -1;
      rot_carrier = 0;
      break;
    case "battleship": battleship = false;
      row_battleship = -1;
      col_battleship = -1;
      rot_battleship = 0;
      break;
    case "submarine": submarine = false;
      row_submarine = -1;
      col_submarine = -1;
      rot_submarine = 0;
      break;
    case "destroyer": destroyer = false;
      row_destroyer = -1;
      col_destroyer = -1;
      rot_destroyer = 0;
      break;
    case "patrolboat": patrolboat = false;
      row_patrolboat = -1;
      col_patrolboat = -1;
      rot_patrolboat = 0;
      break;
    }
  }
  
  public int[][] getGrid(){
    return gridArray;
  }
  
  public Boolean confirmPositions() {
    //all ships are placed
    if (carrier && battleship && submarine && destroyer && patrolboat) {
      
      return true;
    }
    return false;
  }
  
}

