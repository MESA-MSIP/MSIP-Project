package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.manager.MISPCore;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;

public class StudentSurveyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public StudentSurveyPanel(final MISPCore manager) {
		setForeground(Color.BLACK);
		setBackground(Color.GREEN);
		setLayout(null);

		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(235, 339, 145, 46);
		add(btnNewButton);

		JButton button = new JButton("New button");
		button.setBounds(406, 339, 145, 46);
		add(button);

		JRadioButton rdbtnNewRadioButton = new JRadioButton("\r\nValue 1:");
		rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
		rdbtnNewRadioButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rdbtnNewRadioButton.setBounds(8, 197, 132, 85);
		add(rdbtnNewRadioButton);

		JRadioButton radioButton = new JRadioButton("New radio button");
		radioButton.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton.setBounds(176, 197, 132, 85);
		add(radioButton);

		JRadioButton radioButton_1 = new JRadioButton("New radio button");
		radioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_1.setBounds(339, 197, 132, 85);
		add(radioButton_1);

		JRadioButton radioButton_2 = new JRadioButton("New radio button");
		radioButton_2.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_2.setBounds(498, 197, 132, 85);
		add(radioButton_2);

		JRadioButton radioButton_3 = new JRadioButton("New radio button");
		radioButton_3.setHorizontalAlignment(SwingConstants.CENTER);
		radioButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		radioButton_3.setBounds(658, 197, 132, 85);
		add(radioButton_3);

		JTextPane txtpnRateTheMesa = new JTextPane();
		txtpnRateTheMesa.setBackground(new Color(255, 255, 255));
		txtpnRateTheMesa.setContentType("text/plain/");
		StyledDocument doc = txtpnRateTheMesa.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		txtpnRateTheMesa.setText(" Rate the MESA Center Tutors between 1-5  on Availability.\r\n");
		txtpnRateTheMesa.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		txtpnRateTheMesa.setBounds(8, 25, 780, 150);
		add(txtpnRateTheMesa);

	}
}
