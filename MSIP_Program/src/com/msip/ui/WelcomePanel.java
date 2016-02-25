package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Rectangle;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.msip.manager.MISPCore;

import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import java.awt.FlowLayout;

public class WelcomePanel extends JPanel
{
	private MISPCore manager;
	private JPanel info;
	public WelcomePanel(final MISPCore manager) 
	{
		this.manager = manager;
		setBounds(new Rectangle(0, 0, 800, 480));
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Message");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Timer\r\n");
		panel.add(lblNewLabel_1);
		
		JPanel welcomeCards = new JPanel();
		welcomeCards.setLayout(new CardLayout(0, 0));
		
		
		//Construct Cards
		JPanel StudentSurveyPanel = new StudentSurveyPanel(manager);
		JPanel NotificationsPanel = new NotificationsPanel(manager);
		
		//Add the Cards to the JPanel
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		welcomeCards.add(NotificationsPanel, GlobalUI.NotificationsPanel);
		add(welcomeCards, BorderLayout.CENTER);
		
		
		
		
		
		
	}
}
