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

import com.msip.manager.MISPCore;

/**
 * @author Celina
 *
 */
public class GraphReport extends ApplicationFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int[] arrayAnswers = new int[5];
	static MISPCore manager;

	/**
	 * Default constructor.
	 *
	 * @param title
	 *            the frame title.
	 */
	public GraphReport(String title) {
		super(title);

	}

	// **********************************************************//
	// **********************************************************//
	// *** Results Function ****//
	// **********************************************************//
	// **********************************************************//
	private static PieDataset createDataset() {
		DefaultPieDataset datasetQ = new DefaultPieDataset();

		manager = new MISPCore();

		datasetQ.setValue("Response 1", manager.getResults().get(0));
		datasetQ.setValue("Response 2", manager.getResults().get(1));
		datasetQ.setValue("Response 3", manager.getResults().get(2));
		datasetQ.setValue("Response 4", manager.getResults().get(3));
		datasetQ.setValue("Response 5", manager.getResults().get(4));

		return datasetQ;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Chart ****//
	// **********************************************************//
	// **********************************************************//
	private static JFreeChart createChart(PieDataset dataset) {

		JFreeChart chart = ChartFactory.createPieChart(manager.getQuestion(), // chart
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
	 **/
	public JPanel createPiePanel() {
		JFreeChart chart = createChart(createDataset());
		chart.setBackgroundPaint(GlobalUI.GLOBAL_BACKGROUND_COLOR);
		return new ChartPanel(chart);
	}

	// private void getAnswers() {
	// QuestionnairePanel ansQ = new QuestionnairePanel(null);
	// int[] arrayAnswers = new int[5];
	// for (int a = 0; a < arrayAnswers.length; a++) {
	// arrayAnswers[a] = ansQ.getResult().get(a);
	// }
	//
	// }

	/**
	 * Starting point for the demonstration application.
	 *
	 * @param args
	 *            ignored.
	 */

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	//
	// GraphReport pi = new GraphReport("Q?");
	// pi.pack();
	// RefineryUtilities.centerFrameOnScreen(pi);
	// pi.setVisible(true);

	// }

}
