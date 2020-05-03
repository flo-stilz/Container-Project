package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Simulator {
	
	private ArrayList<String> companies = new ArrayList<String>(Arrays.asList("Lego", "COOPDanmark", "EnergiDanmark", "Pandora", "DanishCrown", "Lundbeck", "Bestseller", "Velux", "JPMorganChase", "AppleInc", "MicrosoftCorp", "NovoNordisk", "Netto", "Lidl", "SallingGroup", "VestasWindSystems", "TDCGroup", "Carlsberg", "Amazon", "Novozymes", "Ford", "Mercedes-Benz", "SamsungElectronics", "IntelCorporation", "DeutcheTelekom", "Coloplast", "DVSPanalpina", "KMD", "ArlaFoods", "Maersk", "Topsoe", "Novozymes", "Nestle", "Volkswagen", "Alibaba", "Google", "Tesla", "Porsche", "UPS", "PostNord", "ASUS", "Dell", "Smirnoff", "JBL", "RedBull")); 
	private ArrayList<String> firstnames = new ArrayList<String>(Arrays.asList("Mads", "Florian", "Martin", "Lucien", "Anna", "John", "Peter", "Sven", "Jorgen", "Brunhilde", "Heath", "Bill", "Phil", "Andrea", "Hubert", "Emilie", "Maria", "Louise", "Mette", "Micheal", "Thomas", "Jesper", "Rasmus", "Christian", "Tina", "Alexander", "Mikkel", "Adam", "Sebastian", "Erik", "Phillip", "Samuel", "Conrad", "Agnes", "Victoria", "Ellen", "Marie", "Asta", "Silja", "Frederik", "Klara", "Ellinor", "Rebecca"));
	private ArrayList<String> surnames = new ArrayList<String>(Arrays.asList("Nielsen", "Jensen", "Hansen", "Petersen", "Andersen", "Christensen", "Sorensen", "Moller", "Poulsen", "Ivanova", "Wang", "Kim", "Saito", "Gruber", "Mueller", "Peeters", "Smith", "Schmit", "Martin", "Garcia", "Silva", "Rossi", "Olsen", "Rasmussen", "Poulsen", "Madsen", "Als", "Beck", "Breum", "Brevig", "Brun-Andersen", "Clausen", "Clemensen", "Dahl", "Jacobsen", "Kampman", "Koldborg", "Lund", "Strandberg"));
	private ArrayList<String> addresses = new ArrayList<String>(Arrays.asList("Anker Engelundsvej 101", "Bill Clinton Street 390", "Kings Street 100", "Queens Street 102", "Strandvej 2", "Hovedgade 1000", "Frederiksundsvej 48", "Alexanderstrasse 19", "Via Appia 12", "Rue Du Moulin", "Hollywood Blvd. 123", "Lombard Street 4", "Chapel Street 796", "First Street 1", "Via De Ventura 12", "El Camino Drive 22", "Enighedsvej 2, 2800 Kongens Lyngby", "Kongebakken 40, 4000 Roskilde", "Eriksenvej 17, Roskilde 4000", 
			"Gydevej 6, 3520 Farum", "Fasanvej 7, 8370 Hadsten", "Stemannsgade 15, 8900 Randers", "Vestervang 8, 8000 Aarhus", "Priorgade 14, 9000 Aalborg", "Vodroffsvej 15, 1900 Frederiksberg", "Fiskedamsgade 28, 2100 Koebenhavn", "Wilder Pl. 13, 1403 Koebenhavn", "Edvard Falcks Gade 5, 1569 Koebenhavn", "Bjergbygade 1, 4200 Slagelse", "Nyvangsvej 3, 4400 Kalundborg", "Odinsvej 19, 5800 Nyborg",
			"Strandgade 25, 7100 Vejle", "Gedstenvej 17, 2770 Kaastrup", "Holmbladsgade 44, 2300 Koebenhavn", "Frimestervej 32, 2400 Koebenhavn", "Hededammen 6, 2730 Herlev", "Harhoffs Alle 5, 4100 Ringsted", "Kirkegade 105, 6700 Esbjerg", "Havrebakken 55, 8200 Aarhus" ));
	private ArrayList<String> contents = new ArrayList<String>(Arrays.asList("bananas", "apples", "cars", "towels", "vendingmachines", "calulators", "Steel", "medicine", "cameras", "computers", "iPads", "oil", "rice", "corn", "magazines"));
	private ArrayList<String> locations = new ArrayList<String>(Arrays.asList("PBG", "CPH", "HAM", "LON", "NEW", "LAX", "BCN", "PEK", "BEN", "BOD", "POA", "RIX", "AAR", "RIO", "EBJ", "SKG", "SFL", "AAL", "SDQ", "SDJ", "SIN", "SPU", "LED", "TNG"));
	private String[][] travelTime = {
									{"Cities","PBG", "CPH", "HAM", "LON", "NEW", "LAX", "BCN", "PEK", "BEN", "BOD", "POA", "RIX", "AAR", "RIO", "EBJ", "SKG", "SFL", "AAL", "SDQ", "SDJ", "SIN", "SPU", "LED", "TNG"},
									{"PBG",    "0",   "2" ,	 "1" ,  "1" , "12" ,  "18",  "3",  "25",   "4",   "1",  "20",   "3",   "2",  "20",   "1",   "4",  "19",  "2",   "14",  "20",  "22",   "4",   "3",   "3"},
									{"CPH",    "2",   "0",   "1",   "1",  "14",   "20",  "5",  "27",   "6",   "3",  "22",   "1",   "1",  "22",   "1",   "6",  "21",  "1",   "16",  "22",  "24",   "6",   "1",   "5"},
									{"HAM",    "1",   "1",   "0",   "1",  "13",   "19",  "4",  "26",   "5",   "2",  "21",   "2",   "1",  "21",   "1",   "5",  "20",  "1",   "15",  "21",  "23",   "5",   "2",   "4"},
									{"LON",    "1",   "1",   "1",   "0",  "12",   "18",  "3",  "25",   "4",   "1",  "20",   "3",   "2",  "20",   "1",   "4",  "19",  "2",   "14",  "20",  "22",   "4",   "3",   "3"},
									{"NEW",    "12",  "14",  "13",  "12", "0",    "6",   "13", "25",   "15",  "12", "6",    "15",  "14", "6",    "13",  "15", "7",   "14",  "5",   "30",  "25",   "14",  "15",  "13"},
									{"LAX",    "18",  "20",  "19",  "18", "6",    "0",   "19", "19",   "21",  "18", "9",    "21",  "20", "9",    "19",  "21", "1",   "20",  "5",   "24",  "19",   "20",  "21",  "19"},
									{"BCN",    "3",   "5",   "4",   "3",  "13",   "19",  "0",  "22",   "1",   "2",  "19",   "6",   "5",  "19",   "4",   "1",  "18",  "5",   "13",  "17",  "19",   "1",   "6",   "1"},
									{"PEK",    "25",  "27",  "26",  "25", "25",   "19",  "22", "0",    "21",  "24", "25",   "28",  "27", "25",   "26",  "21", "20",  "27",  "25",  "10",  "5",    "21",  "28",  "22"},
									{"BEN",    "4",   "6",   "5",   "4",  "15",   "21",  "1",  "21",   "0",   "3",  "23",   "7",   "6",  "23",   "5",   "1",  "20",  "5",   "15",  "17",  "19",   "1",   "7",   "1"},
									{"BOD",    "1",   "3",   "2",   "1",  "12",   "18",  "2",  "24",   "3",   "0",  "20",   "4",   "3",  "20",   "2",   "3",  "19",  "3",   "14",  "19",  "21",   "3",   "4",   "2"},
									{"POA",    "20",  "22",  "21",  "20", "6",    "9",   "19", "25",   "23",  "20", "0",    "23",  "22", "1",    "21",  "23", "10",  "22",  "5",   "29",  "23",   "23",  "23",  "19"},
									{"RIX",    "3",   "1",   "2",   "3",  "15",   "21",  "6",  "28",   "7",   "4",  "23",   "0",   "1",  "23",   "2",   "7",  "22",  "1",   "17",  "23",  "25",   "7",   "1",   "6"},
									{"AAR",    "2",   "1",   "1",   "2",  "14",   "20",  "5",  "27",   "6",   "3",  "22",   "1",   "0",  "22",   "1",   "6",  "21",  "1",   "16",  "22",  "24",   "6",   "1",   "5"},
									{"RIO",    "20",  "22",  "21",  "20", "6",    "9",   "19", "25",   "23",  "20", "1",    "23",  "22", "0",    "21",  "23",  "10", "22",  "5",   "29",  "23",   "23",  "23",  "19"},
									{"EBJ",    "1",   "1",   "1",   "1",  "13",   "19",  "4",  "26",   "5",   "2",  "21",   "2",   "1",  "21",   "0",   "5",   "20", "1",   "15",  "21",  "23",   "5",   "2",   "4"},
									{"SKG",    "4",   "6",   "5",   "4",  "15",   "21",  "1",  "21",   "1",   "3",  "23",   "7",   "6",  "23",   "5",   "0",   "22", "6",   "16",  "23",  "19",   "1",   "7",   "1"},
									{"SFL",    "19",  "21",  "20",  "19", "7",    "1",   "18", "20",   "20",  "19", "10",   "22",  "21", "10",   "20",  "22",  "0",  "21",  "10",  "24",  "20",   "22",  "22",  "19"},
									{"AAL",    "2",   "1",   "1",   "2",  "14",   "20",  "5",  "27",   "5",   "3",  "22",   "1",   "1",  "22",   "1",   "6",   "21", "0",   "16",  "22",  "24",   "6",   "1",   "5"},
									{"SDQ",    "14",  "16",  "15",  "14", "5",    "5",   "13", "25",   "15",  "14", "5",    "17",  "16", "5",    "15",  "16",  "10", "16",  "0",   "29",  "24",   "23",  "23",  "20"},
									{"SDJ",    "20",  "22",  "21",  "20", "30",   "24",  "17", "10",   "17",  "19", "29",   "23",  "22", "29",   "21",  "23",  "24", "22",  "29",  "0",   "5",    "16",  "23",  "18"},
									{"SIN",    "22",  "24",  "23",  "22", "25",   "19",  "19", "5",    "19",  "21", "23",   "25",  "24", "23",   "23",  "19",  "20", "24",  "24",  "5",   "0",    "18",  "25",  "19"},
									{"SPU",    "4",   "6",   "5",   "4",  "14",   "20",  "1",  "21",   "1",   "3",  "23",   "7",   "6",  "23",   "5",   "1",   "22", "6",   "23",  "16",  "18",   "0",   "7",   "6"},
									{"LED",    "3",   "1",   "2",   "3",  "15",   "21",  "6",  "28",   "7",   "4",  "23",   "1",   "1",  "23",   "2",   "7",   "22", "1",   "23",  "23",  "25",   "7",   "0",   "6"},
									{"TNG",    "3",   "5",   "4",   "3",  "13",   "19",  "1",  "22",   "1",   "2",  "19",   "6",   "5",  "19",   "4",   "1",   "19", "5",   "20",  "18",  "19",   "6",   "6",   "0"},
									};
	private Random r = new Random();
	private String company;
	private String content;
	private String origin;
	private String destination;
	private String name;
	private String mail;
	private String address;
	private String password;
	private Client client;
	
	
	public void setSeed(int seed) {
		r.setSeed(seed);
	}

	public String companySelection() {
		int value = r.nextInt(companies.size()-1);
		String company = companies.get(value);
		companies.remove(value);
		return company;
		
	}

	public String nameSelection() {
		int value1 = r.nextInt(firstnames.size()-1);
		int value2 = r.nextInt(surnames.size()-1);
		return firstnames.get(value1) + " " + surnames.get(value2);
	}
	public String emailCreation(String companyName, String name) {
		name = name.replaceAll("\\s+", ".");
			
		return name + "@" + companyName + ".com";
	}
	
	public String addressSelection() {
		int value = r.nextInt(addresses.size()-1);
		String address = addresses.get(value);
		addresses.remove(value);
		return address;
	}	
	
	public Client clientSelection(Application application) {
		int value = r.nextInt(application.getClientDat().getClients().size()-1);
		Client client = application.getClientDat().getClients().get(value);
		return client;
	}

	public String contentSelection() {
		int value = r.nextInt(contents.size()-1);
		String content = contents.get(value);
		return content;
	}

	public String originSelection() {
		int value = r.nextInt(locations.size()-1);
		String origin = locations.get(value);
		return origin;
	}

	public String destinationSelection(String origin) {
		ArrayList<String> possiblelocations = new ArrayList<String>(locations);
		possiblelocations.remove(origin);
		int value = r.nextInt(possiblelocations.size()-1);
		String destination = possiblelocations.get(value);
		return destination;
	}

	public int temperatureInitialization() {
		int value = r.nextInt(45);
		int temp = value;
		return temp;
	}

	public int pressureInitialization() {
		int value = r.nextInt(120);
		int pressure = 930+value;
		return pressure;
	}
	
	public int humidityInitialization() {
		int value = r.nextInt(90);
		int hum = value;
		return hum;
	}
	
	public int temperatureGenerator(Container c) {
		int value = r.nextInt(10);
		int previousdataindex = c.getTempList().size()-1;
		int temp = c.getTempList().get(previousdataindex) + value - 5 ;
		return temp;
	}
	
	public int pressureGenerator(Container c) {
		int value = r.nextInt(100);
		int previousdataindex = c.getPressureList().size()-1;
		int pressure = c.getPressureList().get(previousdataindex) + value - 50 ;
		return pressure;
	}
	
	public int humidityGenerator(Container c) {
		int value = r.nextInt(10);
		int previousdataindex = c.getHumList().size()-1;
		int hum = c.getHumList().get(previousdataindex) + value - 5 ;
		return hum;
	}
	
	public void simulateData(Application application) {
		for (Journey j : application.getJourneyContainerDat().getActiveJourneys()) {
			for (Container c : j.getContainers()) {
				if (c.isEmpty()) {
					int temp = temperatureInitialization();
					int pressure = pressureInitialization();
					int hum = humidityInitialization();
					application.updateData(j, c, temp, pressure, hum);
				}
				else {
					int temp = temperatureGenerator(c);
					int pressure = pressureGenerator(c);
					int hum = humidityGenerator(c);
					application.updateData(j, c, temp, pressure, hum);
				}
			}
		}
	}
	
	public void simulation(Application application, int day) {
		for (int i = 0; i<day; i++) {
			
			
			if (i == 0) {
				for (int k = 0; k<2; k++) {
					clientCreation(application);
				}
			}
			
			if (i%2 == 0) {
				journeyCreation(application);
			}
			
			for (int j = 0; j<5; j++) {				
				simulateData(application);
			}
			
			if (i%10 == 0) {
				clientCreation(application);
			}
			
			updateLocation(application);
		}
	}
	
	public void clientCreation(Application application) {
		company = companySelection();
		address = addressSelection();
		name = nameSelection();
		mail = emailCreation(company, name);
		client = application.createClient(company, address, mail, name, password);
	}
	
	public void journeyCreation(Application application) {
		client = clientSelection(application);
		content = contentSelection();
		origin = originSelection();
		destination = destinationSelection(origin);
		Journey j = application.createJourney(origin, destination, content, client.getCompany());
		int originindex = locations.indexOf(origin) + 1;
		int destinationindex = locations.indexOf(destination) + 1;
		j.setDistance(5 + Integer.parseInt(travelTime[originindex][destinationindex]));
	}
	
	public void updateLocation(Application application) {
		
		for (int h = 0; h < application.getJourneyContainerDat().getActiveJourneys().size(); h++) {
			Journey j = application.getJourneyContainerDat().getActiveJourneys().get(h);
			if (j.getDistance() == 0) {
				application.updateCurrentLocation(j, j.getDestination());
				application.endOfJourney(j);
			}
			else {
				int originindex = locations.indexOf(j.getOrigin()) + 1;
				int destinationindex = locations.indexOf(j.getDestination()) + 1;
				if (Integer.parseInt(travelTime[originindex][destinationindex]) == j.getDistance()) {
					application.updateCurrentLocation(j, "In Transit from " + j.getOrigin() + " to " + j.getDestination());
				}
				j.setDistance(j.getDistance()-1);
			}
		}
	}
	
	
	public ArrayList<String> getCompanies() {
		return companies;
	}
	public ArrayList<String> getAddress() {
		return addresses;
	}

	
	
}