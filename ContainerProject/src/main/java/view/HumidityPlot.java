package view;

import java.util.List;

import model.Container;

public class HumidityPlot implements LinePlotType {

	public List<Integer> getData(Container c) {
		return c.getHumList();
	}
}
