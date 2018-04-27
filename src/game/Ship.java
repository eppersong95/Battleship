package game;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Ship {
  private Point location;
  private BufferedImage img, imgleft, imgright, imgupside;
  private int rotation;
  private String name;
  
  Ship(String shipName, int x, int y){
    try {
      //load all of the images into their respective variable
      img = ImageIO.read(Ship.class.getResource("/_game/images/" + shipName + ".png"));
      imgleft = ImageIO.read(Ship.class.getResource("/_game/images/" + shipName + "left.png"));
      imgright = ImageIO.read(Ship.class.getResource("/_game/images/" + shipName + "right.png"));
      imgupside = ImageIO.read(Ship.class.getResource("/_game/images/" + shipName + "upside.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    name = shipName;
    location = new Point(x, y);
    rotation = 0;
  }
  
  public Point getLocation() {
    return location;
  }
  
  public void setLocation(int x, int y) {
    location.setLocation(x, y);
  }
  
  public void rotateShip(Boolean clockwise) {
    if (clockwise) { //0->90->180->270->0
      rotation += 90;
      if (rotation >= 360)
        rotation = 0;     
    } else{ //else: 0->270->180->90
      rotation -= 90;
      if (rotation < 0)
        rotation = 270; 
    }   
  }
  
  public ImageIcon shipIcon() {
    ImageIcon res = new ImageIcon();
    
    if (rotation == 0) { //original
      res.setImage(img);
    } else if (rotation == 90) { //right
      res.setImage(imgright);
    } else if (rotation == 180) { //upside
      res.setImage(imgupside);
    } else { //rotation ==270, left
      res.setImage(imgleft);
    }
    
    return res;
  }
  
  public Rectangle shipBounds() {
    Rectangle res = new Rectangle();
    
    if (rotation == 0) { //original
      res.setBounds(location.x + img.getWidth(), location.y, img.getWidth(), img.getHeight());
    } else if (rotation == 90) { //right
      res.setBounds(location.x, location.y, imgright.getWidth(), imgright.getHeight());
    } else if (rotation == 180) { //upside
      res.setBounds(location.x, location.y, imgupside.getWidth(), imgupside.getHeight());
    } else { //rotation ==270, left
      res.setBounds(location.x, location.y, imgleft.getWidth(), imgleft.getHeight());
    }
    
    return res;
  }

  public void updateLocation(Point loc) {
    int x = (getCol()*45) + 25;
    int y = (getRow()*45) + 20;
    
    //make sure it isn't on the first row or column,
    //if it is move it out
    if (getCol() == 0)
      x = 70;
    if (getRow() == 0)
      y = 65;
    
    location.setLocation(x, y);
  }
  
  public int getRow() {
    return location.y/45;
  }
  
  public int getCol() {
    return location.x/45;
  }
  
  public int getLength() {
      return img.getHeight()/45 + 1;
  }
  
  public int getRotation() {
    return rotation;
  }
  
  public String getName() {
    return name;
  }

  public Boolean inGrid(Rectangle gridBounds) {
    if (gridBounds.contains(location))
      return true;
    else
      return false;
  }
}

