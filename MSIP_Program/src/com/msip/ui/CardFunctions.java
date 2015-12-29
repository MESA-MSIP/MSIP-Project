package com.msip.ui;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.msip.manager.Manager;

public class CardFunctions extends JPanel 
{
	private JPanel cards;
	private Manager mng;
	
	public JPanel setupCards()
	{
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		cards.add((new LoginWindow(mng)), "login");
		cards.add(new AdminTools() , "admin");
		return cards;
	}
	public void showIndexCard(String showPanel)
	{
		//TODO NullPointerException Here:
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, showPanel);
		
	}
	
}
