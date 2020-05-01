package model;
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

public class Application {
	
	private ClientDatabase clientDat = new ClientDatabase();
	private JourneyContainerDatabase journeyContainerDat = new JourneyContainerDatabase();
	private Admin admin = new Admin();
	private Client currentUser;


	public Client getCurrentUser() {
		return currentUser;
	}
	public Application() {
//		loadData();
	}
	public ClientDatabase getClientDat() {
		return clientDat;
	}
	public JourneyContainerDatabase getJourneyContainerDat() {
		return journeyContainerDat;
	}

//	private ArrayList<Journey> containerJourneyHistoryList = new ArrayList<Journey>();
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addObserver(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}
	public void removeObserver(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
		
//	void add (client c) {
//		if (!exists(c)) {
//		clients.add(c);
//		}
//	}
//	
//	boolean exists (client c) {
//		for (int i=0; i < clients.size(); i++) {
//			if ((clients.get(i)).getId()==c.getId()) {return true;}	
//		} 
//		return false;
//	}
	

	
	public Client createClient( String company, String address, String email, String name, String password) {
		Client c = new Client(company, address, email, name, password);
		clientDat.getClients().add(c);
		clientDat.storeClients(); 
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
	
	public ArrayList<Client> search (String keyword){
		ArrayList<Client> results = new ArrayList<Client>();
		for (Client cl: clientDat.getClients()) {

			if ((cl.getAddress().equalsIgnoreCase(keyword)||cl.getCompany().contentEquals(keyword)||cl.getEmail().equalsIgnoreCase(keyword)||cl.getName().equalsIgnoreCase(keyword))) {
				results.add(cl);
			}
		}
		return results;	
	}

	public ArrayList<Journey> findJourney (String origin, String destination){
		
		ArrayList<Journey> results = new ArrayList<Journey>();
		for (Journey j : journeyContainerDat.getActiveJourneys()) {
			if ((j.getOrigin().equalsIgnoreCase(origin))&&
					(j.getDestination().equalsIgnoreCase(destination))&&
					(j.getCurrentLocation().equalsIgnoreCase(origin))) {
				results.add(j);
				}
			}
		return results;
		
	}
	
	
	
	public Container assignContainer(String content, String company, String id) {
		if (journeyContainerDat.getContainerWarehouse().size() == 0) {
			Container container = new Container( content, company, id);
			return container;
		}
		else {
			Container container = journeyContainerDat.getContainerWarehouse().get(0);
			journeyContainerDat.getContainerWarehouse().remove(0);
			container.setCompany(company);
			container.setContent(content);
			container.setId(id);
			return container;
		}
		
	}
	
	public Journey createJourney( String origin, String destination, String content, String company) {
		if (findJourney( origin, destination).size() == 0) {
			 Journey j = new Journey(origin, destination, content, company);
			 journeyContainerDat.getActiveJourneys().add(j);
			 Container container = assignContainer(content, company, j.getId());
			 j.getContainerList().add(container);
			 updateCurrentLocation(j, origin);
			 support.firePropertyChange("journey",null,null);
			 journeyContainerDat.storeActiveJourneys();
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
	
	

	
	public void endOfJourney(Journey j) {
		if (j.getDestination().equals(j.getCurrentLocation())) {
			journeyContainerDat.getPastJourneys().add(j);
			journeyContainerDat.storeEndedJourneys();
			for (Container c : j.getContainerList()) {
				Container container = new Container(c);
				container.setContainerID(c.getContainerId());
				container.setContent("empty");
				container.setCompany("empty");
				container.setCurrentLocation("container warehouse");
				container.getTempList().clear();
				container.getPressureList().clear();
				container.getHumList().clear();
				journeyContainerDat.getContainerWarehouse().add(container);
				journeyContainerDat.storeContainerWarehouse();
			}
			journeyContainerDat.getActiveJourneys().remove(j);
			journeyContainerDat.storeActiveJourneys();
		} 
		support.firePropertyChange("history",null,null);
		support.firePropertyChange("journey",null,null);
	}
	// This has been moved to container as well as all the observer part
	public void addData(Container c, int temp, int pressure, int humidity) {
		c.addData(temp, pressure, humidity);
		support.firePropertyChange("journey",null,null);
		journeyContainerDat.storeActiveJourneys();
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
	
//	// probably not needed anymore
//	
//	public Set<Journey> findJourneysFromContainers(String search){
//		Set<Journey> result = new HashSet<Journey>();
//		for(Journey j : history) {
//			for(Container c : j.getContainerList()) {
//				if ((c.getContainerId().equals(search)) 
//					|| (c.getContent().equals(search))
//					|| (c.getCompany().equals(search))) {
//					result.add(j);	
//				}
//			}
//		}
//		return result;			
//	}
	
	
	//Needs to loose a arraylist from the output. 
	public Container containerInternalStatusHistory(String search, ArrayList<Journey> history) {
		Container containerHis = new Container(findContainer(search, history).get(0));
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				if (c.getContainerId().contentEquals(search)) {
					containerHis.getTempList().addAll(c.getTempList());
					containerHis.getPressureList().addAll(c.getPressureList());
					containerHis.getHumList().addAll(c.getHumList());
				}
			}	
		}
		return containerHis;
	}
	
	
//	public ArrayList<ArrayList<Integer>> containerInternalStatusHistory(String search, ArrayList<Journey> history) {
//		ArrayList<Integer> a = new ArrayList<Integer>();
//		ArrayList<Integer> a1 = new ArrayList<Integer>();
//		ArrayList<Integer> a2 = new ArrayList<Integer>();
//		ArrayList<ArrayList<Integer>> measurement = new ArrayList<ArrayList<Integer>>();
//		for(Journey j : history) {
//			for(Container c : j.getContainerList()) {
//				if (c.getContainerId().contentEquals(search)) {
//					measurement.add(c.getTempList());
//					measurement.add(c.getPressureList());
//					measurement.add(c.getHumList());
//					containerInternalStatusHistoryList.add(measurement);
//				}
//			}	
//		}
//		return containerInternalStatusHistoryList;
//	}
	
	
	
//	public void addObserver(observer o) {
//		obs.add(o);
//	}
//
//	private void notifyObservers( Container c) {
//		for (observer o: obs) {
//			o.update(c.getTempList(),c.getPressureList(),c.getHumList());
//		}
//	}
	
	public Set<Journey> findClientJourneys(ArrayList<Journey> journeyList){
		Set<Journey> result = new HashSet<Journey>();
		for ( Journey j : journeyList) {
			for (Container c : j.getContainerList()) {
				if (currentUser.getCompany().contentEquals(c.getCompany())) {
					result.add(j);
				}
			}
		}
		return result;
	}
	
	public void loadData(){
		clientDat.readClientFile();
		journeyContainerDat.readActiveJourneyFile();
		journeyContainerDat.readEndedJourneyFile();
		journeyContainerDat.readContainerWarehouseFile();
	}
	
	public boolean registrationValidation(String company, String name, String mail, String address, String password) {
		if ((search(company).size() == 0)
						&& (password.length()>4)) {
			createClient(company, address, mail, name, password);
			return true;
		}
		else {
			return false;
		}
	}
	public String loginValidation(String username, String password) {
		if (admin.getUsername().contentEquals(username) && admin.getPassword().contentEquals(password)) {
			return "admin";
//			company.dispose();
		}
		else if ((search(username).size())!= 0) {
	
			Client client = search(username).get(0);
			
			if (client.getPassword().contentEquals(password)) {
				currentUser = client;
				return "client";
//				LoginFrame.dispose();
			}
			else {
				return "N/A";
			}
		}
		else {
			return "N/A";
		}
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
		support.firePropertyChange("journey",null,null);
	}
	
	public void updateClientName(Client c, String refname) {
		c.setName(refname);
		support.firePropertyChange("clients",null,null);
	}
	
	public void updateClientMail(Client c, String mail) {
		c.setEmail(mail);
		support.firePropertyChange("clients",null,null);
	}
	public void updateClientAddress(Client c, String address) {
		c.setAddress(address);
		support.firePropertyChange("clients",null,null);
	}
	public void updateClientPassword(Client c, String password) {
		c.setPassword(password);
	}
	
	
	
	
	
	
	
	
	
	


	
	
	
	
	
//	public ArrayList<Journey> containerJourneyHistory(String search, ArrayList<Journey> history){
//		for(Journey j : history) {
//			for(Container c : j.getContainerList()) {
//				if (c.getContainerId().equals(search)) {
//					containerJourneyHistoryList.add(j);	
//				}
//			}
//		}
//		return containerJourneyHistoryList;			
//	}
	
}
		

	  

