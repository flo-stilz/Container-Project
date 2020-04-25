import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Container {
	private String content;
	private String company;
	private String currentLocation;
	private String containerId;
	private String id; //journeyId
    private String containerID;
    private String journeyID;
	private int temp;
	private int pressure;
	private int humidity;
	private static int cCounter = 0;
    private List<Integer> pressureList = new ArrayList<Integer>();
    private List<Integer> humList = new ArrayList<Integer>();
    private List<Integer> tempList = new ArrayList<Integer>(); 
    
    
	public void setPressureList(List<Integer> pressureList) {
		this.pressureList = pressureList;
	}

	public void setHumList(List<Integer> humList) {
		this.humList = humList;
	}

	public void setTempList(List<Integer> tempList) {
		this.tempList = tempList;
	}

	public Container() {
	}
    
    public Container(Container org) {
   	 this.containerId = org.getContainerId();
   	 this.content = org.getContent();
   	 this.company = org.getCompany();
   	 this.id = org.getId(); //journeyId
   	 this.currentLocation = org.getCurrentLocation();
   	 this.pressureList = new ArrayList<Integer>(org.getPressureList());
   	 this.humList = new ArrayList<Integer>(org.getHumList());
   	 this.tempList = new ArrayList<Integer>(org.getTempList()); 
   }
	
	public Container(String content, String company, String id) {
		this.id = id;
		this.content = content;
		this.company = company;
		this.containerId = "C"+ cCounter;
		cCounter++;
	}
	
    public boolean isEmpty() {
    	if (tempList.size() == 0 && humList.size() == 0 && pressureList.size() == 0) {
    		return true;
    	}
    	else { 
    		return false;
    	}
    }

	public static void setcCounter(int cCounter) {
		Container.cCounter = cCounter;
	}
    
    public List<Integer> getPressureList() {
		return pressureList;
	}

	public List<Integer> getHumList() {
		return humList;
	}
	
	public List<Integer> getTempList() {
		return tempList;
	}

	public void setContainerID(String containerID) {
        this.containerID = containerID;
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
	
	public void setContainerId(String containerId) {
		this.containerId = containerId;
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
}
