package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.event.AncestorListener;
import javax.swing.JButton;

import org.freixas.jcalendar.JCalendar;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Component;

import javax.swing.Box;

public class DateTimeDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btnUpdate;
	private JButton btnClose;
	private JCalendar calendar;
	private AdminToolsPanel adminToolsPanel;

	/**
	 * Create the dialog.
	 * 
	 * @param adminToolsPanel
	 * 
	 * @wbp.parser.constructor
	 */
	public DateTimeDialog(String title, AdminToolsPanel adminToolsPanel, Dialog.ModalityType APPLICATION_MODAL) {
		this.adminToolsPanel = adminToolsPanel;
		setLayout(new BorderLayout(0, 0));
		setPreferredSize(new Dimension(600, 360));
		setMinimumSize(new Dimension(600, 360));

		JPanel panelDateTime = new JPanel();
		getContentPane().add(panelDateTime, BorderLayout.SOUTH);
		panelDateTime.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnUpdate = new JButton("Update");
		btnUpdate.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		panelDateTime.add(btnUpdate);
		btnUpdate.addActionListener(this);

		Component horizontalStrut = Box.createHorizontalStrut(40);
		panelDateTime.add(horizontalStrut);

	    btnClose = new JButton("Close");
		panelDateTime.add(btnClose);
		btnClose.setPreferredSize(new Dimension(100, GlobalUI.BUTTONHEIGHT));
		btnClose.addActionListener(this);

		JPanel panelUpdate = new JPanel();
		getContentPane().add(panelUpdate, BorderLayout.NORTH);

		calendar = new JCalendar(JCalendar.DISPLAY_DATE
				| JCalendar.DISPLAY_TIME, true);
		calendar.setTitleFont(new Font("Serif", Font.BOLD | Font.ITALIC, 14));
		calendar.setDayOfWeekFont(new Font("SansSerif", Font.ITALIC, 12));
		calendar.setDayFont(new Font("SansSerif", Font.BOLD, 12));
		calendar.setTimeFont(new Font("DialogInput", Font.PLAIN, 26));
		calendar.setTodayFont(new Font("Dialog", Font.PLAIN, 0));
		panelUpdate.add(calendar);

		setModal(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnUpdate) {
			SimpleDateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS ");
			System.out.println(formatedDate.format(calendar.getDate()));
			//"2022-12-07 01:20:15.962"
			try {
				String[] date1 = {"sudo","date","--set",formatedDate.format(calendar.getDate())};
//              System.out.println(date1);
                Process p=Runtime.getRuntime().exec(date1);
                p.waitFor();

//				Runtime.getRuntime().exec("sudo date -s " + "'"+ formatedDate.format(calendar.getDate()) + "'");
//				System.out.println("sudo date -s " + "'"+ formatedDate.format(calendar.getDate()) + "'");
				adminToolsPanel.setStatusMsg("System time set to :" + calendar.getDate());

			} catch (Exception e1) {
				e1.printStackTrace();
				adminToolsPanel.setStatusMsg("Could not set system time");
			}
		} else if (e.getSource() == btnClose) {
			setVisible(false);
			dispose();

		}

	}

}