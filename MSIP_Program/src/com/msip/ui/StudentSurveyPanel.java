package com.msip.ui;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.msip.manager.MISPCore;

import javax.swing.JRadioButton;
import javax.swing.JTextPane;

public class StudentSurveyPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JButton skipButton;
	private JButton submitButton;
	private JRadioButton rbutton1, rbutton2, rbutton3, rbutton4, rbutton5;
	private JTextPane surveyQ;
	private MISPCore manager;
	
	public StudentSurveyPanel(final MISPCore manager)
	{
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		setLayout(null);
		
		this.manager = manager;
		
		skipButton = new JButton("Skip Survey\r\n");
		skipButton.setBounds(235, 339, 145, 46);
		skipButton.addActionListener(this);
		add(skipButton);

		submitButton = new JButton("Submit");
		submitButton.setBounds(406, 339, 145, 46);
		submitButton.addActionListener(this);
		add(submitButton);

		rbutton1 = new JRadioButton("\r\nValue 1:");
		rbutton1.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton1.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		rbutton1.setBounds(8, 197, 132, 85);
		add(rbutton1);

		rbutton2 = new JRadioButton("Value 2:");
		rbutton2.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton2.setBounds(176, 197, 132, 85);
		add(rbutton2);

		rbutton3 = new JRadioButton("Value 3:");
		rbutton3.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton3.setBounds(339, 197, 132, 85);
		add(rbutton3);

		rbutton4 = new JRadioButton("Value 4:");
		rbutton4.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton4.setBounds(498, 197, 132, 85);
		add(rbutton4);

		rbutton5 = new JRadioButton("Value 5:");
		rbutton5.setHorizontalAlignment(SwingConstants.CENTER);
		rbutton5.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rbutton5.setBounds(658, 197, 132, 85);
		add(rbutton5);

		surveyQ = new JTextPane();
		surveyQ.setBackground(new Color(255, 255, 255));
		surveyQ.setContentType("text/plain/");
		StyledDocument doc = surveyQ.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		surveyQ.setText(" Rate the MESA Center Tutors between 1-5  on Availability.\r\n");
		surveyQ.setFont(new Font("Segoe UI", Font.PLAIN, 34));
		surveyQ.setBounds(8, 25, 780, 150);
		add(surveyQ);

	}
	
	public void actionPerformed(ActionEvent e)
	{
		if (skipButton == e.getSource())
		{
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
		}
		else if (submitButton == e.getSource())
				{
			//TODO log the information to the surveyTable
			CardLayout cl = (CardLayout) this.manager.getCards().getLayout();
			cl.show(this.manager.getCards(),GlobalUI.LoginPanel);
				}
		else
		{
			return;
		}
	}
}
