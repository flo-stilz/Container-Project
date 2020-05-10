package model;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientDatabase implements Persistency {
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public void store() {
		storeClients();
		storeClientCounter();
	}
	
	public void read() {
		readClientFile();
		readClientCounterFile();
	}
	
	
	//Store the most recently generated client counter
	public void storeClientCounter() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("data/ClientCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(Client.getCount());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	
	//Reads the most recently generated client counter
	public void readClientCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("data/ClientCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		Client.setCount(0);
		Client.setCount((Integer)decoder.readObject()); 
		decoder.close();
	}
	
	//Store every client object along with its attributes
	public void storeClients() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("data/Clients.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(clients);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	
	//Read all clients in the database
	public void readClientFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("data/Clients.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		clients = (ArrayList<Client>)decoder.readObject(); 
		decoder.close();
	}
	
	public ArrayList<Client> getClients() {
		return clients;		
	}
}