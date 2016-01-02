package com.msip.ui;

import java.awt.CardLayout;
import com.msip.ui.GlobalUI;

import javax.swing.JPanel;

import com.msip.manager.Manager;

public class CardFunctions extends JPanel
{
	private JPanel cards;	

	
	public JPanel setupCards(Manager manager)
	{
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		cards.add((new LoginWindow(manager)), GlobalUI.LoginWindowPanel);
		cards.add(new AdminTools() , GlobalUI.AdminToolPanel);
		return cards;
	}

	}
