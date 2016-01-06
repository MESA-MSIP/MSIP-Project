package com.msip.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.border.Border;

import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;


/**
 * @author Juan Helpful links
 *         http://howtodoinjava.com/2014/07/29/create-pdf-files-in-java-itext-
 *         tutorial/ http://www.vogella.com/tutorials/JavaPDF/article.html
 */
public class ReportMakerPDF {

	private Document document;
	PdfWriter writer;
	private String creator = "MSIP Software";
	private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

	/**
	 * Creates a report in the specified path
	 * 
	 * @param yourFile
	 *            - File to create report in
	 * @throws DocumentException
	 */
	public ReportMakerPDF(File yourFile) throws DocumentException {
		document = new Document();
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(yourFile));
			document.open();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
			throw new DocumentException();
		}
	}

	/**
	 * Adds new MettaData to report.
	 * 
	 * @param title
	 *            - report title
	 * @param subject
	 *            - report subject
	 * @param author
	 *            - report author, usually admin
	 */
	public void addMettaData(String title, String subject, String author) {
		document.addTitle(title);
		document.addSubject(subject);
		document.addAuthor(author);
		document.addCreator(creator);
	}

	/**
	 * Adds new header to report.
	 * 
	 * @param title
	 *            - report title
	 * @param author
	 *            - report author
	 * @param reportInterval
	 *            - report Interval, Weekly, Monthly, Semester, Yearly
	 * @param reportFor
	 *            - what information is the report for
	 * @throws DocumentException
	 */
	public void addHeader(String title, String author, String reportInterval, String reportFor)
			throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line
		addEmptyLine(preface, 1);

		// Lets write a big header
		preface.add(new Paragraph(title, catFont));

		preface.add(new Paragraph(reportInterval + " report usuage for " + reportFor + " ", small));

		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Report generated by: " + author + ", " + new Date(), small));

		try {
			document.add(preface);

			LineSeparator ls = new LineSeparator();
			document.add(new Chunk(ls));

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new DocumentException();
		}
	}

	/**
	 * Adds new image to report.
	 * 
	 * @param path
	 *            - path to image
	 * @throws DocumentException
	 */
	public void addImage(String path) throws DocumentException {

		Image image = null;
		try {
			image = Image.getInstance(path);

			// Fixed Positioning

			image.setAbsolutePosition(100f, 550f); // TODO figure out where to
													// postion
			// Scale to new height and new width of image
			image.scaleAbsolute(200, 200); // TODO figure out height

			// Add to document
			document.add(image);

		} catch (BadElementException | IOException e) {
			document.close();
			e.printStackTrace();
			throw new DocumentException();
		}
	}

	/**
	 * Adds new student to report.
	 * 
	 * @param nameOfStudent
	 * @param numOfTimes
	 * @param dates
	 * @throws DocumentException
	 */
	public void addStudent(String nameOfStudent, String kNumber, String numOfTimes, Date[] dates)
			throws DocumentException {
		// TODO how will the report look like?

		PdfPTable table = new PdfPTable(3);
		table.setWidthPercentage(100); // Width 100%
		table.setSpacingBefore(10f); // Space before table
		table.setSpacingAfter(10f); // Space after table

		float[] columnWidths = { .8f, .8f, 1f };
		table.setWidths(columnWidths);

		// Set Table Header
		PdfPCell headerCell1 = new PdfPCell(new Phrase("Name"));
		headerCell1.setBorder(0);
		headerCell1.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(headerCell1);

		PdfPCell headerCell2 = new PdfPCell(new Phrase("Number of Times Present"));
		headerCell2.setBorder(0);
		headerCell2.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(headerCell2);

		PdfPCell headerCell3 = new PdfPCell(new Phrase("Dates Present"));
		headerCell3.setBorder(0);
		headerCell3.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.addCell(headerCell3);
		table.setHeaderRows(1);

		for (int i = 0; i < dates.length; i++) {

			if (i == 0) {
				// First row should have name, number of times and start listing
				// dates
				PdfPCell contentCell1 = new PdfPCell(new Phrase(nameOfStudent));
				contentCell1.setBorder(0);
				table.addCell(contentCell1);

				PdfPCell contentCell2 = new PdfPCell(new Phrase(numOfTimes));
				contentCell2.setBorder(0);
				table.addCell(contentCell2);

				PdfPCell contentCell3 = new PdfPCell(new Phrase(dates[i].toString()));
				contentCell3.setBorder(0);
				table.addCell(contentCell3);
			} else if (i == 1) {
				// Second row should have name and start listing dates
				PdfPCell contentCell1 = new PdfPCell(new Phrase(kNumber));
				contentCell1.setBorder(0);
				table.addCell(contentCell1);

				PdfPCell contentCell2 = new PdfPCell();
				contentCell2.setBorder(0);
				table.addCell(contentCell2);

				PdfPCell contentCell3 = new PdfPCell(new Phrase(dates[i].toString()));
				contentCell3.setBorder(0);
				table.addCell(contentCell3);
			} else {
				// All other rows should have dates
				PdfPCell contentCell1 = new PdfPCell(new Phrase());
				contentCell1.setBorder(0);
				table.addCell(contentCell1);

				PdfPCell contentCell2 = new PdfPCell();
				contentCell2.setBorder(0);
				table.addCell(contentCell2);

				PdfPCell contentCell3 = new PdfPCell(new Phrase(dates[i].toString()));
				contentCell3.setBorder(0);
				table.addCell(contentCell3);
			}
		}

		document.add(table);

		try {
			LineSeparator ls2 = new LineSeparator();
			document.add(new Chunk(ls2));

		} catch (DocumentException e) {
			e.printStackTrace();
			throw new DocumentException();
		}
	}

	/**
	 * Closes Report
	 */
	public void closeReport() {
		document.close();
		writer.close();
	}

	/**
	 * Create a new line
	 * 
	 * @param paragraph
	 * @param number
	 *            of new lines
	 */
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public static void main(String[] args) {

		JFileChooser fc = new JFileChooser();
		// fc.setCurrentDirectory(new java.io.File(".")); // start at
		// application current directory
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File yourFolder = fc.getSelectedFile();
			System.out.println(yourFolder);

			File pathToPDF = new File(yourFolder.getAbsolutePath() + File.separator + "report.pdf");

			try {
				Date[] d = new Date[20];
				for (int i = 0; i < d.length; i++) {
					d[i] = new Date();
				}

				ReportMakerPDF rp = new ReportMakerPDF(pathToPDF);
				rp.addMettaData("Report Title", "Report Subject", "Juan Zepeda");
				rp.addHeader("Report Title", "Juan Zepeda", "Daily", "John Smith");
				rp.addStudent("Juan Zepeda", "KNumber", "10", d);
				rp.addStudent("Juan Zepeda2", "KNumber2", "103", d);
				rp.addStudent("Juan Zepeda3", "KNumber3", "102", d);
				rp.closeReport();

			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
}
