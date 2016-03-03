/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.msip.manager.MISPCore;
import com.toedter.calendar.JDateChooser;

/**
 * @author Celina
 *
 */
public class NotificationsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField textFieldNotifications;
	private JTable tableNotifications;
	private JDateChooser startDateChooser;
	private Date selectedStartDate;
	private JDateChooser expirationDateChooser;
	private Date selectedExpirationDate;
	private JButton btnRemove;
	private JButton btnAdd;
	private MISPCore rem;
	private DefaultTableModel model;

	public NotificationsPanel(MISPCore msipCore) {
		rem = new MISPCore();

		/**
		 * Create the panel.
		 */

		setPreferredSize(new Dimension(700, 380));
		setLayout(new BorderLayout(0, 0));

		tableNotifications = new JTable(new DefaultTableModel(
				new Object[][] {}, new String[] { "Notifications:",
						"Start Date:", "Expiration Date:" }));

		JScrollPane notificationScrollPane = new JScrollPane(tableNotifications);
		add(notificationScrollPane, BorderLayout.CENTER);

		Component horizontalStrut = Box.createHorizontalStrut(20);
		add(horizontalStrut, BorderLayout.WEST);

		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		add(horizontalStrut_1, BorderLayout.EAST);

		Component verticalStrut = Box.createVerticalStrut(20);
		add(verticalStrut, BorderLayout.NORTH);

		JPanel panelNotificationInput = new JPanel();
		panelNotificationInput.setPreferredSize(new Dimension(10, 100));
		add(panelNotificationInput, BorderLayout.SOUTH);

		btnAdd = new JButton("Add");
		btnAdd.setBounds(307, 65, 75, 29);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNotification();
			}

		});
		panelNotificationInput.setLayout(null);
		panelNotificationInput.add(btnAdd);

		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteNotification();

			}
		});
		btnRemove.setBounds(394, 65, 93, 29);
		panelNotificationInput.add(btnRemove);

		textFieldNotifications = new JTextField();
		textFieldNotifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		textFieldNotifications.setBounds(6, 6, 289, 63);
		panelNotificationInput.add(textFieldNotifications);
		textFieldNotifications.setColumns(10);

		// Start Date
		startDateChooser = new JDateChooser();
		startDateChooser.setBounds(329, 6, 137, 26);
		panelNotificationInput.add(startDateChooser);
		// Adds todays date.
		Date date1 = new Date();
		startDateChooser.setDate(date1);
		selectedStartDate = startDateChooser.getDate();
		startDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						selectedStartDate = startDateChooser.getDate();

					}
				});

		expirationDateChooser = new JDateChooser();
		expirationDateChooser.setBounds(492, 6, 137, 26);
		panelNotificationInput.add(expirationDateChooser);
		// sets current date
		Date date = new Date();
		expirationDateChooser.setDate(date);
		selectedExpirationDate = expirationDateChooser.getDate();
		expirationDateChooser.getDateEditor().addPropertyChangeListener(
				new PropertyChangeListener() {
					public void propertyChange(PropertyChangeEvent e) {
						// When the user picks a date it sets it to the text box
						// and retrieves that date.
						selectedExpirationDate = expirationDateChooser
								.getDate();

					}
				});
	}

	// **********************************************************//
	// **********************************************************//
	// *** Add Notification Functions ****//
	// **********************************************************//
	// **********************************************************//
	private void addNotification() {

		tableNotifications.getModel();
		DateFormat dateStart = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		DateFormat dateEnd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String reportDate = dateStart.format(selectedStartDate);
		String reportEndDate = dateEnd.format(selectedExpirationDate);
		String note = textFieldNotifications.getText().trim();
		String st[] = { note, reportDate, reportEndDate };
		model.addRow(st);
		rem.addNotification(note, selectedStartDate, selectedExpirationDate);
	}

	// **********************************************************//
	// **********************************************************//
	// *** Delete Notification Functions ****//
	// **********************************************************//
	// **********************************************************//

	private void deleteNotification() {
		rem.removeExpiredNotifications();
		// clears expired notifications
		tableNotifications.getModel();
		if (tableNotifications.getSelectedRow() != -1) {
			// remove selected row from the model
			model.removeRow(tableNotifications.getSelectedRow());
		}
	}

	// public static void main(String[] args) {
	// NotificationsPanel np = new NotificationsPanel(null);
	// JFrame frame = new JFrame();
	//
	// frame.add(np);
	// frame.setPreferredSize(new Dimension(800, 480));
	// frame.pack();
	// frame.setVisible(true);

	// TODO Auto-generated method stub

	// }

}
