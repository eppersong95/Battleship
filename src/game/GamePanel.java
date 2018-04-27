package game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import client.Client;
import client.SoundPlayer;

public class GamePanel extends JPanel{
  private BufferedImage background;
  private MouseHandler ma;
  
  private Ship ship_carrier, ship_battleship, ship_submarine, ship_destroyer, ship_patrolboat;
  
  JLayeredPane gamepnl;
  private JLabel carrier, battleship, submarine, destroyer, patrolboat;
  private Grid oceanGrid, targetGrid;
  private JButton btnConfirm;
  private JLabel targetTransp;
  private BufferedImage hit, miss;
  private JLabel lblAttack, lblWait;
  private JLabel lblWin, lblLose;
  private JLabel lblHelp, btnHelp, btnClose, btnCloseGame; //yes, this is a weird naming convention but it works
  
  private int oceanx = 25, oceany = 10, targetx = 670, targety = 10;
  
  private Client client;
  
  public GamePanel(Client client){  
    BufferedImage transp = null, youwin = null, youlose = null, attackimg = null, waitimg = null, help = null,
        helpButtonImg = null, closeButtonImg = null, closeGame = null;
    Integer shipLayer = new Integer(5);
    
    this.client = client;
    
    //Read the BufferedImages   
    try {
      ///Battleship/game/images/background.png
      background = ImageIO.read(GamePanel.class.getResource("/_game/images/background.png"));
      transp = ImageIO.read(GamePanel.class.getResource("/_game/images/transp.png"));
      hit = ImageIO.read(GamePanel.class.getResource("/_game/images/hit.png"));
      miss = ImageIO.read(GamePanel.class.getResource("/_game/images/miss.png"));
      youwin = ImageIO.read(GamePanel.class.getResource("/_game/images/youWin.png"));
      youlose = ImageIO.read(GamePanel.class.getResource("/_game/images/youLose.png"));
      attackimg = ImageIO.read(GamePanel.class.getResource("/_game/images/attack.png"));
      waitimg = ImageIO.read(GamePanel.class.getResource("/_game/images/wait.png"));
      help = ImageIO.read(GamePanel.class.getResource("/_game/images/help.png"));
      helpButtonImg = ImageIO.read(GamePanel.class.getResource("/_game/images/helpbutton.png"));
      closeButtonImg = ImageIO.read(GamePanel.class.getResource("/_game/images/closebutton.png"));
      closeGame = ImageIO.read(GamePanel.class.getResource("/_game/images/closegamebutton.png"));
    } catch (IOException e) {
      e.printStackTrace();
    }   
    
    //Key Bindings
    this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "turnShipLeft");
    this.getActionMap().put("turnShipLeft", new TurnShipLeft());
    
