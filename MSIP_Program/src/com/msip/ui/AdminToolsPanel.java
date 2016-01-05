package com.msip.ui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import com.msip.manager.MISPCore;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class AdminToolsPanel extends JPanel implements ActionListener {

	private MISPCore manager;
	private JButton btnLogOut;

	public AdminToolsPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		StudentPanel studentPanel = new StudentPanel(msipCore);
		AdminPanel adminPanel = new AdminPanel(msipCore);
		ReportPanel reportPanel = new ReportPanel(msipCore);

		tabbedPane.add(studentPanel, GlobalUI.StudentTab);
		tabbedPane.add(adminPanel, GlobalUI.AdminTab);
		tabbedPane.add(reportPanel, GlobalUI.ReportTab);

		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);

		btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(this);
		panel.add(btnLogOut);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnLogOut) {
			CardLayout cl = (CardLayout) manager.getCards().getLayout();
			cl.show(manager.getCards(), GlobalUI.LoginPanel);
		}
	}

	/**
	 * @return the manager
	 */
	public MISPCore getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(MISPCore manager) {
		this.manager = manager;
	}

}
