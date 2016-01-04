/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class StudentPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private StudentTable studentTable;
	
	public StudentPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		studentTable = new StudentTable();
		add(studentTable, BorderLayout.CENTER);
		
		JLabel lblStudentPanel = new JLabel("Student Panel");
		add(lblStudentPanel, BorderLayout.NORTH);
	}
}
