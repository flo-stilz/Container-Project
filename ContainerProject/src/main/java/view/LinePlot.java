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
	
	private ChartPanel oldChartPanel;
	private String plottitle;
	private ContainerSelectionPanels csp;
	private List<Integer> data;
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

	public void update(Container c) {
		ChartPanel chartPanel = makeChart(c);
		csp.updatePlot(chartPanel, oldChartPanel);
		oldChartPanel = chartPanel;
	}

	public ChartPanel getOldChartPanel() {
		return oldChartPanel;
	}
	public void setOldChartPanel(ChartPanel oldChartPanel) {
		this.oldChartPanel = oldChartPanel;
	}

	public ChartPanel plotCreation(Container c) {
		c.addObserver(this);
		return makeChart(c);
	}

	public ChartPanel makeChart(Container c) {
		
		data = type.getData(c);
		JFreeChart lineChart = ChartFactory.createLineChart("this is the " + plottitle + " plot of Container " + c.getContainerId(), "Time elapsed (min)",
				plottitle, createDataset(data, plottitle), PlotOrientation.VERTICAL, true, true, false);

		ChartPanel chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
		
		initializeOldChart(chartPanel);
		return chartPanel;
	}

	public void initializeOldChart(ChartPanel chartPanel) {
		if (oldChartPanel == null) {
			oldChartPanel = chartPanel;
		}
	}
	}

