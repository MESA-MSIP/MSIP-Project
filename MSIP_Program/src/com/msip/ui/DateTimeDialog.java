package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;

public class DateTimeDialog extends JDialog {

	// private final JPanel contentPanel = new JPanel();

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 * 
	 * @param adminToolsPanel
	 * 
	 * @wbp.parser.constructor
	 */
	public DateTimeDialog(String title, AdminToolsPanel adminToolsPanel) {
		SettingsPanel settingsPanel = new SettingsPanel(adminToolsPanel);

		setVisible(true);
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(400, 240));
		add(settingsPanel, BorderLayout.CENTER);
	}

}