/**
 * 
 */
package com.msip.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.msip.manager.MISPCore;
import com.toedter.calendar.JDateChooser;

/**
 * @author Celina
 *
 */
public class NotificationsPanel extends JPanel implements KeyListener {

	private static final long serialVersionUID = 1L;
	private JTextArea textAreaNotifications;
	private JTable tableNotifications;
	private JDateChooser startDateChooser;
	private Date selectedStartDate;
	private JDateChooser expirationDateChooser;
	private Date selectedExpirationDate;
	private JButton btnRemove;
	private JButton btnAdd;
	private MISPCore manager;
	private DefaultTableModel model;
	private ArrayList<String> notiArray = new ArrayList<String>();
	private int rowIndex;

	public NotificationsPanel(MISPCore msipCore) {
		manager = msipCore;

		/**
		 * Create the panel.
		 */

		setPreferredSize(new Dimension(700, 380));
		setLayout(new BorderLayout(0, 0));

		tableNotifications = new JTable(new DefaultTableModel(
				new Object[][] {}, new String[] { "Notifications:",
						"Start Date:", "Expiration Date:" }));
		tableNotifications.setFont(new Font("Tahoma", Font.PLAIN, 15));

		tableNotifications.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				// returns the index of a selected notification.
				rowIndex = tableNotifications.getSelectedRow();
				// TODO
				System.out.println(rowIndex);
			}
		});

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
		btnAdd.setEnabled(false);
		btnAdd.setBounds(307, 65, 75, 29);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNotification();
				textAreaNotifications.setText(null);

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
		textAreaNotifications = new JTextArea();
		textAreaNotifications.setWrapStyleWord(true);
		textAreaNotifications.setLineWrap(true);
		textAreaNotifications.addKeyListener(this);

		textAreaNotifications.setAlignmentX(SwingConstants.LEFT);
		textAreaNotifications.setAlignmentY(SwingConstants.NORTH);

		textAreaNotifications.addFocusListener(new FocusListener() {//
					public void focusGained(FocusEvent e) {

					}

					public void focusLost(FocusEvent e) {

					}

				});
		textAreaNotifications.setBounds(21, 6, 210, 48);
		JScrollPane noteScrollPane = new JScrollPane(textAreaNotifications);
		noteScrollPane.setBounds(21, 6, 210, 48);
		panelNotificationInput.add(noteScrollPane);

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

		model = (DefaultTableModel) tableNotifications.getModel();
		DateFormat dateStart = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		DateFormat dateEnd = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String reportDate = dateStart.format(selectedStartDate);
		String reportEndDate = dateEnd.format(selectedExpirationDate);
		String note = textAreaNotifications.getText().trim();
		String st[] = { note, reportDate, reportEndDate };
		model.addRow(st);
		manager.addNotification(note, selectedStartDate, selectedExpirationDate);
		// adds the notification to an array list.
		notiArray.add(note);
	}

	// **********************************************************//
	// **********************************************************//
	// *** Delete Notification Functions ****//
	// **********************************************************//
	// **********************************************************//

	private void deleteNotification() {

		// removes the specific notification from the DB table.
		manager.removeNotification(notiArray.get(rowIndex));
		// removes the specific notification from the arraylist to match the
		// size of the table.
		notiArray.remove(rowIndex);

		// clears expired notifications
		model = (DefaultTableModel) tableNotifications.getModel();
		if (tableNotifications.getSelectedRow() != -1) {
			// remove selected row from the model
			model.removeRow(tableNotifications.getSelectedRow());
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String data = textAreaNotifications.getText();
		if (data.isEmpty()) {
			btnAdd.setEnabled(false);
		} else {
			btnAdd.setEnabled(true); // doesnt work when first starting, still
										// accepts empty string**S
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

}
