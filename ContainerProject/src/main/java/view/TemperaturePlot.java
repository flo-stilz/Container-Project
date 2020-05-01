package view;

import java.util.List;

import model.Container;

public class TemperaturePlot implements LinePlotType {

	public List<Integer> getData(Container c) {
		return c.getTempList();
	}

}
