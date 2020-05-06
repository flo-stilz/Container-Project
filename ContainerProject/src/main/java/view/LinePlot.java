package view;


import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;

import model.Container;
import model.observer;

import org.jfree.data.category.DefaultCategoryDataset;

public class LinePlot extends ApplicationFrame implements observer, Graph {
	
	// stores the previously made chart panel
	private ChartPanel oldChartPanel;
	// the plot title
	private String plottitle;
	// the corresponding container selection panel object
	private ContainerSelectionPanels csp;
	// data to plot
	private List<Integer> data;
	// type of single line plot
	private LinePlotType type;

	public LinePlot(String plottitle, ContainerSelectionPanels csp, LinePlotType type) {
		super(plottitle);
		this.plottitle = plottitle;
		this.csp = csp;
		this.type = type; 
	}

	public DefaultCategoryDataset createDataset(List<Integer> data, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int t = 0;
		for (int i : data) {

			dataset.addValue(i, type, Integer.toString(t));
			t = t + 10;
		}

		return dataset;
	}

	// updates the chartPanel and fast forwards it to the containerSelectionPanels
	// afterwards sets the oldChartPanel to the just created one
	public void update(Container c) {
		ChartPanel chartPanel = makeChart(c);
		csp.updatePlot(chartPanel, oldChartPanel);
		oldChartPanel = chartPanel;
	}
	
	// adds the plot to the observer list and calls makeChart(c) which creates the chart panel
	public ChartPanel plotCreation(Container c) {
		c.addObserver(this);
		return makeChart(c);
	}
	
	// creates a single line plot with data given by the input container c and return the chartPanel consisting of the plot
	public ChartPanel makeChart(Container c) {
		
		// gets the list of data points from the container c depending on the type(temp,pres,hum)
		data = type.getData(c);
		JFreeChart lineChart = ChartFactory.createLineChart("this is the " + plottitle + " plot of Container " + c.getContainerId(), "Time elapsed (min)",
				plottitle, createDataset(data, plottitle), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
		
		initializeOldChart(chartPanel);
		return chartPanel;
	}

	// initialisation of the oldChartPanel
	public void initializeOldChart(ChartPanel chartPanel) {
		if (oldChartPanel == null) {
			oldChartPanel = chartPanel;
		}
	}
	}

