package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class LoginPanel extends JPanel
{
    private ClientGUI cg;
    private LoginListener listener;
    private JLabel bg;
    private Font labelFont;
    private Font titleFont;
    private Font buttonFont;
    private JLabel title;
    private JLabel warning;
    private JLabel userLabel;
    private JLabel passwordLabel;
    private JLabel confPWLabel;
    private JTextField user;
    private JTextField password;
    private JTextField confPW;
    private JButton login;
    private JButton createNew;
    
    //creates panel where users can log into their accounts
    //background added last to show other elements
    public LoginPanel(ClientGUI cg)
    {
    this.cg = cg;
    this.listener = listener;
    formatPanel();
    createFonts();
    createTitle();
    createWarning();
    createUser();
    createPassword();
    createConfPassword();
    createButtons();
    createBackground();
    }

    //formats JPanel size, layout
    public void formatPanel()
    {
    this.setLayout(null);
    this.setSize(cg.WIDTH, cg.HEIGHT);
    }
    
    //creates fonts to be used
    public void createFonts()
    {
    labelFont = new Font("Arial", Font.PLAIN, 20);
    titleFont = new Font("Impact", Font.BOLD, 85);
    buttonFont = new Font("Arial",Font.PLAIN, 18);
    }
    
    //puts background image
    public void createBackground()
    {
    bg = new JLabel();
    bg.setIcon(new ImageIcon("images/login2.jpg"));
    bg.setBounds(0,0,cg.WIDTH, cg.HEIGHT);
    this.add(bg);
    }
    
    //adds Title to top of screen
    public void createTitle()
    {
    title = new JLabel("BATTLESHIP");
    title.setFont(titleFont);
    title.setForeground(Color.RED);
    title.setBounds(750,0,title.getPreferredSize().width, title.getPreferredSize().height);
    this.add(title);
    }
    
    //creates and sets warning
    public void createWarning()
    {
    warning = new JLabel();
    warning.setFont(labelFont);
    this.add(warning);
    setWarning("Connected", Color.GREEN);
    }
    
    //creates and puts username label + field
    public void createUser()
    {
    userLabel = new JLabel();
    userLabel.setFont(labelFont);
    userLabel.setForeground(Color.BLACK);
    userLabel.setText("Username:");
    int x = 945 - userLabel.getPreferredSize().width;
    userLabel.setBounds(x,150,userLabel.getPreferredSize().width+10, userLabel.getPreferredSize().height);
    this.add(userLabel);
    
    user = new JTextField();
    user.setBounds(950,150,200,25);
    this.add(user);
    }
    
    //creates and puts password label + field
    public void createPassword()
    {
    passwordLabel = new JLabel();
    passwordLabel.setFont(labelFont);
    passwordLabel.setForeground(Color.BLACK);
    passwordLabel.setText("Password:");
    int x = 945 - passwordLabel.getPreferredSize().width;
    passwordLabel.setBounds(x,190,passwordLabel.getPreferredSize().width+10, passwordLabel.getPreferredSize().height);
    this.add(passwordLabel);
    
    password = new JPasswordField();
    password.setBounds(950,190,200,25);
    this.add(password);
    }
    
    //creates and puts confirm password label + field
    public void createConfPassword() 
    {
    confPWLabel = new JLabel();
    confPWLabel.setFont(labelFont);
    confPWLabel.setForeground(Color.BLACK);
    confPWLabel.setText("Confirm password:");
    int x = 945 - confPWLabel.getPreferredSize().width;
    confPWLabel.setBounds(x,230,confPWLabel.getPreferredSize().width+10, confPWLabel.getPreferredSize().height);
    this.add(confPWLabel);
    
    confPW = new JPasswordField();
    confPW.setBounds(950, 230, 200, 25);
    this.add(confPW);
    }
    
    //creates + puts buttons
    public void createButtons()
    {
      listener = new LoginListener(cg);
    login = new JButton("Login");
    login.setFont(buttonFont);
    login.setBounds(860,280,login.getPreferredSize().width+10, login.getPreferredSize().height);
    login.addActionListener(listener);
    this.add(login);
    
    createNew = new JButton("Create Account");
    createNew.setFont(buttonFont);
    createNew.setBounds(980,280,createNew.getPreferredSize().width+10,createNew.getPreferredSize().height);
    createNew.addActionListener(listener);
    this.add(createNew);
    }
    
    public void setWarning(String txt, Color color)
    {
    warning.setText(txt);
    warning.setForeground(color);
    warning.setBounds(850,115,warning.getPreferredSize().width + 10, warning.getPreferredSize().height);
    this.setVisible(true);
    }
    
    public boolean validCreateAccount ()
    {
    boolean match = password.getText().equals(confPW.getText());
    boolean nonEmpty = (!(password.getText().equals(""))) && (!(confPW.getText().equals(""))) && (!(user.getText().equals("")));
    return (match && nonEmpty);
    }
    
    public boolean validLogin () {
      return (!(user.getText().equals("")) && !(password.getText().equals("")));
    }
    
    public String getUsername() {
      return user.getText();
    }
    
    public String getPassword() {
      return password.getText();
    }
}
