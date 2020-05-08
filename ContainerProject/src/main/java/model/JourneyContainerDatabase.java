package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class JourneyContainerDatabase implements Persistency {
	
	private ArrayList<Journey> activeJourneys = new ArrayList<Journey>();
	private ArrayList<Container> containerWarehouse = new ArrayList<Container>();
	private ArrayList<Journey> pastJourneys = new ArrayList<Journey>();
	
	public void store() {
		storeActiveJourneys();
		storeEndedJourneys();
		storeJourneyCounter();
		storeContainerWarehouse();
		storeContainerCounter();
	}
	public void read() {
		readActiveJourneyFile();
		readEndedJourneyFile();
		readJourneyCounterFile();
		readContainerWarehouseFile();
		readContainerCounterFile();
	}
	
	//Store the most recently generated journey counter
	public void storeJourneyCounter() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./JourneyCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(Journey.getCounter());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	//Read the most recently generated journey counter
	public void readJourneyCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./JourneyCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		Journey.setCounter((Integer)decoder.readObject()); 
		decoder.close();
	}
	
	//Store the most recently entered container counter
	public void storeContainerCounter() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ContainerCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(Container.getcCounter());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	
	//Read the most recently generated container counter
	public void readContainerCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ContainerCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		Container.setcCounter((Integer)decoder.readObject()); 
		decoder.close();
	}
	
	
	
	//finds all containers present in either the past journey list or present journey list. 
	public ArrayList<Container> getFilteredContainers( ArrayList<Journey> jList) {
		ArrayList<Container> Containers = new ArrayList<Container>();
		for (Journey j : jList) {
			for (Container c : j.getContainers()) {
				Containers.add(c);
			}
		}
		return Containers;
	}
	
	/* finds the same containers as getFilteredContainers.In case the boolean
	 * isPastOrClient is false, the containers in the containerwarehouse to the result
	 */
	public ArrayList<Container> getAllContainers(boolean isPastOrClient, ArrayList<Journey> jList) {
		ArrayList<Container> Containers = getFilteredContainers(jList);
		if (isPastOrClient == false) {
			Containers.addAll(containerWarehouse);
		}
		return Containers;
	}
	
	//Store all active journey object along with its attributes.
	public void storeActiveJourneys() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ActiveJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(activeJourneys);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace(); 
		} 
	}
	
	//Reads all active journeys in the database
	public void readActiveJourneyFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ActiveJourneys.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		activeJourneys = (ArrayList<Journey>)decoder.readObject();
		decoder.close();

		}
	
	//Store all ended journey object along with its attributes.
	public void storeEndedJourneys() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./EndedJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(pastJourneys);
			encoder.close();
			fos.close();
		} catch (IOException ex) { 
			ex.printStackTrace();
		}
	}
	
	//Read all ended journeys in the database
	public void readEndedJourneyFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./EndedJourneys.xml"));
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}
		XMLDecoder decoder = new XMLDecoder(fis);
		pastJourneys = (ArrayList<Journey>)decoder.readObject();
		decoder.close();

	}
	
	//Read all containers present in the container warehouse along with its attributes
	public void storeContainerWarehouse() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ContainerWarehouse.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(containerWarehouse);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	//Read all containers in the container warehouse
	public void readContainerWarehouseFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ContainerWarehouse.xml"));
		} catch (FileNotFoundException e) {
			throw new Error(e);
		}
		XMLDecoder decoder = new XMLDecoder(fis);
		containerWarehouse = (ArrayList<Container>)decoder.readObject();
		decoder.close();

	}
	
	public ArrayList<Journey> getActiveJourneys() {
		return activeJourneys;
	}

	public void setActiveJourneys(ArrayList<Journey> activeJourneys) {
		this.activeJourneys = activeJourneys;
	}
	
	public ArrayList<Journey> getPastJourneys() {
		return pastJourneys;
	}

	public void setPastJourneys(ArrayList<Journey> pastJourneys) {
		this.pastJourneys = pastJourneys;
	}

	public ArrayList<Container> getContainerWarehouse() {
		return containerWarehouse;
	}

	public void setContainerWarehouse(ArrayList<Container> containerWarehouse) {
		this.containerWarehouse = containerWarehouse;
	}
}