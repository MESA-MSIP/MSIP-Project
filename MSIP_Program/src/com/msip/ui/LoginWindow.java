package com.msip.ui;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoginWindow extends JPanel {
	private JTextField kNumber;
	private JTextField adminPass;
	public LoginWindow() {
		setBackground(Color.RED);	//to see textfields and other things to work in
		setLayout(null);
		
		JLabel lblWelcome = DefaultComponentFactory.getInstance().createTitle("Welcome!");
		lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);		//"Welcome!"
		lblWelcome.setBounds(138, 56, 164, 92);
		add(lblWelcome);
		
		kNumber = new JTextField();
		kNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0)
			{
				String str = kNumber.getText();
				if (str.length() >= 10)
					adminPass.setVisible(true);		// set adminPass visible only if kNumber
				else								// is equal to 10 or higher
					adminPass.setVisible(false);	// else set to false
			}
		});
		
		kNumber.setHorizontalAlignment(SwingConstants.CENTER);
		kNumber.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		kNumber.setText("Enter K# Here:");		// k# textfield, aligned and centered:
		kNumber.setBounds(120, 146, 196, 39);
		kNumber.setColumns(10);
		add(kNumber);
		
		
		
		JLabel labelMESA = new JLabel("");
		labelMESA.setIcon(new ImageIcon(LoginWindow.class.getResource("/com/msip/ui/MESA.png")));
		labelMESA.setBounds(352, 0, 98, 39);
		add(labelMESA);
		
		adminPass = new JTextField();
		adminPass.setText("Password:");
		adminPass.setHorizontalAlignment(SwingConstants.CENTER);
		adminPass.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		adminPass.setBounds(120, 198, 196, 39);
		add(adminPass);
		adminPass.setColumns(10);
	}

	public Color getThisBackground() {
		return getBackground();
	}
	public void setThisBackground(Color background) {
		setBackground(background);
	}
}
