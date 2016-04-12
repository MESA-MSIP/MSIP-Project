package com.msip.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.freixas.jcalendar.DateEvent;
import org.freixas.jcalendar.DateListener;
import org.freixas.jcalendar.JCalendar;

import com.toedter.calendar.JDateChooser;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

public class SettingsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnUpdate;
	private JCalendar calendar;
	private AdminToolsPanel adminToolsPanel;

	public SettingsPanel(AdminToolsPanel adminToolsPanel) {
		this.adminToolsPanel = adminToolsPanel;
		
		setLayout(new BorderLayout(0, 0));

		JPanel panelDateTime = new JPanel();
		add(panelDateTime, BorderLayout.SOUTH);
		panelDateTime.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnUpdate = new JButton("Update");
		btnUpdate.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		panelDateTime.add(btnUpdate);
		btnUpdate.addActionListener(this);

		JPanel panelUpdate = new JPanel();
		add(panelUpdate, BorderLayout.NORTH);

		calendar = new JCalendar(JCalendar.DISPLAY_DATE | JCalendar.DISPLAY_TIME, true);
		calendar.setTitleFont(new Font("Serif", Font.BOLD|Font.ITALIC, 14));
		calendar.setDayOfWeekFont(new Font("SansSerif", Font.ITALIC, 12));
		calendar.setDayFont(new Font("SansSerif", Font.BOLD, 12));
		calendar.setTimeFont(new Font("DialogInput", Font.PLAIN, 26));
		calendar.setTodayFont(new Font("Dialog", Font.PLAIN, 0));
		panelUpdate.add(calendar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			Date date = calendar.getDate();
			SimpleDateFormat formatedDate = new SimpleDateFormat("mmddhhmmyyyyy");
			System.out.println(calendar.getDate());
			try {
				Runtime.getRuntime().exec("date -s " + calendar.getDate());
				adminToolsPanel.setStatusMsg("System time set to :" + calendar.getDate());
			} catch (IOException e1) {
				e1.printStackTrace();
				adminToolsPanel.setStatusMsg("Could not set system time");
			}
		}

	}
}
