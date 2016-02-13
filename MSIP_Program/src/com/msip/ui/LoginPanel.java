package com.msip.ui;

import com.msip.external.Utility;
import com.msip.manager.MISPCore;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class LoginPanel
  extends JPanel
  implements ActionListener
{
	private static final long serialVersionUID = 1L;
private static JTextField txtKNumber;
  private JTextField txtAdminPass;
  private JLabel labelToast;
  private JLabel labelKNumber;
  private JLabel labeladminPass;
  private JLabel labelHelp;
  private JLabel labelMESALOGO;
  private JLabel labeladminPassError;
  private JTextArea txtErrorMessage;
  private MISPCore manager;
  private JLabel labelInsertAdminPass;
  
  public LoginPanel(final MISPCore manager)
  {
    setBounds(new Rectangle(0, 0, 800, 480));
    
    this.manager = manager;
    
    setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
    setBackground(Color.WHITE);
    
    NumberFormat numberFormat = NumberFormat.getNumberInstance();
    numberFormat.setGroupingUsed(false);
    txtKNumber = new JFormattedTextField(numberFormat);
    txtKNumber.setColumns(10);
    
    txtKNumber.setBounds(298, 260, 211, 40);
    txtKNumber.addActionListener(this);
    setLayout(null);
    txtKNumber.setHorizontalAlignment(0);
    txtKNumber.setFont(new Font("Segoe UI Light", 0, 16));
    txtKNumber.setColumns(10);
    add(txtKNumber);
    
    this.txtAdminPass = new JPasswordField(10);
    this.txtAdminPass.setBounds(297, 375, 212, 40);
    this.txtAdminPass.addActionListener(this);
    this.txtAdminPass.setHorizontalAlignment(0);
    this.txtAdminPass.setFont(new Font("Segoe UI Light", 0, 16));
    this.txtAdminPass.setColumns(10);
    this.txtAdminPass.setVisible(false);
    add(this.txtAdminPass);
    
    this.labeladminPass = new JLabel("Password:");
    this.labeladminPass.setBounds(321, 340, 166, 22);
    this.labeladminPass.setHorizontalAlignment(0);
    this.labeladminPass.setFont(new Font("Segoe UI", 0, 24));
    this.labeladminPass.setLabelFor(this.txtAdminPass);
    add(this.labeladminPass);
    this.labeladminPass.setVisible(false);
    
    this.labelKNumber = new JLabel("Enter K# Here:");
    this.labelKNumber.setBounds(282, 225, 227, 22);
    this.labelKNumber.setLabelFor(txtKNumber);
    this.labelKNumber.setHorizontalAlignment(0);
    this.labelKNumber.setFont(new Font("Segoe UI", 0, 24));
    add(this.labelKNumber);
    
    JLabel labelWelcome = new JLabel("Welcome!");
    labelWelcome.setBounds(0, 116, 800, 84);
    labelWelcome.setHorizontalAlignment(0);
    labelWelcome.setFont(new Font("Segoe UI", 1, 60));
    Utility.iterateWelcome(labelWelcome, 3000L);
    add(labelWelcome);
    
    this.labelToast = new JLabel("You Have Logged In.");
    this.labelToast.setBounds(531, 264, 215, 32);
    this.labelToast.setVisible(false);
    this.labelToast.setHorizontalAlignment(0);
    this.labelToast.setFont(new Font("Segoe UI", 0, 17));
    add(this.labelToast);
    
    ImageIcon icon = CreateIcon("MESA.png", 315, 72);
    
    this.labeladminPassError = new JLabel("Password or K# is incorrect.");
    this.labeladminPassError.setBounds(531, 384, 261, 22);
    this.labeladminPassError.setHorizontalAlignment(0);
    this.labeladminPassError.setFont(new Font("Segoe UI", 0, 18));
    this.labeladminPassError.setVisible(false);
    
    this.labelHelp = new JLabel("K# is Incorrect.  Try Again.");
    this.labelHelp.setToolTipText("");
    this.labelHelp.setBounds(521, 259, 271, 43);
    this.labelHelp.setFont(new Font("Segoe UI", 0, 18));
    this.labelHelp.setHorizontalAlignment(0);
    this.labelHelp.setVisible(false);
    this.labelMESALOGO = new JLabel(icon);
    this.labelMESALOGO.setBounds(503, 3, 289, 77);
    add(this.labelMESALOGO);
    add(this.labelHelp);
    add(this.labeladminPassError);
    
    this.txtErrorMessage = new JTextArea();
    this.txtErrorMessage.setToolTipText("");
    this.txtErrorMessage.setText("See a MESA Advisor to Sign In.");
    this.txtErrorMessage.setFont(new Font("Segoe UI", 0, 17));
    this.txtErrorMessage.setBounds(529, 266, 271, 43);
    this.txtErrorMessage.setVisible(false);
    add(this.txtErrorMessage);
    
    this.labelInsertAdminPass = new JLabel("Enter your Password.");
    this.labelInsertAdminPass.setFont(new Font("Segoe UI", 0, 18));
    this.labelInsertAdminPass.setBounds(521, 259, 225, 43);
    this.labelInsertAdminPass.setVisible(false);
    add(this.labelInsertAdminPass);
    
    txtKNumber.addKeyListener(new KeyAdapter()
    {
      public void keyTyped(KeyEvent e)
      {
        if (LoginPanel.txtKNumber.getText().length() >= 8) {
          e.consume();
        }
        char keychar = e.getKeyChar();
        if ((!Character.isDigit(keychar)) && (keychar != '\b') && 
          (keychar != '')) {
          e.consume();
        }
      }
      
      public void keyReleased(KeyEvent e)
      {
        String strKNumber = LoginPanel.txtKNumber.getText();
        if (strKNumber.length() < 8)
        {
          LoginPanel.this.txtAdminPass.setText("");
          LoginPanel.this.txtAdminPass.setVisible(false);
          LoginPanel.this.labeladminPass.setVisible(false);
        }
        else
        {
          int adminKNum = Integer.parseInt(strKNumber.trim());
          int adminResponse = manager.isAdmin(adminKNum);
          if (adminResponse == 1)
          {
            LoginPanel.this.labeladminPass.setVisible(true);
            LoginPanel.this.txtAdminPass.setVisible(true);
          }
        }
      }
    });
  }
  
  private void turnOffInsertAdminPass()
  {
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        LoginPanel.this.labelInsertAdminPass.setVisible(false);
      }
    }, 3000L);
  }
  
  private void turnOffAdminError()
  {
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        LoginPanel.this.labeladminPassError.setVisible(false);
      }
    }, 3000L);
  }
  
  private void turnOffHelp()
  {
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        LoginPanel.this.labelHelp.setVisible(false);
      }
    }, 3000L);
  }
  
  private void turnOffToast()
  {
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        LoginPanel.this.labelToast.setVisible(false);
      }
    }, 3000L);
  }
  
  private void turnOffErrorMessage()
  {
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      public void run()
      {
        LoginPanel.this.txtErrorMessage.setVisible(false);
      }
    }, 3000L);
  }
  
  private ImageIcon CreateIcon(String filename, int width, int height)
  {
    InputStream url = getClass().getResourceAsStream("/images/" + filename);
    BufferedImage img = null;
    try
    {
      img = ImageIO.read(url);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    ImageIcon image = new ImageIcon(img.getScaledInstance(width, height, 0));
    return image;
  }
  
  public void actionPerformed(ActionEvent e)
  {
    if (txtKNumber == e.getSource()) {
      if (txtKNumber.getText().length() < 8)
      {
        this.labelHelp.setVisible(true);
        txtKNumber.setText("");
        turnOffHelp();
      }
      else
      {
        try
        {
          String strKNumber = txtKNumber.getText();
          int kNum = Integer.parseInt(strKNumber);
          int response = this.manager.isStudent(kNum);
          int adminResponse = this.manager.isAdmin(kNum);
          if ((response == 0) && (adminResponse == 0))
          {
            this.txtErrorMessage.setVisible(true);
            txtKNumber.setText("");
            turnOffErrorMessage();
          }
          else if ((response == 0) && (adminResponse == 1))
          {
            this.labelInsertAdminPass.setVisible(true);
            turnOffInsertAdminPass();
          }
          else if ((response == 1) || (adminResponse == 1))
          {
            this.labelHelp.setVisible(false);
            this.labelToast.setVisible(true);
            
            this.manager.logStudent(kNum);
            
            txtKNumber.setText("");
            
            turnOffToast();
          }
          else
          {
            this.labelHelp.setVisible(true);
            this.labelToast.setVisible(false);
          }
        }
        catch (NumberFormatException e1)
        {
          e1.printStackTrace();
          
          return;
        }
      }
    }
    if (this.txtAdminPass == e.getSource())
    {
      String strAdminPass = this.txtAdminPass.getText();
      String strAdminKNum = txtKNumber.getText();
      int adminKNum = Integer.parseInt(strAdminKNum);
      int response = this.manager.verifyAdmin(adminKNum, strAdminPass);
      if (response == 1)
      {
        if (this.manager.isStudent(adminKNum) == 1)
        {
          popUpResponse popUp = new popUpResponse();
          int decision = popUp.popUp();
          if (decision == 0)
          {
            this.labelHelp.setVisible(false);
            this.labelToast.setVisible(true);
            txtKNumber.setText("");
            this.txtAdminPass.setText("");
            this.txtAdminPass.setVisible(false);
            this.labeladminPass.setVisible(false);
            
            this.manager.logStudent(adminKNum);
            
            turnOffToast();
          }
          else if (decision == 1)
          {
            CardLayout cl = (CardLayout)this.manager.getCards().getLayout();
            cl.show(this.manager.getCards(), "AdminToolsPanel");
            txtKNumber.setText("");
            this.txtAdminPass.setText("");
            this.txtAdminPass.setVisible(false);
            this.labeladminPass.setVisible(false);
          }
          else if (decision == 2)
          {
            txtKNumber.setText("");
            this.txtAdminPass.setText("");
            this.txtAdminPass.setVisible(false);
            this.labeladminPass.setVisible(false);
            this.labelToast.setVisible(false);
          }
        }
        else
        {
          CardLayout cl = (CardLayout)this.manager.getCards().getLayout();
          cl.show(this.manager.getCards(), "AdminToolsPanel");
          txtKNumber.setText("");
          this.txtAdminPass.setText("");
          this.txtAdminPass.setVisible(false);
          this.labeladminPass.setVisible(false);
        }
      }
      else
      {
        this.labeladminPassError.setVisible(true);
        turnOffAdminError();
      }
    }
  }
  
  public static void setScannedNumber(int kNumber)
  {
    txtKNumber.setText(String.valueOf(kNumber));
  }
}
