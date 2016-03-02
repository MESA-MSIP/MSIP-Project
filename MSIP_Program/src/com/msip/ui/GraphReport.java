/**
 * 
 */
package com.msip.ui;

import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * @author Celina
 *
 */
public class GraphReport extends ApplicationFrame {
	/**
	 * Default constructor.
	 *
	 * @param title
	 *            the frame title.
	 */
	public GraphReport(String title) {
		super(title);
		setContentPane(createPiePanel());
	}

	/**
	 * Creates a sample dataset for questionnaire (Hard-coded)
	 * 
	 * @return A sample dataset.
	 */
	private static PieDataset createDataset() {
		DefaultPieDataset datasetQ = new DefaultPieDataset();
		// for (int q = 0; q < arrayQuest.length; q++) {
		//
		// datasetQ.addValue(arrayQuest[d], Days[q]);
		StudentSurveyPanel ssp = new StudentSurveyPanel(null);
		// ssp.get
		datasetQ.setValue("Helpful", new Double(10.0));
		datasetQ.setValue("Somewhat available", new Double(9.0));
		datasetQ.setValue("Available ", new Double(11.0));
		datasetQ.setValue("We have tutors?", new Double(1.0));
		return datasetQ;

		// }
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the dataset.
	 * 
	 * @return A chart.
	 */
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart("Questionnaire", // chart
																		// title
				dataset, // data
				true, // include legend
				true, false);

		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
		plot.setNoDataMessage("No data available");

		plot.setCircular(false);
		plot.setLabelGap(0.02);
		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator(
				"{0}: {1} ({2})", new DecimalFormat("0"), new DecimalFormat(
						"0%"));
		plot.setLabelGenerator(gen);

		return chart;

	}

	/**
	 * Creates a panel
	 * 
	 * @return A panel.
	 */
	public static JPanel createPiePanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// StudentSurveyPanel ssp = new StudentSurveyPanel(null);

		GraphReport pi = new GraphReport("Questionnaire");
		pi.pack();
		RefineryUtilities.centerFrameOnScreen(pi);
		pi.setVisible(true);

	}

}
