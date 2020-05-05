package model;
import java.util.ArrayList;

// Journey Class
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
	private ArrayList<Container> containers = new ArrayList<Container>();

	
	
	//An empty  default journey constructor needed to create a new instance via reflection by the persistence framework
	
	public Journey() {   
	}

	
	
	// The Journey class contains the journey constructor that initialises the newly created journey object
	
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
	
	public ArrayList<Container> getContainers() {
		return containers;
	}

	public void setContainers(ArrayList<Container> containers) {
		this.containers = containers;
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
	
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public static int getCounter() {
		return counter;
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


}