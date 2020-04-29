


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import model.observer;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class plot extends ApplicationFrame implements observer{
	
	private ChartPanel chartPanel;
	private String plottitle;
	private ContainerSelectionPanels csp;
	private TopMain topmain;
	private String chartType;
	private ArrayList<Integer> a;
	
	public TopMain getTopmain() {
		return topmain;
	}

	public ContainerSelectionPanels getCsp() {
		return csp;
	}

	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	public plot(String plottitle, ContainerSelectionPanels csp, TopMain topmain) {
		super(plottitle);
		this.plottitle = plottitle;
		this.csp = csp;
		this.topmain = topmain;
	}

	public void display() {
		pack();
		RefineryUtilities.centerFrameOnScreen(this);
		setVisible(true);
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void linePlot(List<Integer> data) {
		JFreeChart lineChart = ChartFactory.createLineChart("this is the " + plottitle + " plot", "Time elapsed (min)",
				plottitle, createDataset(data, plottitle), PlotOrientation.VERTICAL, true, true, false);

		chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
		
//		display();
	}




	protected DefaultCategoryDataset createDataset(List<Integer> data, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int t = 0;
		for (int i : data) {

			dataset.addValue(i, type, Integer.toString(t));
			t = t + 10;
		}

		return dataset;
	}


	protected XYDataset create2Dataset(List<Integer> temp2, List<Integer> pres, List<Integer> hum2) {
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

	int range(List<Integer> t) {
		int r = Collections.max(t) - Collections.min(t);
		return r;

	}

	public void update(ArrayList<Integer> t, ArrayList<Integer> p, ArrayList<Integer> h) {
		
		if (this.getTitle().contentEquals("Temperature")) {
			a = t;
		}
		else if (this.getTitle().contentEquals("Pressure")) {
			a = p;
		}
		else {
			a = h;
		}
		
		this.getTitle().contentEquals("Temperature");
		JFreeChart lineChart = ChartFactory.createLineChart("this is the " + plottitle + " plot", "Time elapsed (min)",
				plottitle, createDataset(a, plottitle), PlotOrientation.VERTICAL, true, true, false);

		chartPanel = new ChartPanel(lineChart);
		chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
		setContentPane(chartPanel);
		getCsp().updateAllPlots(getTopmain());
	}
	}

