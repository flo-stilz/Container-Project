import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Database {
	private ArrayList<Journey> journey = new ArrayList<Journey>();
	private ArrayList<Container> containerWarehouse = new ArrayList<Container>();
	private ArrayList<Journey> history = new ArrayList<Journey>();
	private ArrayList<client> clients = new ArrayList<client>();
	private ArrayList<Journey> containerJourneyHistoryList = new ArrayList<Journey>();
	private List<List<List<Integer>>> containerInternalStatusHistoryList = new ArrayList<List<List<Integer>>>();
	
	//not covered
	void add (client c) {
		if (!exists(c)) {
		clients.add(c);
		}
	}
	//covered? 
	boolean exists (client c) {
		for (int i=0; i < clients.size(); i++) {
			if ((clients.get(i)).getId()==c.getId()) {return true;}	
		} 
		return false;
	}
	
	public ArrayList<client> search (String c, ArrayList<client> clients){
		ArrayList<client> results = new ArrayList<client>();
		for (client cl : clients) {
			if ((cl.getAddress().contentEquals(c))||(cl.getCompany().contentEquals(c))||(cl.getEmail().contentEquals(c))||(cl.getName().contentEquals(c))) {
				results.add(cl);
			}
		}
		return results;	
	}
	
	public client createClient(String company, String address, String email, String name) {
		client c = new client(company, address, email, name);
		clients.add(c);
		storeClients(); 
		return c;
	}
	
	public ArrayList<Journey> findUsingLoop (String search, ArrayList<Journey> journey){
			ArrayList<Journey> matches = new ArrayList<Journey>();
			for (Journey j : journey) {
				if ((j.getOrigin().equalsIgnoreCase(search))||
						(j.getDestination().equalsIgnoreCase(search))) {
					matches.add(j);
					}
				}
			return matches;
		}

	public ArrayList<Journey> findJourney (String origin, String destination, ArrayList<Journey> journey){
		ArrayList<Journey> results = new ArrayList<Journey>();
		for (Journey j : journey) {
			if ((j.getOrigin().equalsIgnoreCase(origin))&&
					(j.getDestination().equalsIgnoreCase(destination))&&
					(j.getCurrentLocation().equalsIgnoreCase(origin))) {
				results.add(j);
				}
			}
		return results;
	}
	
	public Container assignContainer(String content, String company, String id) {
		if (containerWarehouse.size() == 0) {
			Container container = new Container( content, company, id);
			return container;
		}
		else {
			Container container = containerWarehouse.get(0);
			containerWarehouse.remove(0);
			container.setCompany(company);
			container.setContent(content);
			return container;
		}
	}
	
	public Journey createJourney( String origin, String destination, String content, String company) {
		if (findJourney( origin, destination, journey).size() == 0) {
			 Journey j = new Journey(origin, destination, content, company);
			 journey.add(j);
			 Container container = assignContainer(content, company, j.getId());
			 j.getContainerList().add(container);
			 return j;
		}
		else {
			Container container = assignContainer(content, company, findJourney( origin, destination, journey).get(0).getId());
			findJourney( origin, destination, journey).get(0).getContainerList().add(container);
			findJourney( origin, destination, journey).get(0).updateCurrentLocation(origin);
			return findJourney( origin, destination, journey).get(0);
		}
	}
	
	public void endOfJourney(Journey j) {
		if (j.getDestination().equals(j.getCurrentLocation())) {
			history.add(j);
			storeEndedJourneys();
			for (Container c : j.getContainerList()) {
				Container container = new Container(c);
				container.setContainerID(c.getContainerId());
				container.setContent("empty");
				container.setCompany("empty");
				container.setCurrentLocation("container warehouse");
				container.getTempList().clear();
				container.getPressureList().clear();
				container.getHumList().clear();
				getContainerWarehouse().add(container);
				storeContainerWarehouse();
			}
			journey.remove(j);
		} 
	}
	
	public void addData(Container c, int temp, int pressure, int humidity) {
		c.getTempList().add(temp);
		c.getPressureList().add(pressure);
		c.getHumList().add(humidity);
	}
	
	public void updateData(Journey j, Container c, int temp, int pressure, int humidity) {
		if (c.isEmpty()) {
			for (Container con : j.getContainerList()) {
				addData(con, temp, pressure, humidity);
			}
		}
		else {
			addData(c, temp, pressure, humidity);
		}
		storeActiveJourneys();
	}
	
	public ArrayList<Journey> containerJourneyHistory(String search, ArrayList<Journey> history){
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				if (c.getContainerId().equals(search)) {
					containerJourneyHistoryList.add(j);	
				}
			}
		}
		return containerJourneyHistoryList;			
	}
	
	
	
//	public Map<measurements, history> something = new HashMap<measurements, history>();		
//	public Map<measurements, history> methodname(String search, ArrayList<Journey> history){
//		
//	}
	
	public List<List<List<Integer>>> containerInternalStatusHistory(String search, ArrayList<Journey> history) {
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				List<List<Integer>> measurements = new ArrayList<List<Integer>>();
				if (c.getContainerId().contentEquals(search)) {
					measurements.add(c.getTempList());
					measurements.add(c.getPressureList());
					measurements.add(c.getHumList());
					containerInternalStatusHistoryList.add(measurements);
				}
			}	
		}
		return containerInternalStatusHistoryList;
	}
	
	
	
	public void storeClients() {
		storeClients(clients);
	}
	
	public void storeClients(ArrayList<client> clients) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./Clients.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(clients);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	
	public ArrayList<client> readClientFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./Clients.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		clients = (ArrayList<client>)decoder.readObject(); 
		decoder.close();
		return clients;
	}
	
	public void storeActiveJourneys() {
		storeActiveJourneys(journey);
	}
	
	public void storeActiveJourneys(ArrayList<Journey> journey) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ActiveJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(journey);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace(); 
		} 
	}
	 
	public ArrayList<Journey> readActiveJourneyFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ActiveJourneys.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		journey = (ArrayList<Journey>)decoder.readObject();
		decoder.close();
		return journey;
		}
	
	
	public void storeEndedJourneys() {
		storeEndedJourneys(history);
	}
	
	public void storeEndedJourneys(ArrayList<Journey> history) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./EndedJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(history);
			encoder.close();
			fos.close();
		} catch (IOException ex) { 
			ex.printStackTrace();
		}
	}

	public ArrayList<Journey> readEndedJourneyFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./EndedJourneys.xml"));
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}
		XMLDecoder decoder = new XMLDecoder(fis);
		journey = (ArrayList<Journey>)decoder.readObject();
		decoder.close();
		return journey;
	}
	
	public void storeContainerWarehouse() {
		storeContainerWarehouse(containerWarehouse);
	}
	
	public void storeContainerWarehouse(ArrayList<Container> ContainerWarehouse) {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ContainerWarehouse.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(ContainerWarehouse);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public ArrayList<Container> readContainerWarehouseFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ContainerWarehouse.xml"));
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}
		XMLDecoder decoder = new XMLDecoder(fis);
		containerWarehouse = (ArrayList<Container>)decoder.readObject();
		decoder.close();
		return containerWarehouse;
	}
	
	public ArrayList<Journey> getJourney() {
		return journey;
	}
	
	public ArrayList<Journey> getHistory() {
		return history;
	}

	public ArrayList<Container> getContainerWarehouse() {
		return containerWarehouse;
	}
	
	public ArrayList<client> getClients() {
		return clients;
	}
}
		

	  

