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
	private ArrayList<client> clients = new ArrayList<client>();
	
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
	
	public ArrayList<client> getClients() {
		return clients;
	}

	public void setClients(ArrayList<client> clients) {
		this.clients = clients;
	}
}