    this.getInputMap(2).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "turnShipRight");
    this.getActionMap().put("turnShipRight", new TurnShipRight());
    
    //Action handlers/listeners
    ma = new MouseHandler();
    this.addMouseListener(ma);
    this.addMouseMotionListener(ma);
    
    gamepnl = new JLayeredPane();
    gamepnl.setLayout(null); //use absolute positioning
    gamepnl.setPreferredSize(new Dimension(1200, 800));
    
    //oceangrid
    oceanGrid = new Grid();
    oceanGrid.setBounds(25, 10, 505, 505);
    gamepnl.add(oceanGrid, new Integer(1));
    
    //targetgrid
    targetGrid = new Grid();
    targetGrid.setBounds(670, 10, 505, 505);
    gamepnl.add(targetGrid, new Integer(1));
    
    targetTransp = new JLabel(new ImageIcon(transp));
    targetTransp.setBounds(670, 10, 505, 505);
    gamepnl.add(targetTransp, new Integer(2));
    
    btnConfirm = new JButton("Confirm Ship Positions!");
    btnConfirm.setActionCommand("ConfirmPositions");
    btnConfirm.setBounds(680, 450, 250, 50);
    
    ActionHandler ah = new ActionHandler();
    btnConfirm.addActionListener(ah);
    
    gamepnl.add(btnConfirm, new Integer(3));
    
    //Aircraft carrier
    ship_carrier = new Ship("aircraftCarrier", 680, 100);
    carrier = new JLabel(ship_carrier.shipIcon());
    carrier.setBounds(ship_carrier.shipBounds());
    gamepnl.add(carrier, shipLayer);
    
    //Battleship
    ship_battleship = new Ship("battleship", 740, 100);
    battleship = new JLabel(ship_battleship.shipIcon());
    battleship.setBounds(ship_battleship.shipBounds());
    gamepnl.add(battleship, shipLayer);
    
    //Submarine
    ship_submarine = new Ship("submarine", 790, 100);
    submarine = new JLabel(ship_submarine.shipIcon());
    submarine.setBounds(ship_submarine.shipBounds());
    gamepnl.add(submarine, shipLayer);
    
    //Destroyer
    ship_destroyer = new Ship("destroyer", 840, 100);
    destroyer = new JLabel(ship_destroyer.shipIcon());
    destroyer.setBounds(ship_destroyer.shipBounds());
    gamepnl.add(destroyer, shipLayer);
    
    //Patrol boat
    ship_patrolboat = new Ship("patrolboat", 890, 100);
    patrolboat = new JLabel(ship_patrolboat.shipIcon());
    patrolboat.setBounds(ship_patrolboat.shipBounds());
    gamepnl.add(patrolboat, shipLayer);
    
    
    //win/lose labels
    lblWin = new JLabel(new ImageIcon(youwin));
    lblWin.setBounds(300, 50, 600, 500);
    lblWin.setVisible(false);
    gamepnl.add(lblWin, new Integer(10));
    
    lblLose = new JLabel(new ImageIcon(youlose));
    lblLose.setBounds(300, 50, 600, 500);
    lblLose.setVisible(false);
    gamepnl.add(lblLose, new Integer(10));
    
    //attack/wait labels
    lblAttack = new JLabel(new ImageIcon(attackimg));
    lblAttack.setBounds(535, 215, 125, 94);
    lblAttack.setVisible(false);
    gamepnl.add(lblAttack, new Integer(5));
    
    lblWait = new JLabel(new ImageIcon(waitimg));
    lblWait.setBounds(535, 215, 125, 94);
    lblWait.setVisible(false);
    gamepnl.add(lblWait, new Integer(5));
    
    //help button and label
    btnHelp = new JLabel(new ImageIcon(helpButtonImg));
    btnHelp.setBounds(575, 450, 50, 50);
    gamepnl.add(btnHelp, new Integer(3));
    
    lblHelp = new JLabel(new ImageIcon(help));
    lblHelp.setBounds(425, 15, 450, 700);
    lblHelp.setVisible(false);
    gamepnl.add(lblHelp, new Integer(7));
    
    btnClose = new JLabel(new ImageIcon(closeButtonImg)); 
    btnClose.setBounds(850, 20, 20, 20);
    btnClose.setVisible(false);
    gamepnl.add(btnClose, new Integer(8));
    
    //add close game (forfeit) button
    btnCloseGame = new JLabel(new ImageIcon(closeGame));
    btnCloseGame.setBounds(575, 15, 50, 50);
    gamepnl.add(btnCloseGame, new Integer(3));
    
    this.add(gamepnl);
    this.repaint();

  }
  
  public void paintComponent(Graphics g) {
    g.drawImage(background, 0, 0, this);
    
    carrier.setLocation(ship_carrier.getLocation().x, ship_carrier.getLocation().y);
    battleship.setLocation(ship_battleship.getLocation().x, ship_battleship.getLocation().y);
    submarine.setLocation(ship_submarine.getLocation().x, ship_submarine.getLocation().y);
    destroyer.setLocation(ship_destroyer.getLocation().x, ship_destroyer.getLocation().y);
    patrolboat.setLocation(ship_patrolboat.getLocation().x, ship_patrolboat.getLocation().y);
  }
  
  public void confirmPositions() {
    if (oceanGrid.confirmPositions()) {
      //remove the confirm button
      gamepnl.remove(btnConfirm);
      gamepnl.remove(targetTransp);
      
      //save the grid to the user
      client.fillShipGrid(oceanGrid.getGrid());
      
      TargetGridAdapter tga = new TargetGridAdapter();
      targetGrid.addMouseListener(tga);
      
      //let the server know that the user is ready to compete
      MatchData md = new MatchData(client.getUserID(), client.getCompetetorID());
      client.sendMessage(md);
      
      gamepnl.revalidate();
      repaint();
    }
  }
  
  public void updateTargetGrid(boolean hit, int row, int col) {
    String sound = "sounds/miss.wav";
    if (hit) {
      JLabel lbl = new JLabel(new ImageIcon(this.hit));
      lbl.setBounds((col*45) + targetx,
          (row*45) + targety, 
          45, 45);
      gamepnl.add(lbl, new Integer(7));
      sound = "sounds/hit.wav";
    }else {
      JLabel lbl = new JLabel(new ImageIcon(this.miss));
      lbl.setBounds((col*45) + targetx,
          (row*45) + targety, 
          45, 45);
      gamepnl.add(lbl, new Integer(7));
    }
    
    revalidate();
    repaint();
    SoundPlayer.playSound(sound);
  }
  
  public void updateOceanGrid(boolean hit, int row, int col) {
    String sound = "sounds/miss.wav";
    if (hit) {
      JLabel lbl = new JLabel(new ImageIcon(this.hit));
      lbl.setBounds((col*45) + oceanx,
          (row*45) + oceany, 
          45, 45);
      gamepnl.add(lbl, new Integer(7));
      sound = "sounds/hit.wav";
    }else {
      JLabel lbl = new JLabel(new ImageIcon(this.miss));
      lbl.setBounds((col*45) + oceanx,
          (row*45) + oceany, 
          45, 45);
      gamepnl.add(lbl, new Integer(7));
    }
    
    revalidate();
    repaint();
    SoundPlayer.playSound(sound);
  }
  
  public void displayWinMessage() {
    lblWin.setVisible(true);
    revalidate();
  }
  
  public void displayLoseMessage() {
    lblLose.setVisible(true);
    revalidate();
  }
  
  public void displayAttackMessage() {
    lblAttack.setVisible(true);
    lblWait.setVisible(false);
    revalidate();
  }
  
  public void displayWaitMessage() {
    lblAttack.setVisible(false);
    lblWait.setVisible(true);
    revalidate();
  }
  
  class MouseHandler extends MouseAdapter{
    
    private Point offset;
    private String ship;
    private Point origLoc;
    
    public void mousePressed(MouseEvent e) {
      Point pressed = e.getPoint();
      
      if (ship_carrier.shipBounds().contains(pressed)) {
        ship = "carrier";
        Rectangle bounds = ship_carrier.shipBounds();
        origLoc = new Point(ship_carrier.getLocation());
        offset = new Point((pressed.x - bounds.x), (pressed.y - bounds.y));
        
      }else if (ship_battleship.shipBounds().contains(pressed)) {
        ship = "battleship";
        Rectangle bounds = ship_battleship.shipBounds();
        origLoc = new Point(ship_battleship.getLocation());
        offset = new Point((pressed.x - bounds.x), (pressed.y - bounds.y));
        
      }else if (ship_submarine.shipBounds().contains(pressed)) {
        ship = "submarine";
        Rectangle bounds = ship_submarine.shipBounds();
        origLoc = new Point(ship_submarine.getLocation());
        offset = new Point((pressed.x - bounds.x), (pressed.y - bounds.y));
        
      }else if (ship_destroyer.shipBounds().contains(pressed)) {
        ship = "destroyer";
        Rectangle bounds = ship_destroyer.shipBounds();
        origLoc = new Point(ship_destroyer.getLocation());
        offset = new Point((pressed.x - bounds.x), (pressed.y - bounds.y));
        
      }else if (ship_patrolboat.shipBounds().contains(pressed)) {
        ship = "patrolboat";
        Rectangle bounds = ship_patrolboat.shipBounds();
        origLoc = new Point(ship_patrolboat.getLocation());
        offset = new Point((pressed.x - bounds.x), (pressed.y - bounds.y));
        
      }else {
        //check to see if the help or help close buttons have been pressed
        //their bounds require an x offset of 30
        Rectangle helpBounds = new Rectangle(btnHelp.getBounds().x + 30,
            btnHelp.getBounds().y, btnHelp.getBounds().width, btnHelp.getBounds().height);
        Rectangle closeBounds = new Rectangle(btnClose.getBounds().x + 30,
            btnClose.getBounds().y, btnClose.getBounds().width, btnClose.getBounds().height);
        Rectangle quitBounds = new Rectangle(btnCloseGame.getBounds().x + 30,
            btnCloseGame.getBounds().y, btnCloseGame.getBounds().width, btnCloseGame.getBounds().height);
        
        if (helpBounds.contains(pressed)) {
          //button requesting help was pressed
          lblHelp.setVisible(true);
          btnClose.setVisible(true);
          revalidate();
        }else if(lblHelp.isVisible() && closeBounds.contains(pressed)) {
          //the help image is up and is being closed
          lblHelp.setVisible(false);
          btnClose.setVisible(false);
          revalidate();
        }else if(quitBounds.contains(pressed)) {
          System.out.println("Terminate the game, server implements how we handle this");
        }else ;
      }
    }
    
     public void mouseReleased(MouseEvent e) {
             if (offset != null) {
               //get the place in the grid
               Point pressed = e.getPoint();
               Rectangle bounds = oceanGrid.getBounds();
               
               /*
                * Place the ship in the oceanGrid or tell the grid that a ship has been moved out of bounds
                */
               if (bounds.contains(pressed)) {
             switch (ship) {
             case "carrier": 
               ship_carrier.updateLocation(pressed);
              if (!oceanGrid.placeShip(ship_carrier))  //there was overlap
                ship_carrier.setLocation(origLoc.x, origLoc.y);
              
              break;
              
             case "battleship": ship_battleship.updateLocation(pressed);
               if (!oceanGrid.placeShip(ship_battleship))  //there was overlap
                 ship_battleship.setLocation(origLoc.x, origLoc.y);
             
                break;
                
             case "submarine": ship_submarine.updateLocation(pressed);
               if (!oceanGrid.placeShip(ship_submarine))  //there was overlap
                 ship_submarine.setLocation(origLoc.x, origLoc.y);
             
            break;
            
             case "destroyer": ship_destroyer.updateLocation(pressed);
               if (!oceanGrid.placeShip(ship_destroyer))  //there was overlap
                 ship_destroyer.setLocation(origLoc.x, origLoc.y);
             
            break;
            
             case "patrolboat": ship_patrolboat.updateLocation(pressed);
               if (!oceanGrid.placeShip(ship_patrolboat))  //there was overlap
                 ship_patrolboat.setLocation(origLoc.x, origLoc.y);
             
            break;   
             }//end switch
             
             
             
               }else { //tell the oceanGrid that the ship is out of bounds
             switch (ship) {
             case "carrier": 
              if (!ship_carrier.inGrid(bounds))
                oceanGrid.moveOutofBounds(ship_carrier.getName());
              break;
             case "battleship":
               if (!ship_battleship.inGrid(bounds))
              oceanGrid.moveOutofBounds(ship_battleship.getName());
            break;
             case "submarine": 
               if (!ship_submarine.inGrid(bounds))
                oceanGrid.moveOutofBounds(ship_submarine.getName());
              break;
             case "destroyer": 
               if (!ship_destroyer.inGrid(bounds))
                oceanGrid.moveOutofBounds(ship_destroyer.getName());
              break;
             case "patrolboat": 
               if (!ship_patrolboat.inGrid(bounds))
                oceanGrid.moveOutofBounds(ship_patrolboat.getName());
              break;
             } 
               }
               
               offset = null;
               repaint();
             }
         }
     
     public void mouseDragged(MouseEvent e) {
       if (offset != null) {
         Point mp = e.getPoint();
         switch (ship) {
         case "carrier": ship_carrier.setLocation(mp.x - offset.x, mp.y - offset.y); 
          break;
         case "battleship": ship_battleship.setLocation(mp.x - offset.x, mp.y - offset.y);
          break;
         case "submarine": ship_submarine.setLocation(mp.x - offset.x, mp.y - offset.y);
          break;
         case "destroyer": ship_destroyer.setLocation(mp.x - offset.x, mp.y - offset.y);
          break;
         case "patrolboat": ship_patrolboat.setLocation(mp.x - offset.x, mp.y - offset.y);
          break;         
         }
                 repaint();
       }
     }
     
     public Point getOffset() {
       return offset;
     }
     
     public String getShip() {
       return ship;
     }
  }

  class TargetGridAdapter extends MouseAdapter{
    public void mousePressed(MouseEvent e) {
      int row = e.getPoint().y/45;
      int col = e.getPoint().x/45;
      
      
      if (client.canAttack()) {
        try {
          client.sendToServer(new AttackLocation(row, col, client.getUserID(), client.getCompetetorID()));
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }
  
  @SuppressWarnings("serial")
  class TurnShipLeft extends AbstractAction{
    public void actionPerformed(ActionEvent e) {
      //rotate the ship left, counterclockwise
      if (ma.getOffset() != null) {
        switch(ma.getShip()) {
        case "carrier": ship_carrier.rotateShip(false);
          carrier.setBounds(ship_carrier.shipBounds());
          carrier.setIcon(ship_carrier.shipIcon());
          break;
        case "battleship": ship_battleship.rotateShip(false);
          battleship.setBounds(ship_battleship.shipBounds());
          battleship.setIcon(ship_battleship.shipIcon());
          break;
        case "submarine": ship_submarine.rotateShip(false);
          submarine.setBounds(ship_submarine.shipBounds());
          submarine.setIcon(ship_submarine.shipIcon());
          break;
        case "destroyer": ship_destroyer.rotateShip(false);
          destroyer.setBounds(ship_destroyer.shipBounds());
          destroyer.setIcon(ship_destroyer.shipIcon());
          break;
        case "patrolboat": ship_patrolboat.rotateShip(false);
          patrolboat.setBounds(ship_patrolboat.shipBounds());
          patrolboat.setIcon(ship_patrolboat.shipIcon());
          break;
        }
        
        repaint();
      }
    } 
  }//turn ship left*/
  
  @SuppressWarnings("serial")
  class TurnShipRight extends AbstractAction{
    public void actionPerformed(ActionEvent e) {
      //rotate ship right, clockwise
      if (ma.getOffset() != null) {
      switch(ma.getShip()) {
        case "carrier": ship_carrier.rotateShip(true);
        carrier.setBounds(ship_carrier.shipBounds());
        carrier.setIcon(ship_carrier.shipIcon());
        break;
      case "battleship": ship_battleship.rotateShip(true);
        battleship.setBounds(ship_battleship.shipBounds());
        battleship.setIcon(ship_battleship.shipIcon());
        break;
      case "submarine": ship_submarine.rotateShip(true);
        submarine.setBounds(ship_submarine.shipBounds());
        submarine.setIcon(ship_submarine.shipIcon());
        break;
      case "destroyer": ship_destroyer.rotateShip(true);
        destroyer.setBounds(ship_destroyer.shipBounds());
        destroyer.setIcon(ship_destroyer.shipIcon());
        break;
      case "patrolboat": ship_patrolboat.rotateShip(true);
        patrolboat.setBounds(ship_patrolboat.shipBounds());
        patrolboat.setIcon(ship_patrolboat.shipIcon());
        break;
      }
        repaint();
      }
    } 
  }//turn ship right
  
  class ActionHandler implements ActionListener{

    public void actionPerformed(ActionEvent e) {
      if (e.getSource() instanceof JButton) {
        String cmd;
        cmd = e.getActionCommand();
        
        if (cmd.equals("ConfirmPositions")) {
          confirmPositions();
        }
      } 
    }
  }//end actionhandler class
  
}//end gamepanel class




