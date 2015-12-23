package com.msip.ui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.CardLayout;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Button;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class AdminTools extends JPanel 
{
	
	private JTable studentTable;
	private JTable adminTable;
	public AdminTools() {
		setBackground(Color.RED);
		setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Segoe UI Light", Font.PLAIN, 16));
		tabbedPane.setBounds(0, 30, 450, 270);
		add(tabbedPane);
		
		
		//TODO	fix/adjust Tabs evenly
		
		JPanel studentPanel = new JPanel();
		tabbedPane.addTab("Add/Remove Student\r\n", null, studentPanel, null);
		studentPanel.setLayout(null);
		
		studentTable = new JTable();
		studentTable.setBounds(0, 0, 445, 192);
		studentTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		studentTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		studentPanel.add(studentTable);
		
		JButton addStudentButton = new JButton("Add");
		addStudentButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		addStudentButton.setBounds(79, 196, 134, 25);
		studentPanel.add(addStudentButton);
		
		JButton removeStudentButton = new JButton("Remove");
		removeStudentButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		removeStudentButton.setBounds(215, 196, 134, 25);
		studentPanel.add(removeStudentButton);
		
		JPanel adminPanel = new JPanel();
		tabbedPane.addTab("Add/Remove Admin\r\n", null, adminPanel, null);
		adminPanel.setLayout(null);
		
		adminTable = new JTable();
		adminTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		));
		adminTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		adminTable.setBounds(0, 0, 445, 192);
		adminPanel.add(adminTable);
		
		JButton addAdminButton = new JButton("Add");
		addAdminButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		addAdminButton.setBounds(82, 196, 134, 25);
		adminPanel.add(addAdminButton);
		
		JButton removeAdminButton = new JButton("Remove");
		removeAdminButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		removeAdminButton.setBounds(216, 196, 134, 25);
		adminPanel.add(removeAdminButton);
		
		JPanel reportPanel = new JPanel();
		tabbedPane.addTab("Generate Report\r\n", null, reportPanel, null);
		
		Button button = new Button("Log Out");
		button.setBackground(Color.CYAN);
		button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		button.setBounds(371, 0, 79, 24);
		add(button);
	}
}
