package model;
import java.util.ArrayList;

public class Journey {
	private String destination;
	private String origin;
	private String id;
	private String currentLocation;
	private int distance;
	private int temp;
	private int pressure;
	private int humidity;
	private static int counter = 0;
	private ArrayList<Container> containerList = new ArrayList<Container>();
	
	//for persistency layer
	public Journey() {
	}


	public Journey(String origin, String destination, String content, String company) {
		
			this.origin = origin.toUpperCase();
			this.destination = destination.toUpperCase();
			this.id = origin.toUpperCase() + destination.toUpperCase() + counter;
			this.currentLocation = this.origin;		
			counter++;

	}
	
	public static void setCounter(int counter) {
		Journey.counter = counter;
	}
	
	public ArrayList<Container> getContainerList() {
		return containerList;
	}

	public void setContainerList(ArrayList<Container> containerList) {
		this.containerList = containerList;
	}

	public String getId() {
		return id;
	}

	public String getDestination() {
		return destination;
	}

	public String getOrigin() {
		return origin;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

//	public void updateCurrentLocation(String newcurrentLocation) {
//		for (int i=0; i < containerList.size(); i++){
//			containerList.get(i).setCurrentLocation(newcurrentLocation);
//		}
//		this.currentLocation = newcurrentLocation.toUpperCase();
//	}
	
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setId(String id) {
		this.id = id;
	}

	// finds containers within a journey depending on a keyword
	
	public ArrayList<Container> findContainers(String keyword) {
		
		ArrayList<Container> containers = new ArrayList<Container>();
		for (Container c : containerList) {
			
			if ((c.getContainerId().equalsIgnoreCase(keyword)) 
					|| (c.getContent().equalsIgnoreCase(keyword))
					|| (c.getCompany().equalsIgnoreCase(keyword))) {
				containers.add(c);
			}
		}
		return containers;
	}

}
