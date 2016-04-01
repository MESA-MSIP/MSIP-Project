package com.msip.ui;

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
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.msip.external.Utility;
import com.msip.manager.MISPCore;

public class LoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtKNumber;
	private JTextField txtAdminPass;
	private JLabel labelKNumber;
	private JLabel labeladminPass;
	private JLabel labelMESALOGO;
	private MISPCore manager;
	private ToastPanel welcomePanel;
	private JTextArea errorMessage;

	public LoginPanel(final MISPCore manager, ToastPanel welcomePanel) {
		setBounds(new Rectangle(0, 0, 800, 480));

		this.manager = manager;
		this.welcomePanel = welcomePanel;

		
		setBorder(GlobalUI.blackBorder);
		setBackground(Color.WHITE);
		setLayout(null);
		

		txtKNumber = new JTextField();
		txtKNumber.setBorder(GlobalUI.blackBorder);
		txtKNumber.setColumns(10);
		txtKNumber.setBounds(297, 215, 211, GlobalUI.TEXTBOXHEIGHT);
		txtKNumber.addActionListener(this);
		
		
		txtKNumber.setHorizontalAlignment(0);
		txtKNumber.setFont(GlobalUI.TextFieldFont);
		txtKNumber.setColumns(10);
		txtKNumber.setVisible(true);
		add(txtKNumber);

		this.txtAdminPass = new JPasswordField(10);
		this.txtAdminPass.setBorder(GlobalUI.blackBorder);
		this.txtAdminPass.setBounds(297, 297, 212, GlobalUI.TEXTBOXHEIGHT);
		this.txtAdminPass.addActionListener(this);
		this.txtAdminPass.setHorizontalAlignment(0);
		this.txtAdminPass.setFont(GlobalUI.TextFieldFont);
		this.txtAdminPass.setColumns(10);
		this.txtAdminPass.setVisible(false);
		add(this.txtAdminPass);

		this.labeladminPass = new JLabel("Password:");
		this.labeladminPass.setBounds(318, 268, 166, 22);
		this.labeladminPass.setHorizontalAlignment(0);
		this.labeladminPass.setFont(GlobalUI.TextFieldLabelFont);
		this.labeladminPass.setLabelFor(this.txtAdminPass);
		add(this.labeladminPass);
		this.labeladminPass.setVisible(false);

		this.labelKNumber = new JLabel("Enter K# Here:");
		this.labelKNumber.setBounds(287, 180, 227, 22);
		this.labelKNumber.setLabelFor(txtKNumber);
		this.labelKNumber.setHorizontalAlignment(0);
		this.labelKNumber.setFont(GlobalUI.TextFieldLabelFont);
		add(this.labelKNumber);

		JLabel labelWelcome = new JLabel("Welcome!");
		labelWelcome.setBounds(0, 83, 800, 84);
		labelWelcome.setHorizontalAlignment(0);
		labelWelcome.setFont(GlobalUI.welcomeLabelFont);
		Utility.iterateWelcome(labelWelcome, 3000L);
		add(labelWelcome);

		ImageIcon icon = CreateIcon("MESA.png", 315, 72);
		this.labelMESALOGO = new JLabel(icon);
		this.labelMESALOGO.setBounds(502, 11, 289, 77);
		add(this.labelMESALOGO);

		errorMessage = new JTextArea("Insert Your Password.");
		errorMessage.setFont(GlobalUI.LableFont);
		errorMessage.setBounds(525, 215, 266, 62);
		errorMessage.setEditable(false);
		errorMessage.setVisible(false);
		errorMessage.setForeground(GlobalUI.redColor);
		add(errorMessage);

		txtKNumber.addKeyListener(new KeyAdapter() {
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
					LoginPanel.this.txtAdminPass.setText(GlobalUI.CLEAR);
					LoginPanel.this.txtAdminPass.setVisible(false);
					LoginPanel.this.labeladminPass.setVisible(false);
				} else {
					// Checks if the kNumber is an Admin kNumber. Sets the admin
					// password Visible if true.
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

					} 
					else if((studentResponse == GlobalUI.SUCCESS) && (adminResponse == GlobalUI.SUCCESS))
					{
						txtAdminPass.setBorder(GlobalUI.redBorder);
						errorMessage.setText(GlobalUI.InsertAdminPassMessage);
						errorMessage.setVisible(true);
						turnOffMessage(errorMessage);			
					}
					else if ((studentResponse == GlobalUI.SUCCESS)
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
}
