import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

public class BarPlots extends plot implements observer {
	
	private ChartPanel chartPanel;
	
	public ChartPanel getChartPanel() {
		return chartPanel;
	}

	// create an empty bar plot
	public BarPlots(String plottitle, ContainerSelectionPanels csp, TopMain topmain) {
		super(plottitle, csp, topmain);
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

	// create a bar plot with initial data
	public BarPlots(String plottitle, ArrayList<Integer> temp, ArrayList<Integer> pres, ArrayList<Integer> hum, ContainerSelectionPanels csp, TopMain topmain) {
		super(plottitle, csp, topmain);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.setValue(range(temp), "hum", "Temperature");
		dataset.setValue(range(pres), "hum", "Pressure");
		dataset.setValue(range(hum), "hum", "Volume");

		JFreeChart chart = ChartFactory.createBarChart(" Maximum change in Conditions", "Internal Status",
				"change in values overtime", dataset, PlotOrientation.VERTICAL, false, false, false);

		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setBasePositiveItemLabelPosition(position);

		 chartPanel = new ChartPanel( chart );        
	     chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	     setContentPane( chartPanel ); 

		chart.setBackgroundPaint(Color.WHITE);

	}
//creates a new bar plot with new data
	public void update(ArrayList<Integer> t, ArrayList<Integer> p, ArrayList<Integer> h) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.setValue(range(t), "hum", "Temperature");
		dataset.setValue(range(p), "hum", "Pressure");
		dataset.setValue(range(h), "hum", "Volume");

		JFreeChart chart = ChartFactory.createBarChart(" Maximum change in Conditions", "Internal Status",
				"change in values overtime", dataset, PlotOrientation.VERTICAL, false, false, false);

		CategoryItemRenderer renderer = ((CategoryPlot) chart.getPlot()).getRenderer();

		ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER);
		renderer.setBasePositiveItemLabelPosition(position);

		 chartPanel = new ChartPanel( chart );        
	     chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
	     setContentPane( chartPanel ); 
	     
	     getCsp().updateAllPlots(getTopmain());

		chart.setBackgroundPaint(Color.WHITE);
//		setbackground(chart);
//		display();
		
	}

}