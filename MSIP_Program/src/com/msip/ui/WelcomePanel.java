package com.msip.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.Timer;
import java.util.TimerTask;

import javax.accessibility.AccessibleEditableText;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.StyledEditorKit;

import com.msip.manager.MISPCore;
import sun.awt.windows.WFontConfiguration;

public class WelcomePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextField txtKNumber;
    private JTextField txtAdminPass;
    private JLabel labelKNumber;
    private JLabel labeladminPass;
    private JLabel labelMESALOGO;
    private JLabel labelSHPELOGO;
    private JLabel labelSACNASLOGO;
    private MISPCore manager;
    private StudentLoginPanel welcomePanel;
    private JTextArea errorMessage;

    public WelcomePanel(final MISPCore manager, StudentLoginPanel welcomePanel) {
        setBounds(new Rectangle(0, 0, 800, 480));

        this.manager = manager;
        this.welcomePanel = welcomePanel;


        setBorder(GlobalUI.blackBorder);
        setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);
        setLayout(null);

        //txtKNumber.setFocusable(true);

        txtKNumber = new JTextField("    00123456");
        TextPrompt prompt = new TextPrompt("    00123456", txtKNumber);
        prompt.setBounds(298, 300, 300, 300);
        txtKNumber.setForeground(Color.LIGHT_GRAY);
        txtKNumber.setBorder(GlobalUI.blackBorder);
        txtKNumber.setColumns(10);
        txtKNumber.setBounds(298, 225, 211, GlobalUI.TEXTBOXHEIGHT);
        txtKNumber.addActionListener(this);
        txtKNumber.setFocusable(true);
        txtKNumber.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

                if (txtKNumber.getText().equals("    00123456")) {
                    txtKNumber.setText("");
                    txtKNumber.setFont(GlobalUI.txtKNumberFont);
                    txtKNumber.setForeground(Color.black);
                    txtKNumber.setVisible(true);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtKNumber.getText().equals("")) {
                    txtKNumber.setText("    00123456");
                    txtKNumber.setForeground(Color.gray);

                }

            }

        });

        txtKNumber.setHorizontalAlignment(0);
        txtKNumber.setFont(GlobalUI.txtKNumberFont);
        txtKNumber.setColumns(10);
        txtKNumber.setVisible(true);
        add(txtKNumber);

        this.txtAdminPass = new

                JPasswordField(10);
        this.txtAdminPass.setBorder(GlobalUI.blackBorder);
        this.txtAdminPass.setBounds(297, 309, 212, GlobalUI.TEXTBOXHEIGHT);
        this.txtAdminPass.addActionListener(this);
        this.txtAdminPass.setHorizontalAlignment(0);
        this.txtAdminPass.setFont(GlobalUI.TextFieldFont);
        this.txtAdminPass.setColumns(10);
        this.txtAdminPass.setVisible(false);

        add(this.txtAdminPass);

        this.labeladminPass = new

                JLabel("Password:");
        this.labeladminPass.setBounds(318, 280, 166, 22);
        this.labeladminPass.setHorizontalAlignment(0);
        this.labeladminPass.setFont(GlobalUI.TextFieldLabelFont);
        this.labeladminPass.setLabelFor(this.txtAdminPass);

        add(this.labeladminPass);
        this.labeladminPass.setVisible(false);

        this.labelKNumber = new

                JLabel("Enter K#:");
        this.labelKNumber.setBounds(133, 235, 227, 22);
        this.labelKNumber.setLabelFor(txtKNumber);
        this.labelKNumber.setHorizontalAlignment(0);
        this.labelKNumber.setFont(GlobalUI.TextFieldLabelFont);

        add(this.labelKNumber);

        JLabel labelWelcome = new JLabel("Welcome!");
        labelWelcome.setBounds(0, 10, 800, 84);
        labelWelcome.setHorizontalAlignment(0);
        labelWelcome.setFont(GlobalUI.welcomeLabelFont);
        // TODO Animation speeds up over time
        //Utility.iterateWelcome(labelWelcome, 3000L);

        add(labelWelcome);

        ImageIcon icon = CreateIcon("MESALOGO.png", 690, 210);
        this.labelMESALOGO = new

                JLabel(icon);
        this.labelMESALOGO.setBounds(50, 10, 700, 230);

        add(this.labelMESALOGO);

        ImageIcon SHPEicon = CreateIcon("SHPE_logo.png", 190, 75);
        this.labelSHPELOGO = new

                JLabel(SHPEicon);
        this.labelSHPELOGO.setBounds(10, 360, 190, 75);

        add(this.labelSHPELOGO);

        ImageIcon SACNASicon = CreateIcon("SACNASLOGO.png", 250, 135);
        this.labelSACNASLOGO = new

                JLabel(SACNASicon);
        this.labelSACNASLOGO.setBounds(600, 325, 250, 135);

        add(this.labelSACNASLOGO);

        errorMessage = new

                JTextArea("Insert Your Password.");
        errorMessage.setFont(GlobalUI.LableFont);
        errorMessage.setBounds(525, 237, 266, 62);
        errorMessage.setEditable(false);
        errorMessage.setVisible(false);
        errorMessage.setForeground(GlobalUI.redColor);
        errorMessage.setBackground(GlobalUI.GLOBAL_BACKGROUND_COLOR);

        add(errorMessage);


        addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                txtKNumber.requestFocusInWindow();
            }
        });
        txtKNumber.addKeyListener(new

                                          KeyAdapter() {
                                              public void keyTyped(KeyEvent e) {
                                                  /*
                                                   * Restricts the textField to only 8 characters, and only
                                                   * numbers.
                                                   */
                                                  if (txtKNumber.getText().length() >= 8) {
                                                      e.consume();
                                                  }
                                                  char keychar = e.getKeyChar();
                                                  if ((!Character.isDigit(keychar)) && (keychar != '\b')
                                                          && (keychar != '')) {
                                                      e.consume();
                                                  }
                                              }

                                              public void keyReleased(KeyEvent e) {
                                                  /*
                                                   * Once the textField is less than 8 characters, hides the
                                                   * password textField.
                                                   */
                                                  String strKNumber = txtKNumber.getText();
                                                  if (strKNumber.length() < 8) {
                                                      txtAdminPass.setText(GlobalUI.CLEAR);
                                                      txtAdminPass.setVisible(false);
                                                      labeladminPass.setVisible(false);
                                                  } else {
                                                      // Checks if the kNumber is an Admin kNumber. Sets the admin
                                                      // password Visible if true.
                                                      int adminKNum = Integer.parseInt(strKNumber.trim());
                                                      int adminResponse = manager.isAdmin(adminKNum);
                                                      if (adminResponse == 1) {
                                                          labeladminPass.setVisible(true);
                                                          txtAdminPass.setVisible(true);
                                                      }
                                                  }
                                              }
                                          });
        txtKNumber.addKeyListener(new

                                          KeyAdapter() {
                                              public void keyTyped(KeyEvent e) {
                                                  /*
                                                   * Restricts the textField to only 8 characters, and only
                                                   * numbers.
                                                   */
                                                  if (txtKNumber.getText().length() >= 8) {
                                                      e.consume();
                                                  }
                                                  char keychar = e.getKeyChar();
                                                  if ((!Character.isDigit(keychar)) && (keychar != '\b')
                                                          && (keychar != '')) {
                                                      e.consume();
                                                  }
                                              }

                                              public void keyReleased(KeyEvent e) {
                                                  /*
                                                   * Once the textField is less than 8 characters, hides the
                                                   * password textField.
                                                   */
                                                  String strKNumber = txtKNumber.getText();
                                                  if (strKNumber.length() < 8) {
                                                      WelcomePanel.this.txtAdminPass.setText(GlobalUI.CLEAR);
                                                      WelcomePanel.this.txtAdminPass.setVisible(false);
                                                      WelcomePanel.this.labeladminPass.setVisible(false);
                                                  } else {
                                                      // Checks if the kNumber is an Admin kNumber. Sets the admin
                                                      // password Visible if true.
                                                      int adminKNum = Integer.parseInt(strKNumber.trim());
                                                      int adminResponse = manager.isAdmin(adminKNum);
                                                      if (adminResponse == 1) {
                                                          WelcomePanel.this.labeladminPass.setVisible(true);
                                                          WelcomePanel.this.txtAdminPass.setVisible(true);
                                                      }
                                                  }
                                              }
                                          });
    }

    /**
     * Grabs the MESA Logo from folder image and returns the image.
     *
     * @param filename
     * @param width
     * @param height
     * @return
     */
    private ImageIcon CreateIcon(String filename, int width, int height) {
        InputStream url = getClass().getResourceAsStream("/images/" + filename);
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon image = new ImageIcon(img.getScaledInstance(width, height, 0));
        return image;
    }

    public void actionPerformed(ActionEvent e) {
        if (txtKNumber == e.getSource()) {
            // If the User inputs less than 8 characters, to show the help
            // message.
            if (txtKNumber.getText().length() < 8) {
                txtKNumber.setText(GlobalUI.CLEAR);
                txtKNumber.setBorder(GlobalUI.redBorder);
                errorMessage.setVisible(true);
                errorMessage.setText(GlobalUI.errorMessage);
                turnOffMessage(errorMessage);
            } else {
                try {
                    // Get the Text of txtKNumber, and parse it into an Integer.
                    String strKNumber = txtKNumber.getText();
                    int kNum = Integer.parseInt(strKNumber);
                    int studentResponse = this.manager.isStudent(kNum);
                    int adminResponse = this.manager.isAdmin(kNum);

                    if ((studentResponse == GlobalUI.FAIL)
                            && (adminResponse == GlobalUI.FAIL)) {
                        // if the student is not recognized in either the Admin
                        // or Student DB, to show the newStudentMessage.
                        clearTextField(this.txtKNumber);
                        this.txtKNumber.setBorder(GlobalUI.redBorder);
                        errorMessage.setVisible(true);
                        errorMessage.setText(GlobalUI.errorMessage);
                        turnOffMessage(errorMessage);

                    } else if ((studentResponse == GlobalUI.FAIL)
                            && (adminResponse == GlobalUI.SUCCESS)) {
                        // Admin needs to Type in Admin Password
                        // Border Red, Prompt to say they need to type in their
                        // password.
                        txtAdminPass.setBorder(GlobalUI.redBorder);
                        errorMessage.setText(GlobalUI.InsertAdminPassMessage);
                        errorMessage.setVisible(true);
                        turnOffMessage(errorMessage);

                    } else if ((studentResponse == GlobalUI.SUCCESS) && (adminResponse == GlobalUI.SUCCESS)) {
                        txtAdminPass.setBorder(GlobalUI.redBorder);
                        errorMessage.setText(GlobalUI.InsertAdminPassMessage);
                        errorMessage.setVisible(true);
                        turnOffMessage(errorMessage);
                    } else if ((studentResponse == GlobalUI.SUCCESS)
                            || (adminResponse == GlobalUI.SUCCESS)) {

                        this.txtKNumber.setText(GlobalUI.CLEAR);
                        this.txtKNumber.setBorder(GlobalUI.blackBorder);
                        this.manager.logStudent(kNum);
                        this.welcomePanel.setMessage(GlobalUI.loginSuccess);
                        showWelcomePanel();
                    }
                } catch (NumberFormatException e1) {
                    e1.printStackTrace();
                    return;
                }
            }
        }
        if (this.txtAdminPass == e.getSource()) {
            //
            String strAdminPass = this.txtAdminPass.getText();
            String strAdminKNum = txtKNumber.getText();
            int adminKNum = Integer.parseInt(strAdminKNum);
            int response = this.manager.verifyAdmin(adminKNum, strAdminPass);
            if (response == GlobalUI.SUCCESS) {
                if (this.manager.isStudent(adminKNum) == 1) {
                    popUpResponse popUp = new popUpResponse();
                    int decision = popUp.popUp();
                    if (decision == 0) {
                        // If they decide to login as a student:
                        txtKNumber.setText(GlobalUI.CLEAR);
                        this.txtAdminPass.setText(GlobalUI.CLEAR);
                        this.txtAdminPass.setVisible(false);
                        this.txtAdminPass.setBorder(GlobalUI.blackBorder);
                        this.labeladminPass.setVisible(false);
                        this.manager.logStudent(adminKNum);
                        this.welcomePanel.setMessage(GlobalUI.loginSuccess);
                        showWelcomePanel();
                    } else if (decision == 1) {
                        // If they decide to login as an admin:
                        txtKNumber.setText(GlobalUI.CLEAR);
                        this.txtAdminPass.setText(GlobalUI.CLEAR);
                        this.txtAdminPass.setVisible(false);
                        this.txtKNumber.setBorder(GlobalUI.blackBorder);
                        this.txtAdminPass.setBorder(GlobalUI.blackBorder);
                        this.labeladminPass.setVisible(false);
                        showAdminPanel();
                    } else if (decision == 2) {
                        // If they do not want to login:
                        txtKNumber.setText(GlobalUI.CLEAR);
                        this.txtAdminPass.setText(GlobalUI.CLEAR);
                        this.txtKNumber.setBorder(GlobalUI.blackBorder);
                        this.txtAdminPass.setBorder(GlobalUI.blackBorder);
                        this.txtAdminPass.setVisible(false);
                        this.labeladminPass.setVisible(false);
                    }
                } else {
                    // If it is not a Student, But an Admin
                    this.txtKNumber.setText(GlobalUI.CLEAR);
                    this.txtAdminPass.setText(GlobalUI.CLEAR);
                    this.txtKNumber.setBorder(GlobalUI.blackBorder);
                    this.txtAdminPass.setBorder(GlobalUI.blackBorder);
                    this.txtAdminPass.setVisible(false);
                    this.labeladminPass.setVisible(false);
                    showAdminPanel();

                }
            } else {
                // If AdminPassword is Incorrect, send Error
                errorMessage.setText(GlobalUI.adminPassError);
                errorMessage.setVisible(true);
                txtAdminPass.setBorder(GlobalUI.redBorder);
                turnOffMessage(errorMessage);
            }
        }
    }

    /**
     * Shows the ToastPanel.
     */
    public void showWelcomePanel() {
        CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
        cl.show(this.manager.getCards(), GlobalUI.WelcomePanel);
    }

    /**
     * Shows the AdminToolsPanel.
     */
    public void showAdminPanel() {
        CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
        cl.show(this.manager.getCards(), GlobalUI.AdminToolsPanel);
    }

    /**
     * clears the TextField.
     *
     * @param textField
     */
    public void clearTextField(JTextField textField) {
        textField.setText(GlobalUI.CLEAR);
    }

    public void setScannedNumber(int kNumber) {
        txtKNumber.setText(String.valueOf(kNumber));
    }

    /**
     * Turns off the message after 3 seconds.
     *
     * @param label
     */
    public void turnOffMessage(final JTextArea label) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                label.setVisible(false);
            }

        }, 4000L);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    }

}




