package com.msip.ui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.border.MatteBorder;

import com.msip.manager.Manager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * @author Chris
 *
 */
@SuppressWarnings("serial")
public class LoginWindow extends JPanel implements ActionListener {
	private JTextField kNumber;
	private JTextField adminPass;
	private JLabel labelToast;
	private JLabel labelkNumber;
	private JLabel labeladminPass;
	private JLabel labelHelp;
	private JLabel labeladminPassError;

	/**
	 * @param manager
	 */
	public LoginWindow(final Manager manager) {

		// Setup Layout, Bounds
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(Color.WHITE); // to see textfields and other things to
									// work in
		setLayout(null);
		setBounds(0, 0, 800, 420);

		// JTxtField kNumber
		kNumber = new JTextField();
		kNumber.addActionListener(this);
		kNumber.addKeyListener(k);
		kNumber.setHorizontalAlignment(SwingConstants.CENTER);
		kNumber.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		kNumber.setBounds(251, 183, 286, 70);
		kNumber.setColumns(10);
		add(kNumber);

		// Admin Password Textfield
		adminPass = new JTextField();
		adminPass.addActionListener(this);
		adminPass.setHorizontalAlignment(SwingConstants.CENTER);
		adminPass.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		adminPass.setBounds(251, 300, 286, 70);
		adminPass.setColumns(10);
		adminPass.setVisible(false);
		add(adminPass);

		// Title Welcome!
		JLabel labelWelcome = new JLabel("Welcome!");
		labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		labelWelcome.setFont(new Font("Segoe UI", Font.BOLD, 42));
		labelWelcome.setBounds(251, 71, 286, 78);
		add(labelWelcome);

		// Animated Toast that shows Logged In:
		labelToast = new JLabel("You Have Logged In.");
		labelToast.setVisible(false);
		labelToast.setHorizontalAlignment(SwingConstants.CENTER);
		labelToast.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelToast.setBounds(582, 179, 147, 78);
		add(labelToast);

		// kNum Description
		labelkNumber = new JLabel("Enter K# Here:");
		labelkNumber.setLabelFor(kNumber);
		labelkNumber.setHorizontalAlignment(SwingConstants.CENTER);
		labelkNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelkNumber.setBounds(333, 143, 124, 28);
		add(labelkNumber);

		// admin password description:
		labeladminPass = new JLabel("Password:");
		labeladminPass.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPass.setLabelFor(adminPass);
		labeladminPass.setBounds(344, 265, 113, 22);
		add(labeladminPass);
		labeladminPass.setVisible(false);

		// MESA LOGO
		JLabel labelMESA = new JLabel("");
		labelMESA.setIcon(new ImageIcon(LoginWindow.class.getResource("/com/msip/ui/MESA.png")));
		labelMESA.setBounds(635, 0, 165, 100);
		add(labelMESA);

		// kNum# incorrect texttip
		labelHelp = new JLabel("K# is Incorrect.");
		labelHelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHelp.setHorizontalAlignment(SwingConstants.CENTER);
		labelHelp.setBounds(596, 192, 108, 53);
		labelHelp.setVisible(false);
		add(labelHelp);

		// AdminPass/kNum Error TextTip
		labeladminPassError = new JLabel("Password or K# is incorrect.");
		labeladminPassError.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPassError.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPassError.setBounds(582, 300, 147, 71);
		labeladminPassError.setVisible(false);
		add(labeladminPassError);

	}

	/**
	 * Gets BackGround Color.
	 * 
	 * @return
	 */
	public Color getThisBackground() {
		return getBackground();
	}

	public void setThisBackground(Color background) {
		setBackground(background);
	}

	/** Multiple uses  of actionPerformed:
	 * kNumberTextField: Check the string inputed, if correct, show the toast.
	 * adminPass: check the password: if correct, show AdminTools, if not show an error message.
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e)
	{
		Manager manager = new Manager();
		if (kNumber == e.getSource())
		{
			try{
				String str = kNumber.getText();
				int kNum = Integer.parseInt(str);			//get string of kNumber txtfield, parse to Integer
				GlobalUI.kNumLength = manager.isStudent(kNum);		//grab studentDB kNumber, return 0,1
				if (GlobalUI.kNumLength == GlobalUI.SUCCESS)
				{
					labelHelp.setVisible(false);
					labelToast.setVisible(true);				//if equal, set the toast to show
					
				}
				else
				{
					labelHelp.setVisible(true);
					labelToast.setVisible(false);			// else show the help message
					
				}
				}
			catch (Exception r){
			r.printStackTrace();
			}
			
		}
			else if (adminPass == e.getSource())
			{
				try
				{
					String str1 = adminPass.getText();
					int adminNum = Integer.parseInt(str1);
					GlobalUI.kNumLength = manager.isStudent(adminNum);
					if (GlobalUI.kNumLength == GlobalUI.SUCCESS)
					{
						CardLayout cl =(CardLayout) manager.cardPanels.getLayout();
						cl.show(manager.cardPanels, GlobalUI.AdminToolPanel);
						
						labeladminPassError.setVisible(false);//method same as kNumber, except for admins
					}
					else
					{
						labeladminPassError.setVisible(true);
							
					}

				}
				catch (Exception r)
				{
					r.printStackTrace();
				}
			}
		
	}

	KeyListener k  = new KeyAdapter()		//set up a new KeyAdapter() for KeyReleased
			{
	/** Listens to the Keys.
	 * If the kNumber Length is greater than 8, set the Admin Password TxtField Visible.
	 * 
	 * @param e
	 */
	public void keyReleased(KeyEvent e) {
		String str = kNumber.getText();
		if (str.length() < GlobalUI.kNumMax) {
			adminPass.setVisible(false); // if number is less than 8, dont show
											// adminPass
			labeladminPass.setVisible(false);
		} else {
			labeladminPass.setVisible(true);
			adminPass.setVisible(true); // else set it visible
		}
	}
			};
}
