package com.msip.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

public class GeneralGraph extends ApplicationFrame {

	private static final long serialVersionUID = 1L;
	private static final int HOURS = 0;
	private static final int DAYS = 1;
	private static final int WEEKS = 2;
	private static final int MONTHS = 3;
	private JPanel panel;

	public GeneralGraph(String title) {
		super(title);
	}

	public void createGraph(int graphType, ArrayList<Date> dataset) {

		switch (graphType) {
		case HOURS:
			int[] arrayHours = analyzeHourData(dataset);
			panel = hoursChart(arrayHours);
			break;
		case DAYS:
			int[] arrayDays = analyzeDayData(dataset);
			panel = daysChart(arrayDays);
			break;
		case WEEKS:
			int[] arrayWeeks = analyzeWeekData(dataset);
			panel = weeksChart(arrayWeeks);
			break;
		case MONTHS:
			int[] arrayMonths = analyzeMonthData(dataset);
			panel = monthsChart(arrayMonths);
			break;
		default:
			panel = new JPanel();
			break;
		}
	}

	// **********************************************************//
	// **********************************************************//
	// *** Hours Functions ****//
	// **********************************************************//
	// **********************************************************//

	private int[] analyzeHourData(ArrayList<Date> dataset) {
		int[] arrayHours = new int[24];
		for (int d = 0; d < arrayHours.length; d++) {
			arrayHours[d] = 0;
		}
		for (Date arrH : dataset) {
			int hour = getHours(arrH);
			arrayHours[hour] = arrayHours[hour] + 1;
		}
		return arrayHours;
	}

	/**
	 * @param chartTitle
	 * @return
	 */
	private ChartPanel hoursChart(int[] arrayHours) {

		JFreeChart barChartHr = ChartFactory.createBarChart("", "TIME PERIOD: HOURS", "NUMBER OF VISITS",
				hoursDataset(arrayHours), PlotOrientation.VERTICAL, true, true, false);
		BarRenderer renderer = (BarRenderer) barChartHr.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, GlobalUI.BARCHARTCOLOR);
		barChartHr.removeLegend();
		ChartPanel chartPanelHr = new ChartPanel(barChartHr);
		chartPanelHr.setPreferredSize(new java.awt.Dimension(700, 250));
		setContentPane(chartPanelHr);

