package model;
import java.util.ArrayList;
import java.util.List;

public class Container {
	private String containerId;
	private String content;
	private String company;
	private String id; //journeyId
	private String currentLocation;
	
    //Take input and store data
    private ArrayList<Integer> pressureList = new ArrayList<Integer>();
    private ArrayList<Integer> humList = new ArrayList<Integer>();
    private ArrayList<Integer> tempList = new ArrayList<Integer>(); 
    
    private static int cCounter = 0;
    private ArrayList<observer> obs = new ArrayList<observer>();
    
    public void setPressureList(ArrayList<Integer> pressureList) {
		this.pressureList = pressureList;
	}

	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}

	public void setHumList(ArrayList<Integer> humList) {
		this.humList = humList;
	}

	public void setTempList(ArrayList<Integer> tempList) {
		this.tempList = tempList;
	}
    
	//Allows for creation of a java bean for the persistency layer
	public Container() {
	}
    
	//This constructor takes as input a container c.  
	//And creates a deep copy of the container c.
    public Container(Container c) {
    	this.containerId = c.getContainerId();
   	 	this.content = c.getContent();
   	 	this.company = c.getCompany();
   	 	this.id = c.getId(); //journeyId
   	 	this.currentLocation = c.getCurrentLocation();
   	 	this.pressureList = new ArrayList<Integer>(c.getPressureList());
   	 	this.humList = new ArrayList<Integer>(c.getHumList());
   	 	this.tempList = new ArrayList<Integer>(c.getTempList()); 
   }
   
    //Adds an integer to all the different internal-status measurement lists
    public void addData(int temp, int pressure, int humidity) {
		getTempList().add(temp);
		getPressureList().add(pressure);
		getHumList().add(humidity);
		notifyObservers(this);
	}
    
    public void addObserver(observer o) {
		obs.add(o);
	}

	private void notifyObservers( Container c) {
		for (observer o: obs) {
			o.update(c);
		}
	}
	
	//Instances a client object
	public Container( String content, String company, String id) {
		this.id = id;
		this.content = content;
		this.company = company;
		this.containerId = "C"+ cCounter;
		cCounter++;
	}
	
	//checks if the size of all internal-status measurements lists are empty. 
    public boolean isEmpty() {
    	if (tempList.size() == 0 && humList.size() == 0 && pressureList.size() == 0) {
    		return true;
    	}
    	else { 
    		return false;
    	}
    }

	public static int getcCounter() {
		return cCounter;
	}
	public static void setcCounter(int cCounter) {
		Container.cCounter = cCounter;
	}
    
    public ArrayList<Integer> getPressureList() {
		return pressureList;
	}

	public ArrayList<Integer> getHumList() {
		return humList;
	}
	
	public ArrayList<Integer> getTempList() {
		return tempList;
	}

	public String getContainerId() {
		return containerId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCurrentLocation() {
		return currentLocation;
	}
	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public ArrayList<observer> getObs() {
		return obs;
	}
}
