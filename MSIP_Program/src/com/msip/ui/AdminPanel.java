/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private AdminTable adminTable;
	
	public AdminPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		adminTable = new AdminTable();
		add(adminTable, BorderLayout.CENTER);
		
		JLabel lblAdminPanel = new JLabel("Admin Panel");
		add(lblAdminPanel, BorderLayout.NORTH);
	}
}
