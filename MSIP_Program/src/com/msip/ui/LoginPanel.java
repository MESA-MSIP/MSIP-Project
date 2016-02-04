package com.msip.ui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.awt.CardLayout;
import java.awt.Color;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import javax.swing.border.MatteBorder;

import com.msip.ui.popUpResponse;
import com.msip.external.Utility;
import com.msip.manager.MISPCore;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.Rectangle;
import javax.swing.JTextArea;

/**
 * @author Chris
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {
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

	/**
	 * @param manager
	 */
	public LoginPanel(final MISPCore manager) {
		setBounds(new Rectangle(0, 0, 800, 480));

		this.manager = manager;

		// Setup Layout, Bounds
		// TODO add to Global.
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(Color.WHITE);

		// Format the JTextField to only accept Numbers
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setGroupingUsed(false);
		txtKNumber = new JFormattedTextField(numberFormat);
		txtKNumber.setColumns(10);

		// JTxtField kNumber
		// txtKNumber = new JTextField();
		txtKNumber.setBounds(330, 212, 136, 28);
		txtKNumber.addActionListener(this);
		setLayout(null);
		txtKNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtKNumber.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtKNumber.setColumns(10);
		add(txtKNumber);

		// Admin Password Textfield
		txtAdminPass = new JPasswordField(10);
		txtAdminPass.setBounds(330, 275, 136, 28);
		txtAdminPass.addActionListener(this);
		txtAdminPass.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdminPass.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtAdminPass.setColumns(10);
		txtAdminPass.setVisible(false);
		add(txtAdminPass);

		// Title Welcome!
		JLabel labelWelcome = new JLabel("Welcome!");
		labelWelcome.setBounds(0, 115, 800, 57);
		labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		labelWelcome.setFont(new Font("Segoe UI", Font.BOLD, 42));
		Utility.iterateWelcome(labelWelcome, 3000);
		add(labelWelcome);

		// Animated Toast that shows Logged In:
		labelToast = new JLabel("You Have Logged In.");
		labelToast.setBounds(514, 215, 170, 22);
		labelToast.setVisible(false);
		labelToast.setHorizontalAlignment(SwingConstants.CENTER);
		labelToast.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		add(labelToast);

		// kNum Description
		labelKNumber = new JLabel("Enter K# Here:");
		labelKNumber.setBounds(330, 190, 98, 22);
		labelKNumber.setLabelFor(txtKNumber);
		labelKNumber.setHorizontalAlignment(SwingConstants.CENTER);
		labelKNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		add(labelKNumber);

		// admin password description:
		labeladminPass = new JLabel("Password:");
		labeladminPass.setBounds(336, 253, 70, 22);
		labeladminPass.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPass.setLabelFor(txtAdminPass);
		add(labeladminPass);
		labeladminPass.setVisible(false);

		// Add Mesa Logo

		ImageIcon icon = CreateIcon(GlobalUI.MESAURL, 315, 72);
		labelMESALOGO = new JLabel(icon);
		labelMESALOGO.setBounds(474, 11, 315, 72);
		add(labelMESALOGO);

		// AdminPass/kNum Error TextTip
		labeladminPassError = new JLabel("Password or K# is incorrect.");
		labeladminPassError.setBounds(504, 278, 191, 22);
		labeladminPassError.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPassError.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPassError.setVisible(false);
		
				// kNum# incorrect texttip
				labelHelp = new JLabel("K# is Incorrect.  Try Again.");
				labelHelp.setToolTipText("");
				labelHelp.setBounds(504, 200, 195, 52);
				labelHelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
				labelHelp.setHorizontalAlignment(SwingConstants.CENTER);
				labelHelp.setVisible(false);
				add(labelHelp);
		add(labeladminPassError);
		
		txtErrorMessage = new JTextArea();
		txtErrorMessage.setToolTipText("");
		txtErrorMessage.setText("See a MESA Advisor to Sign In.");
		txtErrorMessage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		txtErrorMessage.setBounds(514, 213, 232, 28);
		txtErrorMessage.setVisible(false);
		add(txtErrorMessage);
		
		labelInsertAdminPass = new JLabel("Enter your Password.");
		labelInsertAdminPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelInsertAdminPass.setBounds(514, 212, 204, 28);
		labelInsertAdminPass.setVisible(false);
		add(labelInsertAdminPass);

		txtKNumber.addKeyListener(new KeyAdapter() {

			/**
			 * Checks if keyTyped is a digit. If Not, Deletes the invalid key.
			 * Checks if String is more 8 characters. String is now formatted to
			 * not include commas.
			 * 
			 */
			public void keyTyped(KeyEvent e) {
				if (txtKNumber.getText().length() >= 8)
					e.consume();

				char keychar = e.getKeyChar();
				if (!(Character.isDigit(keychar) || (keychar == KeyEvent.VK_BACK_SPACE)
						|| (keychar == KeyEvent.VK_DELETE))) {
					e.consume();
				}
			}

			public void keyReleased(KeyEvent e) {
				
				//check if an kNum is an admin
				
					String strKNumber = txtKNumber.getText();
					
			
				
				if (strKNumber.length() < GlobalUI.kNumMax) {
					txtAdminPass.setText("");
					txtAdminPass.setVisible(false);
					labeladminPass.setVisible(false);
				} else {
					int adminKNum = Integer.parseInt(strKNumber.trim());
					int adminResponse = manager.isAdmin(adminKNum);
					if (adminResponse == GlobalUI.SUCCESS)
					{
						labeladminPass.setVisible(true);
						txtAdminPass.setVisible(true);
					}
					
				}
			}
		});
	}
	private void turnOffInsertAdminPass()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				labelInsertAdminPass.setVisible(false);
			}
		}, 3000);
	}
	private void turnOffAdminError()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask()
				{
				public void run()
				{
					labeladminPassError.setVisible(false);
				}
				}, 3000);
	}
	private void turnOffHelp()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				labelHelp.setVisible(false);
			}
		}, 3000);
	}
	private void turnOffToast() {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				labelToast.setVisible(false);
			}
		}, 3000);
	}
	private void turnOffErrorMessage()
	{
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				txtErrorMessage.setVisible(false);;
			}
		}, 3000);
	}

	/**
	 * Grab a corrected size of MESA Logo.
	 * 
	 * @param filename
	 * @param width
	 * @param height
	 * @return image
	 */
	private ImageIcon CreateIcon(String filename, int width, int height) {

		InputStream url = this.getClass().getResourceAsStream("/images/" + filename);
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(img.getScaledInstance(width, height, 0));
		return image;
	}

	/**
	 * Multiple uses of actionPerformed: kNumberTextField: Check the string
	 * inputed, if correct, show the toast. adminPass: check the password: if
	 * correct, show AdminTools, if not show an error message.
	 * 
	 * @param event
	 */
	public void actionPerformed(ActionEvent e) {

		if (txtKNumber == e.getSource()) {
			//check if they are trying to submit a less than 8 character password:
			if (txtKNumber.getText().length() < 8)
			{
				labelHelp.setVisible(true);
				txtKNumber.setText("");
				turnOffHelp();
			}
			else
			{
				
				try {
					String strKNumber = txtKNumber.getText();
					int kNum = Integer.parseInt(strKNumber);
					int response = manager.isStudent(kNum);
					int adminResponse = manager.isAdmin(kNum);
					
					System.out.println("Student: " + response);
					System.out.println("Admin: " + adminResponse);
					
					
					
					
					//If kNumber does not show in both databases:
					if ((response ==  GlobalUI.FAIL) && (adminResponse == GlobalUI.FAIL))
					{
						txtErrorMessage.setVisible(true);
						txtKNumber.setText("");
						turnOffErrorMessage();
					}
					else
					{
						if ((response == GlobalUI.FAIL) && (adminResponse == GlobalUI.SUCCESS))
						{
							labelInsertAdminPass.setVisible(true);
							turnOffInsertAdminPass();
						}
						else
						{
							if ((response == GlobalUI.SUCCESS) || (adminResponse == GlobalUI.SUCCESS)) {
								labelHelp.setVisible(false);
								labelToast.setVisible(true);
								
								manager.logStudent(kNum);
								
								txtKNumber.setText("");
								// Delay on Toast
								turnOffToast();

							} else {
								labelHelp.setVisible(true);
								labelToast.setVisible(false);
							}
						}
						
					}

				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
					// TODO Message to user that its not a number?
					return;
				}
			}
			}
	
			

		if (txtAdminPass == e.getSource()) {
			String strAdminPass = txtAdminPass.getText();
			String strAdminKNum = txtKNumber.getText();
			int adminKNum = Integer.parseInt(strAdminKNum);
			int response = manager.verifyAdmin(adminKNum, strAdminPass);
			
			//TODO check why verifyAdmin Fails
			System.out.println("kNumber: " + adminKNum);
			System.out.println("Password: " + strAdminPass);
			System.out.println("verifyAdmin: " + response);
			

			if (response == GlobalUI.SUCCESS)
			{
				
				// If admin is also a Student
				if (manager.isStudent(adminKNum) == GlobalUI.SUCCESS) {
					popUpResponse popUp = new popUpResponse();
					int decision = popUp.popUp();
					if (decision == GlobalUI.STUDENT) {
						labelHelp.setVisible(false);
						labelToast.setVisible(true);
						txtKNumber.setText("");
						txtAdminPass.setText("");
						txtAdminPass.setVisible(false);
						labeladminPass.setVisible(false);
						
						manager.logStudent(adminKNum);
						
						turnOffToast();
					} else if (decision == GlobalUI.ADMIN) {
						CardLayout cl = (CardLayout) manager.getCards().getLayout();
						cl.show(manager.getCards(), GlobalUI.AdminToolsPanel);
						txtKNumber.setText("");
						txtAdminPass.setText("");
						txtAdminPass.setVisible(false);
						labeladminPass.setVisible(false);
					} else if (decision == GlobalUI.NEITHER) {
						txtKNumber.setText("");
						txtAdminPass.setText("");
						txtAdminPass.setVisible(false);
						labeladminPass.setVisible(false);
						labelToast.setVisible(false);
					}

				}
				else
				{
					CardLayout cl = (CardLayout) manager.getCards().getLayout();
					cl.show(manager.getCards(), GlobalUI.AdminToolsPanel);
					txtKNumber.setText("");
					txtAdminPass.setText("");
					txtAdminPass.setVisible(false);
					labeladminPass.setVisible(false);
				}

			} else {
				
				labeladminPassError.setVisible(true);
				turnOffAdminError();
			}

		}
	}

	public static void setScannedNumber(int kNumber) {
		txtKNumber.setText(String.valueOf(kNumber));

	}
}
