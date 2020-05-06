package view;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.TextAnchor;

import model.Container;
import model.observer;

public class barPlots extends ApplicationFrame implements observer, Graph {
	
	// the corresponding container selection panel object
	private ContainerSelectionPanels csp;
	// stores the previously made chart panel
	private ChartPanel oldChartPanel;

	public barPlots(String plottitle, ContainerSelectionPanels csp) {
		super(plottitle);
		this.csp = csp;
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.setValue(0, "", "Temperature");
		dataset.setValue(0, "", "Pressure");
		dataset.setValue(0, "", "Volume");

		JFreeChart chart = ChartFactory.createBarChart(" Maximum change in Conditions", "Internal Status",
				"change in values overtime", dataset, PlotOrientation.VERTICAL, false, false, false);

		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setBasePositiveItemLabelPosition(position);

	
		chart.setBackgroundPaint(Color.WHITE);

	}
	
	int range(List<Integer> t) {
	int r = Collections.max(t) - Collections.min(t);
	return r;

}
	// adds the plot to the observer list and calls makeChart(c) which creates the chart panel
	public ChartPanel plotCreation(Container c) {
		c.addObserver(this);
		return makeChart(c);
	}

	// updates the chartPanel and fast forwards it to the containerSelectionPanels
	// afterwards sets the oldChartPanel to the just created one
	public void update(Container c) {
	
		ChartPanel chartPanel = makeChart(c);
		csp.updatePlot(chartPanel, oldChartPanel);
		oldChartPanel = chartPanel;
	}
	
	// creates a bar plot with data given by the input container c and return the chartPanel consisting of the plot
	public ChartPanel makeChart(Container c) {
		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		
		ArrayList<Integer> t = c.getTempList();
		ArrayList<Integer> p = c.getPressureList();
		ArrayList<Integer> h = c.getHumList();
		dataset.setValue(range(t), "hum", "Temperature");
		dataset.setValue(range(p), "hum", "Pressure");
		dataset.setValue(range(h), "hum", "Volume");

		// plot title and axis description
		JFreeChart chart = ChartFactory.createBarChart(" Maximum change in Conditions for Container "+c.getContainerId(), "Internal Status",
				"change in values overtime", dataset, PlotOrientation.VERTICAL, false, false, false);

		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setBasePositiveItemLabelPosition(position);

		ChartPanel chartPanel = new ChartPanel( chart );        
	    chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	    setContentPane( chartPanel ); 
		chart.setBackgroundPaint(Color.WHITE);
		// initialisation of the oldChartPanel
		if (oldChartPanel == null) {
			oldChartPanel = chartPanel;
		}
		
		return chartPanel;
	}



}