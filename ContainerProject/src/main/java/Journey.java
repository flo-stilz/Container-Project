import java.util.ArrayList;

public class Journey {
	private String destination;
	private String origin;
	private String id;
	private String currentLocation;
	private int distance;
	

	private ArrayList<Container> containerList = new ArrayList<Container>();

	
	
	private static int counter = 0;
	
	
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

	public void updateCurrentLocation(String newcurrentLocation) {
		for (int i=0; i < containerList.size(); i++){
			containerList.get(i).setCurrentLocation(newcurrentLocation);
		}
		this.currentLocation = newcurrentLocation.toUpperCase();
	}
	
	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
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
