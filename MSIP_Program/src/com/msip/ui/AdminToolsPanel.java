package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.msip.db.SurveyTable;
import com.msip.manager.MISPCore;

@SuppressWarnings("serial")
public class AdminToolsPanel extends JPanel implements ActionListener {

	private MISPCore manager;
	private JButton btnLogOut;
	private JLabel lblStatusMsg;
	private SurveyTable surveyTable;
	private ReportPanel reportPanel;
	private QuestionnairePanel questionnairePanel;
	private JButton shutDownButton;

	public AdminToolsPanel(MISPCore msipCore) {
		this.setManager(msipCore);
		this.manager = msipCore;
		setBackground(Color.WHITE);
		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(GlobalUI.GlobalFont);

		StudentPanel studentPanel = new StudentPanel(msipCore, this);
		AdminPanel adminPanel = new AdminPanel(msipCore, this);
		reportPanel = new ReportPanel(msipCore, this);
		NotificationsPanel notificationsPanel = new NotificationsPanel(msipCore,this);
		questionnairePanel = new QuestionnairePanel(msipCore);

		tabbedPane.add(studentPanel, GlobalUI.StudentTab);
		tabbedPane.add(adminPanel, GlobalUI.AdminTab);
		tabbedPane.add(reportPanel, GlobalUI.ReportTab);
		tabbedPane.add(notificationsPanel, GlobalUI.NotificationsPanel);
		tabbedPane.add(questionnairePanel, GlobalUI.QuestionnairePanel);

		add(tabbedPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		// removed the flow lay out.
		// FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		// flowLayout.setAlignment(FlowLayout.RIGHT);
		panel.setPreferredSize(new Dimension(100, 73));
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.NORTH);
		btnLogOut = new JButton();

		btnLogOut.setBounds(720, 5, 63, 63);
		// btnLogOut.setBorderPainted(false);

		ImageIcon icon = CreateIcon("Exit2.png", 60, 60);

		btnLogOut.setBorder(GlobalUI.blackBorder);
		btnLogOut.setIcon(icon);
		// btnLogOut.setPreferredSize(new Dimension(60, 60));

		btnLogOut.addActionListener(this);
		panel.setLayout(null);
		panel.add(btnLogOut);

		// Component horizontalStrut = Box.createHorizontalStrut(20);
		// horizontalStrut.setBounds(440, 36, 5, 12);
		// horizontalStrut.setPreferredSize(new Dimension(5, 0));
		// panel.add(horizontalStrut);

		// added a shut down button.
		shutDownButton = new JButton();
		ImageIcon shutDown = CreateIcon("shutdown3.png", 55, 55);
		shutDownButton.setIcon(shutDown);
		shutDownButton.setBounds(15, 5, 63, 63);

		// shutDownButton.setBorder(GlobalUI.blackBorder);
		shutDownButton.addActionListener(this);
		panel.add(shutDownButton);

		JPanel panelStatus = new JPanel();
		FlowLayout fl_panelStatus = (FlowLayout) panelStatus.getLayout();
		fl_panelStatus.setAlignment(FlowLayout.LEFT);
		add(panelStatus, BorderLayout.SOUTH);

		lblStatusMsg = new JLabel("Welcome to the Admin panel");
		panelStatus.add(lblStatusMsg);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				if (manager.getID() != -2) {
					questionnairePanel.updateGraph();
				}
				reportPanel.updateGraph();
			}

			public void componentHidden(ComponentEvent e) {
				if (manager.getID() != -2) {
					questionnairePanel.updateGraph();
				}
				reportPanel.updateGraph();

			}
		});

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

		} else if (e.getSource() == shutDownButton) {
			try {
				// Shuts down the pi
				Process p = Runtime.getRuntime().exec("sudo shutdown -h now");
				p.waitFor();
			} catch (IOException | InterruptedException e1) {
				e1.printStackTrace();
			}

		}

	}

	/**
	 * Grabs the Exit Logo from folder image and returns the image.
	 * 
	 * @param filename
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageIcon CreateIcon(String filename, int width, int height) {
		InputStream url = getClass().getResourceAsStream("/images/" + filename);
		BufferedImage img = null;
		try {
			img = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon image = new ImageIcon(img.getScaledInstance(width, height, 0));
		return image;
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
