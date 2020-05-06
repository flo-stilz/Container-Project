package view;

import java.util.List;

import model.Container;

public class PressurePlot implements LinePlotType {

	// gets the pressure measurements from container c
	public List<Integer> getData(Container c) {
		return c.getPressureList();
	}
}
