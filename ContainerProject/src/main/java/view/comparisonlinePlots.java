package view;


import java.awt.BasicStroke;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import model.Container;
import model.observer;

public class comparisonlinePlots extends ApplicationFrame implements observer, Graph {
	
	private ChartPanel oldChartPanel;
	private ContainerSelectionPanels csp;
	
	public comparisonlinePlots(String plottitle, ContainerSelectionPanels csp) {
		super(plottitle);
		this.csp = csp;
		ArrayList<Integer> e = new ArrayList<Integer>();
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Changes in Container's Enviornment",
			"Time elapsed from Journey", "changes", create2Dataset(e,e,e), PlotOrientation.VERTICAL, true, true,
			false);

		ChartPanel chartPanel = new ChartPanel(xylineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));

	
	}

	public XYDataset create2Dataset(List<Integer> temp2, List<Integer> pres, List<Integer> hum2) {
		final XYSeries temp = new XYSeries("Temperature");
		int c = 0;
		for (int i : temp2) {
			temp.add(c, i);
			c = c + 10;
		}
		final XYSeries pre = new XYSeries("Pressure");
		c = 0;
		for (int i : pres) {
			pre.add(c, i);
			c = c + 10;
		}
		final XYSeries hum = new XYSeries("Humidity");
		c = 0;
		for (int i : hum2) {
			hum.add(c, i);
			c = c + 10;
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(hum);
		dataset.addSeries(pre);
		dataset.addSeries(temp);
		return dataset;
	}
	
	
public ChartPanel plotCreation(Container c) {
		c.addObserver(this);
		return makeChart(c);
	}

	public void update(Container c) {
		
		ChartPanel chartPanel = makeChart(c);
		csp.updatePlot(chartPanel, oldChartPanel);
		oldChartPanel = chartPanel;
	}
	
	
	public ChartPanel makeChart(Container c) {
		ArrayList<Integer> t = c.getTempList();
		ArrayList<Integer> p = c.getPressureList();
		ArrayList<Integer> h = c.getHumList();
		
		JFreeChart xylineChart = ChartFactory.createXYLineChart("Changes in Container's Enviornment for Container "+c.getContainerId(),
				"Time elapsed from Journey", "changes", create2Dataset(t, p, h), PlotOrientation.VERTICAL, true, true,
				false);

		ChartPanel chartPanel = new ChartPanel(xylineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		final XYPlot plot = xylineChart.getXYPlot();

		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.RED);
		renderer.setSeriesPaint(1, Color.GREEN);
		renderer.setSeriesPaint(2, Color.YELLOW);
		renderer.setSeriesStroke(0, new BasicStroke(4.0f));
		renderer.setSeriesStroke(1, new BasicStroke(3.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));
		plot.setRenderer(renderer);
		setContentPane(chartPanel);
		
		if (oldChartPanel == null) {
			oldChartPanel = chartPanel;
		}

		return chartPanel;
	}
}
