package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ClientDatabase {
	private ArrayList<Client> clients = new ArrayList<Client>();
	
	public void storeClients() {
		storeClients(clients);
	}
	
	public void storeClients(ArrayList<Client> clients) {
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
