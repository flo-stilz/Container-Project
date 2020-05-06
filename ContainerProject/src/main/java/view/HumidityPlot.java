package view;

import java.util.List;

import model.Container;

public class HumidityPlot implements LinePlotType {

	// gets the humidity measurements from container c
	public List<Integer> getData(Container c) {
		return c.getHumList();
	}
}
