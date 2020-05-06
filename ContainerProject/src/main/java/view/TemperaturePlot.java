package view;

import java.util.List;

import model.Container;

public class TemperaturePlot implements LinePlotType {

	// gets the temperature measurements from container c
	public List<Integer> getData(Container c) {
		return c.getTempList();
	}

}
