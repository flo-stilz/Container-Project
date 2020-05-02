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
		storeClientCounters();
	}
	
	public void read() {
		readClientFile();
		readClientCounterFile();
	}
	
	public void clear() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./Clients.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(new ArrayList<Client>());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ClientCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(69420);
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		read();
	}
	
	public void storeClientCounters() {
		try {
			FileOutputStream fos = new FileOutputStream(new File("./ClientCounter.xml"));
			XMLEncoder encoder = new XMLEncoder(fos);
			encoder.writeObject(Client.getCount());
			encoder.close();
			fos.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	} 
	
	public void readClientCounterFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./ClientCounter.xml"));
			} catch (FileNotFoundException e) {
				throw new Error(e);
				}
		XMLDecoder decoder = new XMLDecoder(fis);
		Client.setCount(0);
		Client.setCount((Integer)decoder.readObject()); 
		decoder.close();
	}
	

	public void storeClients() {
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
	
	public void readClientFile() {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("./Clients.xml"));
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

//	public void setClients(ArrayList<client> clients) {
//		this.clients = clients;
//	}
}