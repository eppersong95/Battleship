package client;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//this panel allows user to enter server IP address + port number
public class ConnectionPanel extends JPanel {
  private ClientGUI cg;
    private JLabel bg;
    private Font labelFont;
    private Font titleFont;
    private Font buttonFont;
    private JLabel title;
    private JLabel warning;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JTextField ip;
    private JTextField port;
    private JButton connect;
    
    //background added last to show other elements
    public ConnectionPanel(ClientGUI cg)
    {
    this.cg = cg;
    formatPanel();
    createFonts();
    createTitle();
    warning = new JLabel();
    setWarning("", Color.RED);
    warning.setVisible(false);
    this.add(warning);
    createIP();
    createPort();
    createConnect();
    createBackground();
    this.setVisible(true);
    }
    
    public void formatPanel()
    {
    this.setLayout(null);
    this.setSize(cg.WIDTH, cg.HEIGHT);
    }
    
    //defines the fonts to be used
    public void createFonts()
    {
    labelFont = new Font("Arial", Font.PLAIN, 20);
    titleFont = new Font("Impact", Font.BOLD, 85);
    buttonFont = new Font("Arial", Font.PLAIN, 18);
    }
    
    //sets background image
    public void createBackground()
    {
    bg = new JLabel();
    bg.setIcon(new ImageIcon("images/login2.jpg"));
    bg.setBounds(0,0,cg.WIDTH, cg.HEIGHT);
    this.add(bg);
    }
    
    //creates title
    public void createTitle()
    {
    title = new JLabel("BATTLESHIP");
    title.setFont(titleFont);
    title.setForeground(Color.RED);
    title.setBounds(750,0,title.getPreferredSize().width, title.getPreferredSize().height);
    this.add(title);
    }
    
    //sets warning/status
    public void setWarning(String txt, Color color)
    {
    warning.setText(txt);
    warning.setFont(labelFont);
    warning.setForeground(color);
    warning.setBounds(850,110,warning.getPreferredSize().width,warning.getPreferredSize().height);
    warning.setVisible(true);
    this.setVisible(true);
    }
    
    //sets IP label + text field
    public void createIP()
    {
    ipLabel = new JLabel("Server IP Address:");
    ipLabel.setFont(labelFont);
    ipLabel.setForeground(Color.BLACK);
    int x = 945 - ipLabel.getPreferredSize().width;
    ipLabel.setBounds(x,150,ipLabel.getPreferredSize().width, ipLabel.getPreferredSize().height);
    this.add(ipLabel);
    
    ip = new JTextField();
    ip.setBounds(950,150,200,25);
    this.add(ip);
    }
    
    //sets port label + text field
    public void createPort()
    {
    portLabel = new JLabel();
    portLabel.setFont(labelFont);
    portLabel.setForeground(Color.BLACK);
    portLabel.setText("Port #:");
    int x = 945 - portLabel.getPreferredSize().width;
    portLabel.setBounds(x,190,portLabel.getPreferredSize().width, portLabel.getPreferredSize().height);
    this.add(portLabel);
    
    port = new JTextField();
    port.setBounds(950,190,200,25);
    this.add(port);
    }
    
    //creates connect button
    public void createConnect()
    {
    connect = new JButton("Connect");
    connect.setFont(buttonFont);
    connect.setBounds(950,240,connect.getPreferredSize().width + 10, connect.getPreferredSize().height);
    connect.addActionListener(new ConnectionListener(cg));
    this.add(connect);
    }
    
    public String getHost() 
    {
      return ip.getText();
    }
    
    public int getPort()
    {
      try {
      int toReturn = Integer.parseInt(port.getText());
      return toReturn;
    } catch (Exception e) {
      return 8000;
    }
    }
}

