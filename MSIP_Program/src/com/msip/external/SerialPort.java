package com.msip.external;

import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataListener;
import com.pi4j.io.serial.SerialFactory;
public class SerialPort {

public SerialPort(){
	// connect using setting 38400, N, 8, 1.
	// create an instance of the serial communications class
	final Serial serial = SerialFactory.createInstance();
	serial.open(Serial.DEFAULT_COM_PORT, 57600);

		// create and register the serial data listener
		serial.addListener(new SerialDataListener() {
			
			public void dataReceived(SerialDataEvent event) {
				// print out the data received to the console
				System.out.print(event.getData());
			}
		});
	}
}


