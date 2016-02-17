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
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.Box;

@SuppressWarnings("serial")
public class AdminToolsPanel extends JPanel implements ActionListener {

	private MISPCore manager;
	private JButton btnLogOut;
	private JLabel lblStatusMsg;

	public AdminToolsPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(GlobalUI.GlobalFont);

		StudentPanel studentPanel = new StudentPanel(msipCore, this);
		AdminPanel adminPanel = new AdminPanel(msipCore, this);
		ReportPanel reportPanel = new ReportPanel(msipCore);
		NotificationsPanel notificationsPanel = new NotificationsPanel(msipCore);
		QuestionnairePanel questionnairePanel = new QuestionnairePanel(msipCore);

		tabbedPane.add(studentPanel, GlobalUI.StudentTab);
		tabbedPane.add(adminPanel, GlobalUI.AdminTab);
		tabbedPane.add(reportPanel, GlobalUI.ReportTab);
		tabbedPane.add(notificationsPanel, GlobalUI.NotificationsPanel);
		tabbedPane.add(questionnairePanel, GlobalUI.QuestionnairePanel);

		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);

		btnLogOut = new JButton("Log Out");
		btnLogOut.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnLogOut.setFont(GlobalUI.GlobalFont);
		
		btnLogOut.addActionListener(this);
		panel.add(btnLogOut);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(5, 0));
		panel.add(horizontalStrut);
		
		JPanel panelStatus = new JPanel();
		FlowLayout fl_panelStatus = (FlowLayout) panelStatus.getLayout();
		fl_panelStatus.setAlignment(FlowLayout.LEFT);
		add(panelStatus, BorderLayout.SOUTH);
		
		lblStatusMsg = new JLabel("Welcome to the Admin panel");
		panelStatus.add(lblStatusMsg);
	}

	public void setStatusMsg(final String msg) {
		lblStatusMsg.setText("  " + msg);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

				lblStatusMsg.setText("  ");
			}
		}, GlobalUI.TWO_THOUSAND_MILLI_SECONDS);
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
