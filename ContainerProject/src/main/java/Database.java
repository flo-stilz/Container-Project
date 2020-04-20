import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Database {
	
	private ArrayList<Journey> journey = new ArrayList<Journey>();
	private ArrayList<Container> containerWarehouse = new ArrayList<Container>();
	private ArrayList<Journey> history = new ArrayList<Journey>();
	private ArrayList<client> clients = new ArrayList<client>();
	private ArrayList<Journey> containerJourneyHistoryList = new ArrayList<Journey>();
	private ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistoryList = new ArrayList<ArrayList<ArrayList<Integer>>>();
	

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
	
	Set<client> search (String c){
		Set<client> results = new HashSet<client>();
		for (client cl: clients) {

			if ((cl.getAddress().contentEquals(c)||cl.getCompany().contentEquals(c)||cl.getEmail().contentEquals(c)||cl.getName().contentEquals(c))) {
				results.add(cl);
			}
		}
		return results;	
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
	
	public client createClient( String company, String address, String email, String name) {
		client c = new client(company, address, email, name);
		clients.add(c);
		return c;
	}

	public Set<Journey> findUsingLoop (String search){
	
			Set<Journey> matches = new HashSet<Journey>();
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
	}
	
	
	public ArrayList<Journey> containerJourneyHistory(String search){
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				if (c.getContainerId().equals(search)) {
					containerJourneyHistoryList.add(j);	
				}
			}
		}
		return containerJourneyHistoryList;			
	}


	public ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistory(String search, ArrayList<Journey> history) {
		for(Journey j : history) {
			for(Container c : j.getContainerList()) {
				ArrayList<ArrayList<Integer>> measurements = new ArrayList<ArrayList<Integer>>();
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
	
}
		

	  

