package com.msip.external;

import com.msip.manager.MISPCore;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;

public class SerialPort {

	private MISPCore manager;

	public SerialPort(final MISPCore manager) {

		this.setManager(manager);

		// connect using setting 38400, N, 8, 1.
		// create an instance of the serial communications class
		final Serial serial = SerialFactory.createInstance();
		serial.open(Serial.DEFAULT_COM_PORT, 57600);

		// create and register the serial data listener
		serial.addListener(new SerialDataListener() {

			public void dataReceived(SerialDataEvent event) {
				try {
					int kNumber = Integer.parseInt(event.getData());
					//Call function in managger to fill fields in the UI
					manager.setScannedNumber(kNumber);
				} catch (NumberFormatException e) {
					// Exception thrown because its not a int, just log it
					System.out.print("RAW: " + event.getData());
				}
			}
		});
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
