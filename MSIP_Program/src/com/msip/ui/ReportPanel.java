package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ReportPanel() {
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		JLabel lblReportPanel = new JLabel("Report Panel");
		add(lblReportPanel, BorderLayout.NORTH);
	}

}
