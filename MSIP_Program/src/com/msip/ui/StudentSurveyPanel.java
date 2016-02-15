package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;

public class StudentSurveyPanel extends JPanel {
	public StudentSurveyPanel()
	{
		setForeground(Color.BLACK);
		setBackground(Color.GREEN);
		setLayout(null);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(235, 339, 145, 46);
		add(btnNewButton);
		
		JButton button = new JButton("New button");
		button.setBounds(406, 339, 145, 46);
		add(button);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 60));
		lblNewLabel.setBounds(101, 35, 576, 105);
		add(lblNewLabel);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("New radio button");
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton.setBounds(8, 195, 221, 76);
		add(rdbtnNewRadioButton);
		
	}
}
