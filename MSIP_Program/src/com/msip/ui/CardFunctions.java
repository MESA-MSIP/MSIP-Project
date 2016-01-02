package com.msip.ui;

import java.awt.CardLayout;
import com.msip.ui.GlobalUI;

import javax.swing.JPanel;

import com.msip.manager.Manager;

public class CardFunctions extends JPanel
{
	private JPanel cards;
	private Manager mng;
	
	public CardFunctions(Manager manager)
	{
		//Empty
	}
	
	public JPanel setupCards()
	{
		cards = new JPanel();
		cards.setLayout(new CardLayout());
		cards.add((new LoginWindow(mng)), GlobalUI.LoginWindowPanel);
		cards.add(new AdminTools() , GlobalUI.AdminToolPanel);
		return cards;
	}
	public void showIndexCard(String showPanel)
	{
		//TODO NullPointerException Here:
		CardLayout cl = (CardLayout) cards.getLayout();
		cl.show(cards, showPanel);
		
	}
	}
