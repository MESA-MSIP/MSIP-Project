package com.msip.ui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.MatteBorder;

import com.msip.manager.MISPCore;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

/**
 * @author Chris
 *
 */
@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements ActionListener {
	private JTextField txtKNumber;
	private JTextField txtAdminPass;
	private JLabel labelToast;
	private JLabel labelKNumber;
	private JLabel labeladminPass;
	private JLabel labelHelp;
	private JLabel labeladminPassError;
	private MISPCore manager;

	/**
	 * @param manager
	 */
	public LoginPanel(final MISPCore manager) {
		
		this.manager = manager;

		// Setup Layout, Bounds
		// TODO add to Global.
		setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		setBackground(Color.WHITE);
		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		// JTxtField kNumber
		txtKNumber = new JTextField();
		txtKNumber.addActionListener(this);
		txtKNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtKNumber.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtKNumber.setColumns(10);
		add(txtKNumber);

		// Admin Password Textfield
		txtAdminPass = new JTextField();
		txtAdminPass.addActionListener(this);
		txtAdminPass.setHorizontalAlignment(SwingConstants.CENTER);
		txtAdminPass.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtAdminPass.setColumns(10);
		txtAdminPass.setVisible(false);
		add(txtAdminPass);

		// Title Welcome!
		JLabel labelWelcome = new JLabel("Welcome!");
		labelWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		labelWelcome.setFont(new Font("Segoe UI", Font.BOLD, 42));
		add(labelWelcome);

		// Animated Toast that shows Logged In:
		labelToast = new JLabel("You Have Logged In.");
		labelToast.setVisible(false);
		labelToast.setHorizontalAlignment(SwingConstants.CENTER);
		labelToast.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		add(labelToast);

		// kNum Description
		labelKNumber = new JLabel("Enter K# Here:");
		labelKNumber.setLabelFor(txtKNumber);
		labelKNumber.setHorizontalAlignment(SwingConstants.CENTER);
		labelKNumber.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		add(labelKNumber);

		// admin password description:
		labeladminPass = new JLabel("Password:");
		labeladminPass.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPass.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPass.setLabelFor(txtAdminPass);
		add(labeladminPass);
		labeladminPass.setVisible(false);

		// MESA LOGO
		// TODO
		// https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
		// Add Mesa Logo

		// kNum# incorrect texttip
		labelHelp = new JLabel("K# is Incorrect.");
		labelHelp.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labelHelp.setHorizontalAlignment(SwingConstants.CENTER);
		labelHelp.setVisible(false);
		add(labelHelp);

		// AdminPass/kNum Error TextTip
		labeladminPassError = new JLabel("Password or K# is incorrect.");
		labeladminPassError.setHorizontalAlignment(SwingConstants.CENTER);
		labeladminPassError.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		labeladminPassError.setVisible(false);
		add(labeladminPassError);

		txtKNumber.addKeyListener(new KeyAdapter() {
			
			public void keyReleased(KeyEvent e) {
				
				String str = txtKNumber.getText();
				// if number is less than 8, don't show adminPass
				
				if (str.length() < GlobalUI.kNumMax) {
					txtAdminPass.setVisible(false);
					labeladminPass.setVisible(false);
				} else {
					labeladminPass.setVisible(true);
					txtAdminPass.setVisible(true);
				}
			}
		});
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
			String strKNumber = txtKNumber.getText();

			try {
				int kNum = Integer.parseInt(strKNumber);
				int response = manager.isStudent(kNum);

				if (response == GlobalUI.SUCCESS) {
					labelHelp.setVisible(false);
					labelToast.setVisible(true);
				} else {
					labelHelp.setVisible(true);
					labelToast.setVisible(false);
				}
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				// TODO Message to user that its not a number?
				return;
			}
		}

		if (txtAdminPass == e.getSource()) {
			String strAdminPass = txtAdminPass.getText();

			try {
				int adminKNum = Integer.parseInt(strAdminPass);
				int response = manager.isStudent(adminKNum);

				if (response == GlobalUI.SUCCESS) {
					CardLayout cl = (CardLayout) manager.getCards().getLayout();
					cl.show(manager.getCards(), GlobalUI.AdminToolsPanel);

					labeladminPassError.setVisible(false);
				} else {
					labeladminPassError.setVisible(true);
				}

			} catch (NumberFormatException e1) {
				e1.printStackTrace();
				// TODO Message to user that its not a number?
				return;
			}
		}
	}
}
