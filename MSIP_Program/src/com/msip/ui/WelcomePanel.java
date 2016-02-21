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
		
		JPanel welcomeCards = new JPanel();
		welcomeCards.setLayout(new CardLayout(0, 0));
		
		
		//Construct Cards
		JPanel StudentSurveyPanel = new StudentSurveyPanel(manager);
		
		//Add the Cards to the JPanel
		welcomeCards.add(StudentSurveyPanel, GlobalUI.StudentSurveyPanel);
		add(welcomeCards, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		
		
		
		
		
		
	}
}
