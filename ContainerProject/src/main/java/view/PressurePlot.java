package view;

import java.util.List;

import model.Container;

public class PressurePlot implements LinePlotType {

	public List<Integer> getData(Container c) {
		return c.getPressureList();
	}
}
