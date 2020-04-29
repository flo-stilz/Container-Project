package model;


import java.util.ArrayList;
import java.util.List;

public interface observer {

	void update(ArrayList<Integer> t, ArrayList<Integer> p, ArrayList<Integer> h);
	
}
