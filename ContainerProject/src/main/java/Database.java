import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

public class Database {
	
	private ArrayList<Journey> journey = new ArrayList<Journey>();
	private ArrayList<Container> containerWarehouse = new ArrayList<Container>();
	private ArrayList<Journey> history = new ArrayList<Journey>();
	private ArrayList<client> clients = new ArrayList<client>();
	//private ArrayList<observer> obs = new ArrayList<observer>();
	private ArrayList<chartobserver> cobs = new ArrayList<chartobserver>();
	
	private ArrayList<Journey> containerJourneyHistoryList = new ArrayList<Journey>();
	private ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistoryList = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	
	
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	void addObserver(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}
		
	void add (client c) {
		if (!exists(c)) {
		clients.add(c);
		}
	}
	
	boolean exists (client c) {
		for (int i=0; i < clients.size(); i++) {
			if ((clients.get(i)).getId()==c.getId()) {return true;}	
		} 
		return false;
	}
	
	public ArrayList<client> search (String keyword){

//	public ArrayList<client> search (String keyword, ArrayList<client> clients){
		ArrayList<client> results = new ArrayList<client>();
		for (client cl: clients) {

			if ((cl.getAddress().contentEquals(keyword)||cl.getCompany().contentEquals(keyword)||cl.getEmail().contentEquals(keyword)||cl.getName().contentEquals(keyword))) {
				results.add(cl);
			}
		}
		return results;	
	}
	
	public client createClient( String company, String address, String email, String name, String password) {
		client c = new client(company, address, email, name, password);
		clients.add(c);
		storeClients(); 
		support.firePropertyChange("clients",null,null);
		return c;
	}

	public ArrayList<Journey> findUsingLoop (String search, ArrayList<Journey> journeyList){
	
			ArrayList<Journey> matches = new ArrayList<Journey>();
			for (Journey j : journeyList) {
				if ((j.getOrigin().equalsIgnoreCase(search))||
						(j.getDestination().equalsIgnoreCase(search))
						|| (j.getId().equalsIgnoreCase(search))
						|| (j.getCurrentLocation().equalsIgnoreCase(search))) {
					matches.add(j);
					}
				}
			return matches;
			
		}

	public ArrayList<Journey> findJourney (String origin, String destination){
		
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
			container.setId(id);
			return container;
		}
		
	}
	
	public Journey createJourney( String origin, String destination, String content, String company) {
		if (findJourney( origin, destination).size() == 0) {
			 Journey j = new Journey(origin, destination, content, company);
			 journey.add(j);
			 Container container = assignContainer(content, company, j.getId());
			 j.getContainerList().add(container);
			 updateCurrentLocation(j, origin);
			 support.firePropertyChange("journey",null,null);
			 return j;
		}
		else {
			Journey j = findJourney( origin, destination).get(0);
			Container container = assignContainer(content, company, j.getId());
			j.getContainerList().add(container);
			updateCurrentLocation(j, origin);
			support.firePropertyChange("journey",null,null);
			return findJourney( origin, destination).get(0);
		}
	}
	
	// Find containers that match a certain keyword in the active journey list
	
	public ArrayList<Container> findContainer(String keyword, ArrayList<Journey> journeyList) {
		ArrayList<Container> containers = new ArrayList<Container>();
		for (Journey j : journeyList) {
			for (Container c : j.getContainerList()) {
				if ((c.getContainerId().equalsIgnoreCase(keyword)) 
						|| (c.getCompany().equalsIgnoreCase(keyword)) 
						|| (c.getContent().equalsIgnoreCase(keyword))
						|| (c.getCurrentLocation().equalsIgnoreCase(keyword))) {
					containers.add(c);
				}
			}
		}
		return containers;
	}
	
	// rename it afterwards
	public ArrayList<Container> getfilteredContainers(boolean isPast, ArrayList<Journey> jList) {

		ArrayList<Container> Containers = new ArrayList<Container>();
		for (Journey j : jList) {
			for (Container c : j.getContainerList()) {
				Containers.add(c);
			}
		}
		return Containers;
	}
	
	public ArrayList<Container> getAllContainers(boolean isPastOrClient, ArrayList<Journey> jList) {
		
		ArrayList<Container> Containers = getfilteredContainers(isPastOrClient, jList);
		if (isPastOrClient == false) {
			Containers.addAll(containerWarehouse);
		}
		return Containers;
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
		notifychartOberver();
		support.firePropertyChange("history",null,null);
		support.firePropertyChange("journey",null,null);
	}
	// This has been moved to container as well as all the observer part
	public void addData(Container c, int temp, int pressure, int humidity) {
		c.addData(temp, pressure, humidity);
		support.firePropertyChange("journey",null,null);
//		c.getTempList().add(temp);
//		c.getPressureList().add(pressure);
//		c.getHumList().add(humidity);
//		notifyObservers(c);
	}
	
	public void updateData(Journey j, Container c, int temp, int pressure, int humidity) {
		if (c.isEmpty()) {
			for (Container con : j.getContainerList()) {
				con.addData(temp, pressure, humidity);
			}
		}
		else {
			c.addData(temp, pressure, humidity);
		}
	}
	
	// probably not needed anymore
	public Set<Journey> findJourneysFromContainers(String search){
		Set<Journey> result = new HashSet<Journey>();
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				if ((c.getContainerId().equals(search)) 
					|| (c.getContent().equals(search))
					|| (c.getCompany().equals(search))) {
					result.add(j);	
				}
			}
		}
		return result;			
	}


	public ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistory(String search, ArrayList<Journey> history) {
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				ArrayList<ArrayList<Integer>> measurement = new ArrayList<ArrayList<Integer>>();
				if (c.getContainerId().contentEquals(search)) {
					measurement.add(c.getTempList());
					measurement.add(c.getPressureList());
					measurement.add(c.getHumList());
					containerInternalStatusHistoryList.add(measurement);
				}
			}	
		}
		return containerInternalStatusHistoryList;
	}
	
	