		CategoryAxis axis = barChartHr.getCategoryPlot().getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		return chartPanelHr;
	}

	private CategoryDataset hoursDataset(int[] arrayHours) {
		String[] hours = { "1AM", "2AM", "3AM", "4AM", "5AM", "6AM", "7AM", "8AM", "9AM", "10AM", "11AM", "12PM", "1PM",
				"2PM", "3PM", "4PM", "5PM", "6PM", "7PM", "8PM", "9PM", "10PM", "11PM", "12AM" };

		final DefaultCategoryDataset datasetHours = new DefaultCategoryDataset();
		for (int h = 0; h < arrayHours.length; h++) {
			datasetHours.addValue(arrayHours[h], "student", hours[h]);
		}
		return datasetHours;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Days Functions ****//
	// **********************************************************//
	// **********************************************************//

	private int[] analyzeDayData(ArrayList<Date> dataset) {
		int[] arrayDays = new int[8];

		for (int d = 0; d < arrayDays.length; d++) {
			arrayDays[d] = 0;
		}

		for (Date arrD : dataset) {
			int day = getDay(arrD);
			arrayDays[day] = arrayDays[day] + 1;

		}
		return arrayDays;
	}

	/**
	 * @param chartTitle
	 * @return
	 */
	private ChartPanel daysChart(int[] arrayDays) {

		JFreeChart barChartDy = ChartFactory.createBarChart("", "TIME PERIOD: DAYS", "NUMBER OF VISITS",
				dayDataset(arrayDays), PlotOrientation.VERTICAL, true, true, false);
		barChartDy.removeLegend();
		BarRenderer renderer = (BarRenderer) barChartDy.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, GlobalUI.BARCHARTCOLOR);
		ChartPanel chartPanelDy = new ChartPanel(barChartDy);
		chartPanelDy.setPreferredSize(new java.awt.Dimension(700, 250));
		setContentPane(chartPanelDy);

		return chartPanelDy;
	}

	private CategoryDataset dayDataset(int[] arrayDays) {
		String[] Days = { "", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

		final DefaultCategoryDataset datasetDays = new DefaultCategoryDataset();
		for (int d = 1; d < arrayDays.length; d++) {
			datasetDays.addValue(arrayDays[d], "Student", Days[d]);
		}
		return datasetDays;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Week Functions ****//
	// **********************************************************//
	// **********************************************************//

	private int[] analyzeWeekData(ArrayList<Date> dataset) {
		int[] arrayWeeks = new int[54];
		for (int w = 0; w < arrayWeeks.length; w++) {
			arrayWeeks[w] = 0;

		}
		for (Date arrW : dataset) {
			int week = getWeek(arrW);
			arrayWeeks[week] = arrayWeeks[week] + 1;
		}
		return arrayWeeks;
	}

	/**
	 * @param chartTitle
	 * @return
	 */
	private ChartPanel weeksChart(int[] arrayWeeks) {

		JFreeChart barChartWk = ChartFactory.createBarChart("", "TIME PERIOD: WEEKS", "NUMBER OF VISITS",
				weekDataset(arrayWeeks), PlotOrientation.VERTICAL, true, true, false);
		barChartWk.removeLegend();
		BarRenderer renderer = (BarRenderer) barChartWk.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, GlobalUI.BARCHARTCOLOR);
		ChartPanel chartPanelWk = new ChartPanel(barChartWk);
		chartPanelWk.setPreferredSize(new java.awt.Dimension(700, 250));
		setContentPane(chartPanelWk);
		CategoryAxis axis = barChartWk.getCategoryPlot().getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

		return chartPanelWk;
	}

	private CategoryDataset weekDataset(int[] arrayWeeks) {

		String Weeks[] = { "", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
				"17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33",
				"34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
				"51", "52", "53" };

		final DefaultCategoryDataset datasetWeeks = new DefaultCategoryDataset();
		for (int w = 1; w < arrayWeeks.length; w++) {
			datasetWeeks.addValue(arrayWeeks[w], "student", Weeks[w]);
		}
		return datasetWeeks;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Month Functions ****//
	// **********************************************************//
	// **********************************************************//

	private int[] analyzeMonthData(ArrayList<Date> dataset) {
		int[] arrayMonths = new int[12];

		for (int m = 0; m < arrayMonths.length; m++) {
			arrayMonths[m] = 0;
		}

		for (Date arrM : dataset) {
			int month = getMonth(arrM);

			arrayMonths[month] = arrayMonths[month] + 1;
		}
		return arrayMonths;
	}

	/**
	 * @param chartTitle
	 * @param arrayMonths
	 * @return
	 */
	private ChartPanel monthsChart(int[] arrayMonths) {

		JFreeChart barChartMt = ChartFactory.createBarChart("", "TIME PERIOD: MONTHS", "NUMBER OF VISITS",
				MonthDataset(arrayMonths), PlotOrientation.VERTICAL, true, true, false);
		barChartMt.removeLegend();
		BarRenderer renderer = (BarRenderer) barChartMt.getCategoryPlot().getRenderer();
		renderer.setSeriesPaint(0, GlobalUI.BARCHARTCOLOR);
		ChartPanel chartPanelMt = new ChartPanel(barChartMt);
		chartPanelMt.setPreferredSize(new java.awt.Dimension(700, 250));
		setContentPane(chartPanelMt);
		CategoryAxis axis = barChartMt.getCategoryPlot().getDomainAxis();
		axis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		return chartPanelMt;
	}

	private CategoryDataset MonthDataset(int[] arrayMonths) {

		String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		final DefaultCategoryDataset datasetMonths = new DefaultCategoryDataset();

		for (int m = 0; m < arrayMonths.length; m++) {

			datasetMonths.addValue(arrayMonths[m], "student", months[m]);

		}
		return datasetMonths;
	}

	// **********************************************************//
	// **********************************************************//
	// *** Helper Functions ****//
	// **********************************************************//
	// **********************************************************//

	private int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int week = cal.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	private int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int month = cal.get(Calendar.MONTH);
		return month;

	}

	private int getHours(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date(date.getTime()));
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		return hour;

	}

	private int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		return day;
	}

	public JPanel getGraph() {
		return panel;
	}

}