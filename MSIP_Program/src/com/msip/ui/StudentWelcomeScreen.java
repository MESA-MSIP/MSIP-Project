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

public class StudentWelcomeScreen extends JPanel {
	private JTextField txtEnterKHere;
	public StudentWelcomeScreen() {
		setBackground(Color.RED);
		setLayout(null);
		
		JLabel lblWelcome = DefaultComponentFactory.getInstance().createTitle("Welcome!");
		lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 24));
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setBounds(136, 72, 164, 92);
		add(lblWelcome);
		
		txtEnterKHere = new JTextField();
		txtEnterKHere.setHorizontalAlignment(SwingConstants.CENTER);
		txtEnterKHere.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		txtEnterKHere.setText("Enter K# Here:");
		txtEnterKHere.setBounds(120, 163, 196, 39);
		add(txtEnterKHere);
		txtEnterKHere.setColumns(10);
		
		JLabel MESA = new JLabel("");
		MESA.setBounds(329, 0, 121, 48);
		add(MESA);
	}

	public Color getThisBackground() {
		return getBackground();
	}
	public void setThisBackground(Color background) {
		setBackground(background);
	}
}