//	public void addObserver(observer o) {
//		obs.add(o);
//	}
//
//	private void notifyObservers( Container c) {
//		for (observer o: obs) {
//			o.update(c.getTempList(),c.getPressureList(),c.getHumList());
//		}
//	}
	
	public void addchartObserver(chartobserver o) {
		cobs.add(o);
	}
	//where containers get added to warehouse
	private void notifychartOberver() {
		for (chartobserver o :cobs) {
			o.updateC(containerWarehouse);
		}
	}
	
	public Set<Journey> findClientJourneys(String client, ArrayList<Journey> journeyList){
		Set<Journey> result = new HashSet<Journey>();
		for ( Journey j : journeyList) {
			for (Container c : j.getContainerList()) {
				if (client.contentEquals(c.getCompany())) {
					result.add(j);
				}
			}
		}
		return result;
	}
	
//	public ArrayList<Container> findClientContainers(String client, ArrayList<Container> containerList){
//		ArrayList<Container> result = new ArrayList<Container>();
//		for ( Container c : containerList) {
//			if (client.contentEquals(c.getCompany())) {
//				result.add(c);
//			}
//		}
//		return result;
//	}
	
	public void updateCurrentLocation(Journey j, String newcurrentLocation) {
		for (int i=0; i < j.getContainerList().size(); i++){
			j.getContainerList().get(i).setCurrentLocation(newcurrentLocation);
		}
		j.setCurrentLocation(newcurrentLocation.toUpperCase());
	}
	
	public void updateClientName(client c, String refname) {
		c.updateName(refname);
	}
	
	public void updateClientMail(client c, String mail) {
		c.updateEmail(mail);
	}
	public void updateClientAddress(client c, String address) {
		c.updateAddress(address);
	}
	public void updateClientPassword(client c, String password) {
		c.updatePassword(password);
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

	public void setJourney(ArrayList<Journey> journey) {
		this.journey = journey;
	}
	
	public ArrayList<Journey> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<Journey> history) {
		this.history = history;
	}

	public ArrayList<Container> getContainerWarehouse() {
		return containerWarehouse;
	}

	public void setContainerWarehouse(ArrayList<Container> containerWarehouse) {
		this.containerWarehouse = containerWarehouse;
	}
	public ArrayList<client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<client> clients) {
		this.clients = clients;
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
	
}
		

	  

