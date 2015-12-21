package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import java.awt.Font;

public class AdminTools extends JPanel 
{
	public AdminTools() {
		setBackground(Color.RED);
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		tabbedPane.setBounds(0, 0, 450, 300);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("", null, panel, null);
	}
}
