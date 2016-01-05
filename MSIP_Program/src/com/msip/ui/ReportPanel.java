package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import com.msip.manager.MISPCore;

import javax.swing.JLabel;

public class ReportPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MISPCore manager;
	
	public ReportPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		JLabel lblReportPanel = new JLabel("Report Panel");
		add(lblReportPanel, BorderLayout.NORTH);
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

}
