package com.msip.external;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

	public static void main(String[] args) throws IOException {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File yourFolder = fc.getSelectedFile();
			System.out.println(yourFolder);
//TODO Change to csv.
			File pathToPDF = new File(yourFolder.getAbsolutePath()
					+ File.separator + "newreport.csv");

			ReportMakerCSV csv = new ReportMakerCSV(pathToPDF);
			String[] dates = { "1/3/16", "1/4/16", "1/5/16" };

			csv.addHeader("Name,times Present,Dates present");
			csv.CreateCSVFile("Fernando", "3", dates);

		}
	}
}
