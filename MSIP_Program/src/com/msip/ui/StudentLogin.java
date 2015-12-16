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

public class StudentLogin extends JPanel {
	private JTextField txtEnterKHere;
	public StudentLogin() {
		setBackground(Color.RED);	//to see textfields and other things to work in
		setLayout(null);
		
		JLabel lblWelcome = DefaultComponentFactory.getInstance().createTitle("Welcome!");
		lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);		//"Welcome!"
		lblWelcome.setBounds(136, 72, 164, 92);
		add(lblWelcome);
		
		txtEnterKHere = new JTextField();
		txtEnterKHere.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent arg0)
			{
				//actionperformed on Correct Key
				//check on correctkey
				//If correct, link to StudentLWelcomeScreen
			}
		});
		txtEnterKHere.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnterKHere.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtEnterKHere.setText("Enter K# Here:");		// k# textfield, aligned and centered:
		txtEnterKHere.setBounds(120, 163, 196, 39);
		txtEnterKHere.setColumns(10);
		add(txtEnterKHere);
		
		JLabel labelMESA = new JLabel("");
		labelMESA.setIcon(new ImageIcon(StudentLogin.class.getResource("/com/msip/ui/MESA.png")));
		labelMESA.setBounds(352, 0, 98, 39);
		add(labelMESA);
	}

	public Color getThisBackground() {
		return getBackground();
	}
	public void setThisBackground(Color background) {
		setBackground(background);
	}
}
