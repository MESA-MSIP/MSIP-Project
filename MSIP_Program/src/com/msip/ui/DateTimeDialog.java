package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.AncestorListener;

public class DateTimeDialog extends JDialog{

	// private final JPanel contentPanel = new JPanel();

	private static final long serialVersionUID = 1L;

	/**
	 * Create the dialog.
	 * 
	 * @param adminToolsPanel
	 * 
	 * @wbp.parser.constructor
	 */
	public DateTimeDialog(String title, AdminToolsPanel adminToolsPanel, Dialog.ModalityType APPLICATION_MODAL) {
		SettingsPanel settingsPanel = new SettingsPanel(adminToolsPanel);
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(400, 240));
		setMinimumSize(new Dimension(400,240));

		add(settingsPanel, BorderLayout.CENTER);
		setModal(true);
		setVisible(true);
	}

}