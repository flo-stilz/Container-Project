import java.util.ArrayList;
import java.util.List;

public class Container {
	private String containerId;
	private String content;
	private String company;
	private String id; //journeyId
	private String currentLocation;
	
    //Take input and store data
    private List<Integer> pressureList = new ArrayList<Integer>();
    private List<Integer> humList = new ArrayList<Integer>();
    private List<Integer> tempList = new ArrayList<Integer>(); 
    
    public void setPressureList(ArrayList<Integer> pressureList) {
		this.pressureList = pressureList;
	}
	public void setHumList(ArrayList<Integer> humList) {
		this.humList = humList;
	}
	public void setTempList(ArrayList<Integer> tempList) {
		this.tempList = tempList;
	}



//	private String containerID;
//    private String journeyID;
//    private String uniqueID = containerID + "," + journeyID;   
//    private String holdID1;
//    private String holdID2;
//    private String holdID3;
//    private ArrayList <String> outPressure = new ArrayList<String>(); 
//    private ArrayList <String> outHum = new ArrayList<String>(); 
//    private ArrayList <String> outTemp = new ArrayList<String>();
    
    private ArrayList<observer> obs = new ArrayList<observer>();

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
    
    
	
	private static int cCounter = 0;
	
	public Container( String content, String company, String id) {
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
			o.update(c.getTempList(),c.getPressureList(),c.getHumList());
		}
	}
    
    
	public static int getcCounter() {
		return cCounter;
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

//	public void setContainerID(String containerID) {
//        this.containerID = containerID;
//    }
//
//	public void setJourneyID(String journeyID) {
//		this.journeyID = journeyID;
//	}

//	public void getExpandedUniqueIDAll(String uniqueID) {
//        holdID1 = uniqueID + "," + "pressure";
//        holdID2 = uniqueID + "," + "hum";
//        holdID3 = uniqueID + "," + "temp";
//        outPressure.add(holdID1);
//        outHum.add(holdID2);
//        outTemp.add(holdID3);
//    }
//    
//    public String getUniqueID() {
//		return uniqueID;
//	}
	public String getContainerId() {
		return containerId;
	}

	public String getContent() {
		return content;
	}
	public void setContainerId(String containerId) {
		this.containerId = containerId;
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



}
