package com.msip.external;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;

import com.itextpdf.text.Document;

public class ReportMakerCSV {
	private String header;
	private Document doc;
	private FileWriter writer;

	private ReportMakerCSV(File fileName) {
		doc = new Document();
		try {
			writer = new FileWriter(fileName);
			doc.open();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Makes header names.
	 * 
	 * @param header
	 */
	public String addHeader(String header) {
		this.header = header;
		return header;
	}

	/**
	 * Creates the csv file.
	 * 
	 * @param studentName
	 * @param timesPresent
	 * @param dates
	 */
	public void CreateCSVFile(String studentName, String timesPresent,
			String[] dates) {

		try {
			// creates a header
			writer.append(header);
			// makes a new line
			writer.append("\n");

			writer.append(studentName).append(',');
			writer.append(timesPresent).append(',');
			writer.append(dates[0]).append(',');
			writer.append("\n");

			for (int i = 1; i < dates.length; i++) {
				writer.append(',').append(',');
				writer.append(dates[i]);
				writer.append("\n");
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	private static Timestamp getRandomDate() {
		long offset = Timestamp.valueOf("2015-01-01 00:00:00").getTime();
		long end = Timestamp.valueOf("2016-01-01 00:00:00").getTime();
		long diff = end - offset + 1;
		return new Timestamp(offset + (long) (Math.random() * diff));
	}

	public static void main(String[] args) throws IOException {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File yourFolder = fc.getSelectedFile();
			System.out.println(yourFolder);
//TODO Change to csv.
			File pathToPDF = new File(yourFolder.getAbsolutePath()
					+ File.separator + "Samplereport_csv.csv");

			ReportMakerCSV csv = new ReportMakerCSV(pathToPDF);
			   SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd");

			
			Date[] d = new Date[5];
			Timestamp timestamp;
			for (int i = 0; i < d.length; i++) {
				timestamp = getRandomDate();
				d[i] = new Date(timestamp.getTime());
			}
			String[] s = new String[5];
			for(int i = 0; i < s.length; i++){
				s[i] = str.format(d[i]);
			}
			
			Date[] d1= new Date[7];
			Timestamp timestamp1;
			for (int i = 0; i < d1.length; i++) {
				timestamp1 = getRandomDate();
				d1[i] = new Date(timestamp1.getTime());
			}
			String[] s2 = new String[5];
			for(int i = 0; i < s2.length; i++){
				s2[i] = str.format(d1[i]);
			}
			
			Date[] d2 = new Date[9];
			Timestamp timestamp2;
			for (int i = 0; i < d2.length; i++) {
				timestamp2 = getRandomDate();
				d2[i] = new Date(timestamp2.getTime());
			}
			String[] s3 = new String[5];
			for(int i = 0; i < s3.length; i++){
				s3[i] = str.format(d2[i]);
			}
			
			csv.addHeader("Name,times Present,Dates present");
			csv.CreateCSVFile("Fernando Estevez", "5", s);
			csv.CreateCSVFile("Christian Martinez", "7", s2);
			//csv.CreateCSVFile("Celina Lazaro", "9", s3);


		}
	}
}
