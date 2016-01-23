/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import com.msip.manager.MISPCore;

import javax.swing.JLabel;

public class AdminPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private AdminTable adminTable;
	private MISPCore manager;
	private AdminToolsPanel adminToolsPanel;
	
	public AdminPanel(MISPCore mispCore, AdminToolsPanel adminToolsPanel) {
		this.setManager(mispCore);
		this.setAdminToolsPanel(adminToolsPanel);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		adminTable = new AdminTable();
		add(adminTable, BorderLayout.CENTER);
		
		JLabel lblAdminPanel = new JLabel("Admin Panel");
		add(lblAdminPanel, BorderLayout.NORTH);
	}

	/**
	 * @return the manager
	 */
	public MISPCore getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}

	/**
	 * @return the adminToolsPanel
	 */
	public AdminToolsPanel getAdminToolsPanel() {
		return adminToolsPanel;
	}

	/**
	 * @param adminToolsPanel the adminToolsPanel to set
	 */
	public void setAdminToolsPanel(AdminToolsPanel adminToolsPanel) {
		this.adminToolsPanel = adminToolsPanel;
	}
}
