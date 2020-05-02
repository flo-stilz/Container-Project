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
		storeJourneyCounters();
		storeContainerWarehouse();
		storeContainerCounters();
	}
	public void read() {
		readActiveJourneyFile();
		readEndedJourneyFile();
		readJourneyCounterFile();
		readContainerWarehouseFile();
		readContainerCounterFile();
	}
	
	public void clear() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ActiveJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(new ArrayList<Journey>());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace(); 
		} 
		try {
			FileOutputStream fos = new FileOutputStream(new File("./EndedJourneys.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(new ArrayList<Journey>());
			encoder.close();
			fos.close();
		} catch (IOException ex) { 
			ex.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File("./JourneyCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(0);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ContainerWarehouse.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(new ArrayList<Container>());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ContainerCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(0);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		read();

	}
	
	
	public void storeJourneyCounters() {
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
	
	public void readJourneyCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./JourneyCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
//		Journey.setCounter(0);
		Journey.setCounter((Integer)decoder.readObject()); 
		decoder.close();
	}
	
	
	public void storeContainerCounters() {
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
	
	public void readContainerCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ContainerCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
//		Container.setcCounter(2);
		Container.setcCounter((Integer)decoder.readObject()); 
		decoder.close();
	}
	
	
	
	//needs testing
	public ArrayList<Container> getfilteredContainers( ArrayList<Journey> jList) {

		ArrayList<Container> Containers = new ArrayList<Container>();
		for (Journey j : jList) {
			for (Container c : j.getContainers()) {
				Containers.add(c);
			}
		}
		return Containers;
	}
	
	//needs testing
	public ArrayList<Container> getAllContainers(boolean isPastOrClient, ArrayList<Journey> jList) {
		
		ArrayList<Container> Containers = getfilteredContainers(jList);
		if (isPastOrClient == false) {
			Containers.addAll(containerWarehouse);
		}
		return Containers;
	}
	
//	public void storeActiveJourneys() {
//		storeActiveJourneys(activeJourneys);
//	}
	
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
	
	
//	public void storeEndedJourneys() {
//		storeEndedJourneys(pastJourneys);
//	}
	
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
	
//	public void storeContainerWarehouse() {
//		storeContainerWarehouse(containerWarehouse);
//	}
	
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