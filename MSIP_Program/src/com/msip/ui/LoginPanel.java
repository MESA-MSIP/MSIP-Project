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

public class LoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtKNumber;
	private JTextField txtAdminPass;
	private JLabel labelKNumber;
	private JLabel labeladminPass;
	private JLabel labelMESALOGO;
	private MISPCore manager;
	private boolean isTxtkNumberEnabled = true;
	private WelcomePanel welcomePanel;

	public LoginPanel(final MISPCore manager, WelcomePanel welcomePanel) {
		setBounds(new Rectangle(0, 0, 800, 480));

		this.manager = manager;
		this.welcomePanel = welcomePanel;

		
		setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		
		txtKNumber = new JTextField();
		txtKNumber.setColumns(10);
		txtKNumber.setBounds(297, 215, 211, GlobalUI.TEXTBOXHEIGHT);
		txtKNumber.addActionListener(this);
		setLayout(null);
		txtKNumber.setHorizontalAlignment(0);
		txtKNumber.setFont(new Font("Segoe UI Light", 0, 16));
		txtKNumber.setColumns(10);
		txtKNumber.setVisible(true);
		add(txtKNumber);

		this.txtAdminPass = new JPasswordField(10);
		this.txtAdminPass.setBounds(297, 297, 212, GlobalUI.TEXTBOXHEIGHT);
		this.txtAdminPass.addActionListener(this);
		this.txtAdminPass.setHorizontalAlignment(0);
		this.txtAdminPass.setFont(new Font("Segoe UI Light", 0, 16));
		this.txtAdminPass.setColumns(10);
		this.txtAdminPass.setVisible(false);
		add(this.txtAdminPass);

		this.labeladminPass = new JLabel("Password:");
		this.labeladminPass.setBounds(318, 268, 166, 22);
		this.labeladminPass.setHorizontalAlignment(0);
		this.labeladminPass.setFont(new Font("Segoe UI", 0, 24));
		this.labeladminPass.setLabelFor(this.txtAdminPass);
		add(this.labeladminPass);
		this.labeladminPass.setVisible(false);

		this.labelKNumber = new JLabel("Enter K# Here:");
		this.labelKNumber.setBounds(287, 180, 227, 22);
		this.labelKNumber.setLabelFor(txtKNumber);
		this.labelKNumber.setHorizontalAlignment(0);
		this.labelKNumber.setFont(new Font("Segoe UI", 0, 24));
		add(this.labelKNumber);

		JLabel labelWelcome = new JLabel("Welcome!");
		labelWelcome.setBounds(0, 83, 800, 84);
		labelWelcome.setHorizontalAlignment(0);
		labelWelcome.setFont(new Font("Segoe UI", 1, 60));
		Utility.iterateWelcome(labelWelcome, 3000L);
		add(labelWelcome);

		ImageIcon icon = CreateIcon("MESA.png", 315, 72);
		this.labelMESALOGO = new JLabel(icon);
		this.labelMESALOGO.setBounds(502, 11, 289, 77);
		add(this.labelMESALOGO);

		

		txtKNumber.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (txtKNumber.getText().length() >= 8) {
					e.consume();
				}
				
				//TODO fix the setText() on txtKNumber
				if (isTxtkNumberEnabled)
				{
					char keychar = e.getKeyChar();
					if ((!Character.isDigit(keychar)) && (keychar != '\b') && (keychar != '')) {
						e.consume();
					}
				}
				else
				{
					e.consume();
				}
			}

			public void keyReleased(KeyEvent e) {
				String strKNumber = txtKNumber.getText();
				if (strKNumber.length() < 8) {
					LoginPanel.this.txtAdminPass.setText("");
					LoginPanel.this.txtAdminPass.setVisible(false);
					LoginPanel.this.labeladminPass.setVisible(false);
				} else {
					int adminKNum = Integer.parseInt(strKNumber.trim());
					int adminResponse = manager.isAdmin(adminKNum);
					if (adminResponse == 1) {
						LoginPanel.this.labeladminPass.setVisible(true);
						LoginPanel.this.txtAdminPass.setVisible(true);
					}
				}
			}
		});
	}

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
			if (txtKNumber.getText().length() < 8) {
				txtKNumber.setText(GlobalUI.CLEAR);
				this.welcomePanel.setMessage(GlobalUI.help);
				showWelcomePanel();
			} else {
				try {
					String strKNumber = txtKNumber.getText();
					int kNum = Integer.parseInt(strKNumber);
					int response = this.manager.isStudent(kNum);
					int adminResponse = this.manager.isAdmin(kNum);
					if ((response == 0) && (adminResponse == 0)) {
						//new Student not in either DB
						clearTextField(this.txtKNumber);
						this.welcomePanel.setMessage(GlobalUI.newStudentMessage);
						showWelcomePanel();
					} else if ((response == 0) && (adminResponse == 1)) {
						//Admin needs to Type in Admin Password
						this.welcomePanel.setMessage(GlobalUI.InsertAdminPassMessage);
						showWelcomePanel();
					} else if ((response == 1) || (adminResponse == 1)) {
						//TODO setText() BUG:
						this.txtKNumber.setText(GlobalUI.CLEAR);
						this.manager.logStudent(kNum);						
						this.welcomePanel.setMessage(GlobalUI.loginSuccess);
						showWelcomePanel();
					} else {
						showWelcomePanel();
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();

					return;
				}
			}
		}
		if (this.txtAdminPass == e.getSource()) {
			String strAdminPass = this.txtAdminPass.getText();
			String strAdminKNum = txtKNumber.getText();
			int adminKNum = Integer.parseInt(strAdminKNum);
			int response = this.manager.verifyAdmin(adminKNum, strAdminPass);
			if (response == GlobalUI.SUCCESS) {
				if (this.manager.isStudent(adminKNum) == 1) {
					popUpResponse popUp = new popUpResponse();
					int decision = popUp.popUp();
					if (decision == 0) {
						txtKNumber.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setVisible(false);
						this.labeladminPass.setVisible(false);
						this.manager.logStudent(adminKNum);
						this.welcomePanel.setMessage(GlobalUI.loginSuccess);
						showWelcomePanel();
					} else if (decision == 1) {
						txtKNumber.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setVisible(false);
						this.labeladminPass.setVisible(false);
						showAdminPanel();
					} else if (decision == 2) {
						txtKNumber.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setText(GlobalUI.CLEAR);
						this.txtAdminPass.setVisible(false);
						this.labeladminPass.setVisible(false);
					}
				} else {
					txtKNumber.setText("");
					this.txtAdminPass.setText("");
					this.txtAdminPass.setVisible(false);
					this.labeladminPass.setVisible(false);
					showAdminPanel();
					
				}
			} else {
				//If AdminPassword is Incorrect, send Error
				
				this.welcomePanel.setMessage(GlobalUI.adminPassError);
				showWelcomePanel();
				
			}
		}
	}

	//show welcomePanel
	public void showWelcomePanel()
	{
		CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
		cl.show(this.manager.getCards(), GlobalUI.WelcomePanel);
	}
	public void showAdminPanel()
	{
		CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
		cl.show(this.manager.getCards(), GlobalUI.AdminToolsPanel);
	}
	public void clearTextField(JTextField textField)
	{
		textField.setText(GlobalUI.CLEAR);
	}
	
	public  void setScannedNumber(int kNumber) {
		txtKNumber.setText(String.valueOf(kNumber));
	}
}
