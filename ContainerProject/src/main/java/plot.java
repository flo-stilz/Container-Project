


import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class plot extends ApplicationFrame {
	public plot(String plottitle) {
		super(plottitle);
	}

	String chartType;
	ArrayList<Integer> a;

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
//this sets the background to a really stupid picture
	void setbackground(JFreeChart c) {
		c.getPlot().setBackgroundAlpha(0);

		c.setBackgroundPaint(Color.WHITE);

		Image icon;
		try {
			icon = ImageIO.read(new File("img/background.png"));
			c.setBackgroundImage(icon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected DefaultCategoryDataset createDataset(ArrayList<Integer> a, String type) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int t = 0;
		for (int i : a) {

			dataset.addValue(i, type, Integer.toString(t));
			t = t + 10;
		}

		return dataset;
	}


	protected XYDataset create2Dataset(ArrayList<Integer> t, ArrayList<Integer> p, ArrayList<Integer> h) {
		final XYSeries temp = new XYSeries("Temperature");
		int c = 0;
		for (int i : t) {
			temp.add(c, i);
			c = c + 10;
		}
		final XYSeries pre = new XYSeries("Pressure");
		c = 0;
		for (int i : p) {
			pre.add(c, i);
			c = c + 10;
		}
		final XYSeries hum = new XYSeries("Humidity");
		c = 0;
		for (int i : h) {
			hum.add(c, i);
			c = c + 10;
		}
		final XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(hum);
		dataset.addSeries(pre);
		dataset.addSeries(temp);
		return dataset;
	}

	int range(ArrayList<Integer> a) {
		int r = Collections.max(a) - Collections.min(a);
		return r;

	}

	}

