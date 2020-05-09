package model;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Application {
	
	private ClientDatabase clientDat = new ClientDatabase();
	private JourneyContainerDatabase journeyContainerDat = new JourneyContainerDatabase();
	private Admin admin = new Admin();
	private Client currentUser;


	public Client getCurrentUser() {
		return currentUser;
	}

	public ClientDatabase getClientDat() {
		return clientDat;
	}
	public JourneyContainerDatabase getJourneyContainerDat() {
		return journeyContainerDat;
	}
	public void store() {
		clientDat.store();
		journeyContainerDat.store();
	}
	
	public void read() {
		clientDat.read();
		journeyContainerDat.read();
	}


//	private ArrayList<Journey> containerJourneyHistoryList = new ArrayList<Journey>();
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public void addObserver(PropertyChangeListener l) {
		support.addPropertyChangeListener(l);
	}

	public void removeObserver(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
	
	public void simulation(int days) {
		Simulator simulation = new Simulator();
		simulation.simulation(this, days); 
	}
	
	//searches for all clients that contain a similair string for either company or email
	public ArrayList<Client> findSimilairClients (String company, String email){
		ArrayList<Client> results = new ArrayList<Client>();
		for (Client c : clientDat.getClients()) {
			if ((c.getCompany().equalsIgnoreCase(company)) 
					||c.getEmail().equalsIgnoreCase(email)) {
				results.add(c);
			}
		}
		return results;
	}
	
	//This method is responsible for creating a new client object
	public Client createClient(String company, String address, String email, String name, String password) {
		if (findSimilairClients(company,email).size() == 0) {
			Client c = new Client(company, address, email, name, password);
			clientDat.getClients().add(c);
			support.firePropertyChange("clients",null,null);
			return c;
		}
		else {
			return findSimilairClients(company, email).get(0);
		}
	}

	//searches for a journey that matches a keyword
	public ArrayList<Journey> findJourneys (String search, ArrayList<Journey> journeyList){
			ArrayList<Journey> matches = new ArrayList<Journey>();
			for (Journey j : journeyList) {
				if ((j.getOrigin().equalsIgnoreCase(search))
						|| (j.getDestination().equalsIgnoreCase(search))
						|| (j.getId().equalsIgnoreCase(search))
						|| (j.getCurrentLocation().equalsIgnoreCase(search))) {
					matches.add(j);
					}
				}
			return matches;
		}
	
	//searches for a client that matches a keyword
	public ArrayList<Client> searchClient (String keyword){
		ArrayList<Client> results = new ArrayList<Client>();
		for (Client cl: clientDat.getClients()) {
			if ((cl.getAddress().equalsIgnoreCase(keyword)
					||cl.getCompany().contentEquals(keyword)
					||cl.getEmail().equalsIgnoreCase(keyword)
					||cl.getName().equalsIgnoreCase(keyword))) {
				results.add(cl);
			}
		}
		return results;	
	}

	//searches for all active journeys that contain a given set of origin and destination
	public ArrayList<Journey> findSimilairJourneys (String origin, String destination){
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
	
	/* Responsible for assigning containers to new journeys in two different ways, 
	 * depending on the size of the container warehouse.
	 */
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
	
	//Handles the creation of new journeys. If a journey with the same destination and origin is ongoing,
	//then another container is added to that journey.
	public Journey createJourney( String origin, String destination, String content, String company) {
		if (findSimilairJourneys( origin, destination).size() == 0) {
			 Journey j = new Journey(origin, destination, content, company);
			 journeyContainerDat.getActiveJourneys().add(j);
			 Container container = assignContainer(content, company, j.getId());
			 j.getContainers().add(container);
			 updateCurrentLocation(j, origin);
			 support.firePropertyChange("journey",null,null);
			 return j;
		}
		else {
			Journey j = findSimilairJourneys( origin, destination).get(0);
			Container container = assignContainer(content, company, j.getId());
			j.getContainers().add(container);
			updateCurrentLocation(j, origin);
			support.firePropertyChange("journey",null,null);
			return findSimilairJourneys( origin, destination).get(0);
		}
	}
	
	// Find containers that match a certain keyword in the active journey list
	public ArrayList<Container> findContainer(String keyword, ArrayList<Journey> journeyList) {
		ArrayList<Container> containers = new ArrayList<Container>();
		for (Journey j : journeyList) {
			for (Container c : j.getContainers()) {
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

	//responsible for moving the journey from the active journeylist to the pastjourney list, 
	//once the journey has reached its destination.
	public void endOfJourney(Journey j) {
		if (j.getDestination().equals(j.getCurrentLocation())) {
			journeyContainerDat.getPastJourneys().add(j);
			journeyContainerDat.storeEndedJourneys();
			for (Container c : j.getContainers()) {
				Container container = new Container(c);
				container.setContainerId(c.getContainerId());
				container.setContent("empty");
				container.setCompany("empty");
				container.setCurrentLocation("container warehouse"); 
				container.getTempList().clear();
				container.getPressureList().clear();
				container.getHumList().clear();
				journeyContainerDat.getContainerWarehouse().add(container);
			}
			journeyContainerDat.getActiveJourneys().remove(j);
		} 
		support.firePropertyChange("history",null,null);
		support.firePropertyChange("journey",null,null);
	}
	// This has been moved to container as well as all the observer part
	public void addData(Container c, int temp, int pressure, int humidity) {
		c.addData(temp, pressure, humidity);
		support.firePropertyChange("journey",null,null);
	} 
	
	//adds internal-status measurements to all containers in a given journey. 
	public void updateData(Journey j, Container c, int temp, int pressure, int humidity) {
		if (c.isEmpty()) {
			for (Container con : j.getContainers()) {
				con.addData(temp, pressure, humidity);
			}
		}
		else {
			c.addData(temp, pressure, humidity);
		}
	}
	
	//finds all internal-status measurement of a given container, throughout each of it completed journeys
	public Container containerInternalStatusHistory(String search, ArrayList<Journey> history) {
		Container containerHis = new Container();
		for(Journey j : history) {
			for(Container c : j.getContainers()) {
				if (c.getContainerId().contentEquals(search)) {
					containerHis.setContainerId(c.getContainerId());
					containerHis.getTempList().addAll(c.getTempList());
					containerHis.getPressureList().addAll(c.getPressureList());
					containerHis.getHumList().addAll(c.getHumList());
				}
			}	
		}
		return containerHis;
	}
	
	//finds all active and past journeys belonging to a given client
	public Set<Journey> findClientJourneys(ArrayList<Journey> journeyList){
		Set<Journey> result = new HashSet<Journey>();
		for ( Journey j : journeyList) {
			for (Container c : j.getContainers()) {
				if (currentUser.getCompany().contentEquals(c.getCompany())) {
					result.add(j);
				}
			}
		}
		return result;
	}
	
	//verifies whether a client fulfills the requirements for registration
	public boolean registrationValidation(String company, String name, String mail, String address, String password) {
		if ((searchClient(company).size() == 0)
						&& (password.length()>4)) {
			createClient(company, address, mail, name, password);
			return true;
		}
		else {
			return false;
		}
	}
	
	//identifies which type of user is login into the system
	public String loginValidation(String username, String password) {
		if (admin.getUsername().contentEquals(username) && admin.getPassword().contentEquals(password)) {
			return "admin";
		}
		else if ((searchClient(username).size())!= 0) {
			Client client = searchClient(username).get(0);
			if (client.getPassword().contentEquals(password)) {
				currentUser = client;
				return "client";
			}
			else {
				return "N/A";
			}
		}
		else {
			return "N/A";
		}
	}
	
	// updates a journeys current location to a new location
	public void updateCurrentLocation(Journey j, String newcurrentLocation) {
		for (int i=0; i < j.getContainers().size(); i++){
			j.getContainers().get(i).setCurrentLocation(newcurrentLocation);
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
}