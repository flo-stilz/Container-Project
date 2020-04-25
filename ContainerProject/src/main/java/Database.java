import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Database {
	
	private ArrayList<Journey> journey = new ArrayList<Journey>();
	private ArrayList<Container> containerWarehouse = new ArrayList<Container>();
	private ArrayList<Journey> history = new ArrayList<Journey>();
	private ArrayList<client> clients = new ArrayList<client>();
	private ArrayList<observer> obs = new ArrayList<observer>();
	private ArrayList<chartobserver> cobs = new ArrayList<chartobserver>();
	

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
		ArrayList<client> results = new ArrayList<client>();
		for (client cl: clients) {

			if ((cl.getAddress().contentEquals(keyword)||cl.getCompany().contentEquals(keyword)||cl.getEmail().contentEquals(keyword)||cl.getName().contentEquals(keyword))) {
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
	
	public client createClient( String company, String address, String email, String name, String password) {
		client c = new client(company, address, email, name, password);
		clients.add(c);
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
			return container;
		}
		
	}
	
	public Journey createJourney( String origin, String destination, String content, String company) {
		if (findJourney( origin, destination).size() == 0) {
			 Journey j = new Journey(origin, destination, content, company);
			 journey.add(j);
			 Container container = assignContainer(content, company, j.getId());
			 j.getContainerList().add(container);
			 j.updateCurrentLocation(origin);
			 return j;
		}
		else {
			Container container = assignContainer(content, company, findJourney( origin, destination).get(0).getId());
			findJourney( origin, destination).get(0).getContainerList().add(container);
			findJourney( origin, destination).get(0).updateCurrentLocation(origin);
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
	
	public ArrayList<Container> getActiveContainers() {
		ArrayList<Container> Containers = new ArrayList<Container>();
		for (Journey j : journey) {
			for (Container c : j.getContainerList()) {
				Containers.add(c);
			}
		}
		return Containers;
	}
	
	public ArrayList<Container> getAllContainers() {
		ArrayList<Container> Containers = getActiveContainers();
		Containers.addAll(containerWarehouse);
		return Containers;
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
		notifychartOberver();
	}
	
	public void addData(Container c, int temp, int pressure, int humidity) {
		c.getTempList().add(temp);
		c.getPressureList().add(pressure);
		c.getHumList().add(humidity);
		notifyObservers(c);
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
		ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistoryList = new ArrayList<ArrayList<ArrayList<Integer>>>();
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
	public void addObserver(observer o) {
		obs.add(o);
	}

	private void notifyObservers( Container c) {
		for (observer o: obs) {
			o.update(c.getTempList(),c.getPressureList(),c.getHumList());
		}
	}
	
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
	
//	public ArrayList<Container> findClientContainer(String client, ArrayList<Journey> journeyList){
//		ArrayList<Container> result = new ArrayList<Container>();
//		for ( Journey j : journeyList) {
//			for (Container c : j.getContainerList()) {
//				if (client.contentEquals(c.getCompany())) {
//					result.add(c);
//				}
//			}
//		}
//		return result;
//	}
	
	
	
}
		

	  

