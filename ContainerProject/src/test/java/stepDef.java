
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Container;
import model.Application;
import model.Client;
import model.ClientDatabase;
import model.Journey;
import model.JourneyContainerDatabase;
import model.Simulator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

public class stepDef {
	
	Application application = new Application();
	
	//Imports for Client Management
	Map<String, Client> clients = new HashMap<String, Client>(); 
	ArrayList<Client> clientMatches = new ArrayList<Client>();
	String companyName;
	String companyName2;
	String address;
	String address2;
	String mail;
	String mail2;
	String name;
	String name2;
	String password;
	String password2;
	
	
	//Imports for Journey management
	Map<String, Journey> journeys = new HashMap<String, Journey>(); 
	ArrayList<Journey> journeyMatches = new ArrayList<Journey>();
	ArrayList<Container> containerMatches = new ArrayList<Container>();
	String origin;
	String origin2;
	String destination;
	String destination2;
	String content;
	String content2;
	String company;
	String company2;
	String search;
	String newloc;
	String loc;
	String keyword;
	
	//Imports for Container Status Tracking
	private Integer temp;
	private Integer pressure;
	private Integer hum;
	private Integer temp1;
	private Integer pressure1;
	private Integer hum1;
	private Integer temp2;
	private Integer pressure2;
	private Integer hum2;
	
	//Imports for Keep Track of the Evolution
	ArrayList<Container> containerJourneyHistoryList = new ArrayList<Container>();
	ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistoryList = new ArrayList<ArrayList<ArrayList<Integer>>>();
	ArrayList<Container> filteredcontainer = new ArrayList<Container>();
	ArrayList<Container> allcontainers = new ArrayList<Container>();
	Container containerStatusHistory;
	Journey journey;
	Client client;
	
	
	//Imports for Persistency Layer
	private ClientDatabase cb = new ClientDatabase();
	private JourneyContainerDatabase jb = new JourneyContainerDatabase();
	ArrayList<Client> clientsCopy;
	ArrayList<Journey> activeJourneysCopy;
	ArrayList<Journey> pastJourneysCopy;
	ArrayList<Container> containersCopy;
	int clientCounterCopy;
	int journeyCounterCopy;
	int containerCounterCopy;
	
	
	
	//Imports for the Simulator
	private Simulator sim = new Simulator();
	private Integer seed;
	private Integer seed2;
	private int days;
	Container c1;
	


	
	
	
	//Client Management
	
	//SCENARIO 1: Register clients
	@Given("a client {string} with company name {string} address {string} email {string} contact person {string} password {string}")
	public void a_client_with_company_name_address_email_contact_person_password(String cid, String company, String address, String email, String name, String password) {
	    this.companyName = company;
	    this.name = name;
	    this.mail = email;
	    this.address = address;
	    this.password = password;
	}
	
	@Given("a second client {string} with company name {string} address {string} email {string} contact person {string} password {string}")
	public void a_second_client_with_company_name_address_email_contact_person_password(String cid, String company, String address, String email, String name, String password) {
	    this.companyName2 = company;
	    this.name2 = name;
	    this.mail2 = email;
	    this.address2 = address;
	    this.password2 = password;
	}
	
	@When("the client {string} is registered in the database")
	public void the_client_is_registered_in_the_database(String cid) {
		clients.put(cid, application.createClient(companyName, address, mail, name, password));
	}
	
	@When("the second client {string} is registered in the database")
	public void the_second_client_is_registered_in_the_database(String cid) {
		clients.put(cid, application.createClient(companyName2, address2, mail2, name2, password2));
	}
	
	@Then("the two clients are present in the database")
	public void the_two_clients_are_present_in_the_database() {
		assertEquals(application.getClientDat().getClients().size(),2); 
		assertEquals(application.getClientDat().getClients().get(0).getCompany(), companyName);
		assertEquals(application.getClientDat().getClients().get(1).getCompany(), companyName2);
	}
	
	
	//SCENARIO 2: Check generated clientids are unique
	@Then("the clients generated clientids are unique")
	public void the_clients_generated_clientids_are_unique() {
		assertNotEquals(application.getClientDat().getClients().get(0).getId(), application.getClientDat().getClients().get(1).getId());
	}
	

	//SCENARIO 3: Register the same client twice
	@Then("only one client is present in the database")
	public void the_client_only_is_present_in_the_database() {
		assertEquals(clients.size(),1);
		assertEquals(application.getClientDat().getClients().get(0).getName(), "Robert Stork");
	}
	
	
	//SCENARIO 4: Update client information
	@When("the client {string} requests their email to be updated to {string}")
	public void the_client_requests_their_email_to_be_updated_to(String cid, String email) {
		clients.get(cid).updateEmail(email); 
	}
	
	@When("the client {string} requests their address to be updated to {string}")
	public void the_client_requests_their_address_to_be_updated_to(String cid, String address) {
		clients.get(cid).updateAddress(address); 
	}
	
	@When("the client {string} requests their contact person to be updated to {string}")
	public void the_client_requests_their_contact_person_to_be_updated_to(String cid, String name) {
		clients.get(cid).updateName(name);
	}
	
	@Then("the information of the client {string} has been updated")
	public void the_information_of_the_client_has_been_updated(String cid) {
		assertEquals(clients.get(cid).getEmail(), "Sats1@administration.com");
		assertEquals(clients.get(cid).getAddress(), "Valby");
		assertEquals(clients.get(cid).getName(), "Filip Hemmingsen");
	}
	     
	
	//SCENARIO 5: Search for a client in a database using a specific email
	@Given("an email {string} to search for")
	public void an_email_to_search_for(String mail) {
		this.mail = mail;
	}
	
	@When("searching for a client in the database using the email")
	public void searching_for_a_client_in_the_database_using_the_email() {
		clientMatches = application.search(mail);
	}
	
	@Then("a list of one client matching the specific email {string} is returned")
	public void a_list_of_one_client_matching_the_specific_email_is_returned(String email) {
		assertEquals(clientMatches.size(),1);
		assertEquals(clientMatches.get(0).getEmail(), email);
	}
	
	
	//SCENARIO 6: Search for a client in a database using a specific address that is no client is listed under
	@Given("an address {string} to search for")
	public void an_address_to_search_for(String address) {
		this.address = address;
	}
	
	@When("searching for client in the database using the address")
	public void searching_for_client_in_the_database_using_the_address() {
	    clientMatches = application.search(address);
	}

	@Then("an empty list of clients matching the keyword is returned")
	public void an_empty_list_of_clients_matching_the_keyword_is_returned() {
	    assertEquals(clientMatches.size(),0); 
	}
	
	
	
	
    //Journey Management
	
	//SCENARIO 1: Create a journey
	@Given("an origin {string} destination {string} content {string} and company {string}")
	public void an_origin_destination_content_and_company(String origin, String destination, String content, String company) {
		this.origin = origin;
		this.destination = destination;
		this.content = content;
		this.company = company;
	}
	
	@When("the journey {string} is registered in the database")
	public void the_journey_is_registered_in_the_database(String jid) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}

	@Then("a unique journey id is created")
	public void a_unique_journey_id_is_created() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getId(), "CPHRIO19");
	}
	
	@Then("the origin and destination for the journey is correct")
	public void the_origin_and_destination_for_the_journey_is_correct() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getOrigin(),"CPH");
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getDestination(),"RIO");
	}
	
	@Then("the content and company for the journey is correct")
	public void the_content_and_company_for_the_journey_is_correct() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getContent(), "bananas");
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getCompany(),"My Protein");
	}
	
	@Then("the journey exists in the database")
	public void the_journey_exists_in_the_database() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	}
	

	//SCENARIO 2: Create multiple journeys
	@Given("a second origin {string} destination {string} content {string} and company {string}")
	public void a_second_origin_destination_content_and_company(String origin, String destination, String content, String company) {
		this.origin2 = origin;
		this.destination2 = destination;
		this.content2 = content;
		this.company2 = company;
	}
	
	@When("the second journey {string} is registered in the database")
	public void the_second_journey_is_registered_in_the_database(String jid) {
		journeys.put(jid, application.createJourney(origin2, destination2, content2, company2));
	}
	

	@Then("the two journeys exists in the database")
	public void the_two_journeys_exists_in_the_database() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 2);
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getOrigin(), origin.toUpperCase());
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(1).getOrigin(), origin2.toUpperCase());
	}
	
	
	//SCENARIO 3: Create multiple similair journeys
	@Then("only a single journey is created")
	public void only_one_journey_is_created() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	}

	@Then("the journey has two containers that exists in the database")
	public void the_journey_has_two_containers_that_exists_in_the_database() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().size(), 2);
	}

	
	//SCENARIO 4: Search for a journey in a database using a specific content that no journey is listed under
	@Given("a journey {string} with origin {string} destination {string} content {string} and company {string}")
	public void a_journey_with_origin_destination_content_and_company(String jid, String origin, String destination, String content, String company) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}
	
	@Given("a keyword {string} describing a journey")
	public void a_keyword_describing_a_journey(String keyword) {
	    this.search = keyword;   
	}
	
	@When("searching through the journeys in the database using the keyword")
	public void searching_through_the_journeys_in_the_database_using_the_keyword() {
	    journeyMatches = application.findUsingLoop(search, application.getJourneyContainerDat().getActiveJourneys());
	}

	@Then("an empty list of journeys in the database matching the keyword is returned")
	public void an_empty_list_of_journeys_in_the_database_matching_the_keyword_is_returned() {
		assertEquals(journeyMatches.size(), 0);
	}
	
	
	//SCENARIO 5: Search for a journey in a database using a specific origin
	@Then("a list of one journey matching the specific origin {string} is returned")
	public void a_list_of_one_journey_matching_the_specific_origin_is_returned(String origin) {
		assertEquals(journeyMatches.get(0).getOrigin(), origin.toUpperCase());
		assertEquals(journeyMatches.size(), 1);
	}

	
	//SCENARIO 6: Search for a journey in a database using a specific destination
	@Then("a list of one journey matching the specific destination {string} is returned")
	public void a_list_of_one_journey_matching_the_specific_destination_is_returned(String destination) {
		assertEquals(journeyMatches.get(0).getDestination(), destination.toUpperCase());
		assertEquals(journeyMatches.size(), 1);
	}
	

	//SCENARIO 7: Find a journeys current location
	@When("the journeys current location is found")
	public void the_journeys_current_location_is_found() {
		loc = application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation();
	}

	@Then("the current location of the journey {string} is returned")
	public void the_current_location_of_the_journey_is_returned(String currentlocation) {
		assertEquals(loc, currentlocation.toUpperCase());
	}
	
	
	// SCENARIO 8: Update a journeys current location
	@Given("a new location {string}")
	public void a_new_location(String newlocation) {
		newloc = newlocation;
	}

	@When("the journeys current location is updated")
	public void the_journeys_current_location_is_updated() {
		application.updateCurrentLocation(application.getJourneyContainerDat().getActiveJourneys().get(0), newloc);
	}

	@Then("the journeys new location is {string}")
	public void the_journeys_new_location_is(String newlocation) {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation(), newlocation.toUpperCase());
	}

	
	//SCENARIO 9: End a journey
    @Given("the journey {string} is completed")
    public void the_journey_is_completed(String jid) {
    	application.updateCurrentLocation(journeys.get(jid), journeys.get(jid).getDestination());
    	application.endOfJourney(journeys.get(jid));
    }
    
    @Then("the journey is removed from the list of active journeys") 
    public void the_journey_is_removed_from_the_list_of_active_journeys() {
    	assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 0); 
    }
    
	@Then("the journey is added to the list of ended journeys")
	public void the_journey_is_added_to_the_list_of_ended_journeys() {
		assertEquals(application.getJourneyContainerDat().getPastJourneys().size(), 1);
		assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getDestination(), "PHE");
	}

	@Then("the journeys assigned containers are stored in the container warehouse")
	public void the_journeys_assigned_containers_are_stored_in_the_container_warehouse() {
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(),1);
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().get(0).getCurrentLocation(), "container warehouse");
	}
	

	//SCENARIO 10: Assign container from container warehouse
	@When("a new journey {string} with origin {string} destination {string} content {string} and company {string}")
	public void a_new_journey_with_origin_destination_content_and_company(String jid, String origin, String destination, String content, String company) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}
	
	@Then("the journeys assigned container should be taken from the container warehouse")
	public void the_journeys_assigned_container_should_be_taken_from_the_container_warehouse() {
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(), 0);
	}
	
	@Then("the same container is used for both journeys")
	public void the_same_container_is_used_for_both_journeys() {
		assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getContainers().get(0).getContainerId(), application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getContainerId());
	}
	
	
	//SCENARIO 11: Search for a container in a database using a specific content	
	@Given("a keyword {string} describing a container")
	public void a_keyword_describing_a_container(String keyword) {
		this.keyword = keyword;	
	}	
	
	@When("searching for the container using the keyword")
	public void searching_for_the_container_using_the_keyword() {
		containerMatches = application.findContainer(keyword, application.getJourneyContainerDat().getActiveJourneys());	
	}

	@Then("a list of containers matching the specific content is returned")
	public void a_list_of_containers_macthing_the_specifc_content_is_returned() {
		assertEquals(containerMatches.size(), 1);
		assertEquals(containerMatches.get(0).getContent(), keyword);
	}

	
	//SCENARIO 12: Search for a container in a database using a specific company
	@Then("a list of containers matching the specific company is returned")
	public void a_list_of_containers_matching_the_specific_company_is_returned() {
		assertEquals(containerMatches.size(), 1);
		assertEquals(containerMatches.get(0).getCompany(), keyword);
	}
	
	
	//SCENARIO 13: Search for a container in a database using a specific location
	@Then("a list of containers matching the specfic location is returned")
	public void a_list_of_containers_matching_the_specfic_location_is_returned() {
		assertEquals(containerMatches.size(), 1);
		assertEquals(containerMatches.get(0).getCurrentLocation(), keyword); 	
	}
	
	
	//SCENARIO 14: Search for container in a database using a keyword that no containers contain
	@Then("an empty list of containers matching the keyword is returned")
	public void an_empty_list_of_containers_matching_the_keyword_is_returned() {
		assertEquals(containerMatches.size(),0); 
	}
	
	
	
	
	//Container Status Tracking
	
	//SCENARIO 1: Add internal-status measurements to a journeys container
	@When("adding temp {int} hum {int} and pressure {int} to the assigned containers of the journey {string}")
	public void adding_temp_hum_and_pressure_to_an_assigned_container_of_the_journey(int temp, int pressure, int humidity, String jid) {
		application.updateData(journeys.get(jid), journeys.get(jid).getContainers().get(0), temp, pressure, humidity);	
	}
	
	@Then("the internal-status measurements are present for the assigned containers of the journey {string}")
	public void the_internal_status_measurements_are_present_for_an_ssigned_container_of_the_journey(String jid) {
		assertEquals(journeys.get(jid).getContainers().get(0).getTempList().size(), 1);
		assertEquals(journeys.get(jid).getContainers().get(0).getPressureList().size(), 1);
		assertEquals(journeys.get(jid).getContainers().get(0).getHumList().size(), 1);
	}
	
	@Then("the internal-status measurements for the journeys container matches the values temp {int} hum {int} pressure {int}")
	public void the_internal_status_measurements_for_the_journeys_container_matches_the_values_temp_hum_pressure(int temp, int pressure, int hum) {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getTempList(), Arrays.asList(temp));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getPressureList(), Arrays.asList(pressure));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getHumList(), Arrays.asList(hum)); 
	}
	
	
	//SCENARIO 2: Add internal-status measurements to a journeys containers
	@Then("both containers contain the measurements temp {int} hum {int} and pressure {int}")
	public void both_container_contain_the_meansurements_temp_hum_pressure(int temp, int pressure, int hum) {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getTempList(),application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(1).getTempList());
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getPressureList(),application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(1).getPressureList());
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getHumList(),application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(1).getHumList());
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getTempList(), Arrays.asList(temp));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getPressureList(), Arrays.asList(pressure));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getHumList(), Arrays.asList(hum));
	}
	
	
	
	//SCENARIO 3: Add multiple internal-status measurements to a journeys container
	@When("readding temp {int} hum {int} and pressure {int} to the assigned containers of the journey {string}")
	public void readding_temp_hum_and_pressure_to_the_assigned_containers_of_the_journey(int temp, int pressure, int humidity, String jid) {
		application.updateData(journeys.get(jid), journeys.get(jid).getContainers().get(0), temp, pressure, humidity);
	}
	
	@Then("both sets of internal-status measurements are present for the assigned containers of the journey {string}")
	public void both_sets_of_internal_status_measurements_are_present_for_an_assigned_containers_of_the_journey(String jid) {
		assertEquals(journeys.get(jid).getContainers().get(0).getTempList().size(), 2);
		assertEquals(journeys.get(jid).getContainers().get(0).getPressureList().size(), 2);
		assertEquals(journeys.get(jid).getContainers().get(0).getHumList().size(), 2);
	} 
	
	@Then("both sets of internal-status measurements for the journeys containers matches the values temp {int} {int} hum {int} {int} pressure {int} {int}")
	public void both_sets_of_internal_status_measurements_for_the_journeys_containers_matches_the_values_temp_hum_pressure(int temp1, int temp2, int pressure1, int pressure2, int hum1, int hum2) {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getTempList(), Arrays.asList(temp1, temp2));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getPressureList(), Arrays.asList(pressure1, pressure2));
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getHumList(), Arrays.asList(hum1, hum2));
	}
	
    
	//Keep Track Of The Evolution
    
	//SCENARIO 1: Track the Journey-History of a container
	@Given("the journey and container counter is set to zero which describes the name of the corresponding ids")
	public void the_journey_and_container_counter_is_set_to_zero_which_describes_the_name_of_the_corresponding_ids() {
		Journey.setCounter(0);
		Container.setcCounter(0);
	}
	
    @Given("a container with id {string} used in the journey {string}")
    public void a_container_with_id_used_in_the_journey(String search, String jid) {
    	this.search = search; 
    }

    @When("the container id is searched for in the journey history")
    public void the_container_id_is_searched_for_in_the_journey_history() {
    	containerJourneyHistoryList = application.findContainer(search, application.getJourneyContainerDat().getPastJourneys());
    }

    @Then("the container history of the container {string} is returned")
    public void the_container_history_of_the_container_is_returned(String containerid) {
    	assertEquals(containerJourneyHistoryList.size(), 1);
    	assertEquals(containerJourneyHistoryList.get(0).getId(), "BUAMIA0"); 	
    }
    
    //SCENARIO 2: Track the Journey-History of a reused container
    @Given("a container with id {string} used in journey {string} and journey {string}")
    public void a_container_with_id_used_in_journey_and_journey(String search, String jid1, String jid2) {
        this.search = search; 
    }
    
    @Then("the journey history of the resued container {string} is returned")
    public void the_journey_history_of_the_resued_container_is_returned(String search) {
    	assertEquals(containerJourneyHistoryList.get(0).getId(), "BUAMIA0");
    	assertEquals(containerJourneyHistoryList.get(1).getId(), "BUAMIA1");
    	assertEquals(containerJourneyHistoryList.size(),2); 
    }
    
    
    //SCENARIO 3: Track the Journey-History of an unused container
    @Given("an unsused container with id {string}")
    public void an_unsused_container_with_id(String search) {
        this.search = search;
    }
    
    @Then("the empty journey history of the unsued container {string} is returned")
    public void the_empty_journey_history_of_the_unsued_container_is_returned(String jid) {
    	assertEquals(containerJourneyHistoryList.size(),0);
    }
    
    
    //SCENARIO 4: Track the internal-status-history of a container
    @Given("internal-status measurements for the containers corresponding to journey {string} are being simulated")
    public void internal_status_measurements_for_the_journey_containers_are_being_simulated(String jid) {
    	sim.setSeed(123);
    	temp = sim.temperatureInitialization();
    	pressure = sim.pressureInitialization();
    	hum = sim.humidityInitialization(); 
    }
    
    @Given("the simulated internal-status measurements are being added to all containers in the journey {string}")
    public void the_simulated_internal_status_measurements_are_being_added_to_all_containers_in_the_journey(String jid) {
    	application.updateData(journeys.get(jid), journeys.get(jid).getContainers().get(0), temp, pressure, hum);
    }
    
    @When("the container id is searched for in the internal-status history")
    public void the_container_id_is_searched_for_in_the_internal_status_history() {
    	containerStatusHistory = application.containerInternalStatusHistory(search, application.getJourneyContainerDat().getPastJourneys());
    	}
    
    @Then("the internal-status history of container {string} is returned")
    public void the_internal_status_history_of_container_is_returned(String search){
    	assertEquals(containerStatusHistory.getTempList().size(), 2);
    	assertEquals(containerStatusHistory.getPressureList().size(), 2);
    	assertEquals(containerStatusHistory.getHumList().size(), 2);
    }
 
    
    // SCENARIO 5: Track the internal-status-history of a reused container
    
    @Given("a new set of internal-status measurements for the containers corresponding to journey {string} are being simulated")
    public void a_new_set_of_internal_status_measurements_for_the_containers_corresponding_to_journey_are_being_simulated(String string) {
    	sim.setSeed(321);
    	temp1 = sim.temperatureInitialization();
    	pressure1 = sim.pressureInitialization();
    	hum1 = sim.humidityInitialization(); 
    }
    
    @Given("the new set of simulated internal-status measurements are being added to all containers in the journey {string}")
    public void the_new_set_of_simulated_internal_status_measurements_are_being_added_to_all_containers_in_the_journey(String jid) {
    	application.updateData(journeys.get(jid), journeys.get(jid).getContainers().get(0), temp1, pressure1, hum1);
    }
    
    @Then("the internal-status history of the reused container {string} is returned")
    public void the_internal_status_history_of_the_reused_container_is_returned(String search) {
    	assertEquals(containerInternalStatusHistoryList.size(),2);
    }
    
    
    //SCENARIO 6: Track the internal-status-history of an unused container
    @When("a container with id {string} not used in the journey {string}")
    public void a_container_with_id_not_used_in_the_journey(String search, String jid) {
    	this.search = search; 
    }
    
    @Then("the empty internal-status history of the unused container {string} is returned")
    public void the_empty_internal_status_history_of_the_unused_container_is_returned(String search) {
        assertEquals(containerInternalStatusHistoryList.size(),0);
    }
    
    
	//SCENARIO 7: Filtering for all active containers
	@When("filtering for all the active containers")
	public void filtering_for_all_the_active_containers() {
		filteredcontainer = application.getJourneyContainerDat().getfilteredContainers(application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@Then("a list of all the active containers is returned")
	public void a_list_of_all_the_active_containers_is_returned() {
		assertEquals(filteredcontainer.size(),2);
		assertEquals(filteredcontainer.get(0).getContainerId(), application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0).getContainerId());
	}
	
	
	//SCENARIO 8: Filtered for all containers in the containerwarehouse
	@When("filtering for all the containers in the containerwarehouse")
	public void filtering_for_all_containers_in_the_containerwarehouse() {
		filteredcontainer = application.getJourneyContainerDat().getfilteredContainers(application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@Then("a list of all containers in the containerwarehouse is returned")
	public void a_list_of_all_containers_in_the_containerwarehouse_is_returned() {
		assertEquals(filteredcontainer.size(),1);
		assertEquals(filteredcontainer.get(0).getContainerId(), "C4");
	}
	
	
	//SCENARIO 9: Retrieving all containers
	@When("all containers have been retrieved")
	public void all_containers_have_been_retrieved(){
		allcontainers = application.getJourneyContainerDat().getAllContainers(false, application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@Then("a list of all containers are returned")
	public void a_list_of_all_containers_are_returned(){
		assertEquals(allcontainers.size(),2);
		assertEquals(allcontainers.get(0).getId(), "CPHBEJ6");
		assertEquals(allcontainers.get(1).getId(), "BDPTOR5");
	}   
	
	
	
	
	//Journey Simulator
	    
	//SCENARIO 1: Pick a Company for the client
	@Given("a seed {int} pointing to a particular instance in the simulator")
	public void a_seed_pointing_to_a_particular_instance_in_the_simulator(int seed) {
	    this.seed = seed;
	}
	
	@When("a specific company out of the {int} available is selected based on the value of the seed")
	public void a_specific_company_out_of_the_is_selected_based_on_the_value_of_the_seed(int numberOfSimulatedCompanies) {
		sim.setSeed(seed);
	    companyName = sim.companySelection();
	}
	
	@Then("the company selected is {string}")
	public void the_company_selected_is(String Netto) {
	    assertEquals(companyName, "MicrosoftCorp");
	}
	
	@Then("the company {string} is removed from the list of companies")
	public void the_company_is_removed_from_the_list_of_companies(String Netto) {
	    assertEquals(sim.getCompanies().size(), 44);
	}
	
	
	//SCENARIO 2: Pick a name and email for the client
	@When("a specific name and email is selected based on the value of the seed")
	public void a_specific_name_and_email_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    name = sim.nameSelection();
	    mail = sim.emailCreation(companyName, name);
	}
	
	@Then("the name and email {string} and {string} is returned")
	public void the_name_and_email_will_is_returned(String generatedName, String generatedMail) {
		assertEquals(name, generatedName);
		assertEquals(mail, generatedMail);
	}
	
	
	//SCENARIO 3: Pick an address for the client
	@When("a specific address out of the {int} available is selected based on the value of the seed")
	public void a_specific_address_out_of_the_available_is_selected_based_on_the_value_of_the_seed(int numberOfSimulatedAddresses) {
		sim.setSeed(seed);
		assertEquals(sim.getAddress().size(), numberOfSimulatedAddresses);
	    address = sim.addressSelection();
	}

	@Then("that selected address will be {string}")
	public void that_selected_address_will_be(String generatedAddress) {
	    assertEquals(address, generatedAddress);
	}

	@Then("that address will be removed from the list of addresses")
	public void that_address_will_be_removed_from_the_list_of_addresses() {
	    assertEquals(sim.getAddress().size(), 38);
	}
	
	
	//SCENARIO 4: Create a client
	@When("a client {string} is created based on the simulated information")
	public void a_client_is_created_based_on_the_simulated_information(String cid) {
		sim.setSeed(seed);
		company = sim.companySelection();
		address = sim.addressSelection();
		name = sim.nameSelection();
		mail = sim.emailCreation(name, company);
		clients.put(cid, application.createClient(company, address, mail, name, password));
	}

	@Then("the client exists in the database")
	public void the_client_exists_in_the_database() {
	    assertEquals(application.getClientDat().getClients().size(), 1);
	}
	
	
	//SCENARIO 5: Pick a client for creation of a journey
	@Given("a client {string} with seed {int}")
	public void a_client_with_seed(String cid,Integer seed) {
		sim.setSeed(seed);
		company = sim.companySelection();
		address = sim.addressSelection(); 
		name = sim.nameSelection();
		mail = sim.emailCreation(name, company);
		clients.put(cid, application.createClient(company, address, mail, name, password));
	}

	@When("a client is choosen with seed {int}")
	public void a_client_is_choosen_with_seed(int seed) {
		sim.setSeed(seed);
	    client = sim.clientSelection(application);
	}

	@Then("that clientId will be {int}")
	public void that_clientId_will_be(int clientid) {
	    assertEquals(client.getId(), clientid);
	}
	
	
	//SCENARIO 6: Pick content for creation of a journey
	@When("a specific content is selected based on the value of the seed")
	public void a_specific_content_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
		content = sim.contentSelection();
	}

	@Then("the content {string} is returned")
	public void the_content_is_returned(String generatedContent) {
	    assertEquals(content, generatedContent);
	}
	

	//SCENARIO 7: Pick origin for creation of a journey
	@When("a specific origin is selected based on the value of the seed")
	public void a_specific_origin_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    origin = sim.originSelection();
	}

	@Then("the origin {string} is returned")
	public void the_origin_is_returned(String generatedOrigin) {
	    assertEquals(origin, generatedOrigin);
	}
	
	
	//SCENARIO 8: Pick destination for creation of a journey
	@When("a specific destination is selected based on the value of the seed")
	public void a_specific_destination_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    destination = sim.destinationSelection(origin);
	}

	@Then("the destination {string} is returned")
	public void the_destination_is_returned(String generatedDestination) {
	    assertEquals(destination, generatedDestination);
	}

	
	//SCENARIO 9: Create a journey
	@When("a journey {string} is created")
	public void a_journey_is_created(String jid) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}
	
	
	// SCENARIO 10: Initialize data for a Journey
	@When("the internal-status measurements are generated for the containers in the journey {string}")
	public void the_internal_status_measurements_are_generated_for_the_containers_in_the_journey(String jid) {
		sim.setSeed(seed);
		temp = sim.temperatureInitialization();
	    pressure = sim.pressureInitialization();
	    hum = sim.humidityInitialization();
	}

	@When("the data is added to the containers in the journey {string}")
	public void the_data_is_added_to_the_containers_in_the_journey(String jid) {
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0);
	    application.updateData(application.getJourneyContainerDat().getActiveJourneys().get(0), c1, temp, pressure, hum);
	}

	@Then("the data exists in the container for the journey {string}")
	public void the_data_will_exists_in_the_container_for_the_journey(String jid) {
	    assertEquals(c1.getTempList().size(), 1);
	    assertEquals(c1.getPressureList().size(), 1); 
	    assertEquals(c1.getHumList().size(), 1);
	    assertEquals(c1.getTempList().get(0).toString(), "17");
	    assertEquals(c1.getPressureList().get(0).toString(), "980");
	    assertEquals(c1.getHumList().get(0).toString(), "56");
	}

	@Then("the data has the specific values")
	public void the_data_has_the_specific_values() {
	    assertEquals(c1.getTempList().get(0).toString(), "17");
	    assertEquals(c1.getPressureList().get(0).toString(), "980");
	    assertEquals(c1.getHumList().get(0).toString(), "56");
	}
	
	
	//SCENARIO 11: Generate data based previously entered data in a Container
	@When("the internal-status measurements are generated based on the previously generated internal-status measurements")
	public void the_internal_status_measurements_are_generated_based_on_the_previously_generated_internal_status_measurements() {
		sim.setSeed(seed);
		temp = sim.temperatureGenerator(c1);
	    pressure = sim.pressureGenerator(c1);
	    hum = sim.humidityGenerator(c1);
	    application.updateData(application.getJourneyContainerDat().getActiveJourneys().get(0), c1, temp, pressure, hum);
	}
	
	@Then("the internal-status measurements are added to all the containers in the journey")
	public void the_internal_status_measurements_are_added_to_all_the_containers_in_the_journey() {
		assertEquals(c1.getTempList().size(), 2);
	    assertEquals(c1.getPressureList().size(), 2);
	    assertEquals(c1.getHumList().size(), 2);
	}
	
	
	//SCENARIO 12: Simulate internal-status measurements thrice
	@When("a simulated journey {string} is created")
	public void a_simulated_journey_is_created(String jid) {
		sim.setSeed(seed);
		sim.journeyCreation(application);
		journeys.put(jid, application.getJourneyContainerDat().getActiveJourneys().get(0));
	}
	
	@When("internal-status measurements are simulated and updated thrice with the following seeds {int} {int} {int}")
	public void internal_status_measurements_are_simulated_and_updated_thrice_with_the_following_seeds(Integer seed1, Integer seed2, Integer seed3) {
		sim.setSeed(seed1);
		sim.simulateData(application);
		sim.setSeed(seed2);
	    sim.simulateData(application);
		sim.setSeed(seed3);
	    sim.simulateData(application);
	}

	@Then("three internal-status measurements of each type will be added to all the containers in the journey")
	public void three_internal_status_measurements_of_each_type_will_be_added_to_all_the_containers_in_the_journey() {
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0);
	    assertEquals(c1.getTempList().size(),3);
	}

	@Then("the three sets of internal-status measurements are returned")
	public void the_three_sets_of_internal_status_measurements_are_returned() {
	    assertEquals(c1.getTempList(), new ArrayList<Integer>(Arrays.asList(17,20,18)));
	    assertEquals(c1.getPressureList(), new ArrayList<Integer>(Arrays.asList(980,946,898)));
	    assertEquals(c1.getHumList(), new ArrayList<Integer>(Arrays.asList(56,51,47)));
	}

	
	//SCENARIO 13: Update the location of a Journey from origin to transition state
	@Given("a simulated client {string} is created with seed {int}")
	public void a_simulated_client_is_created_with_seed(String cid, Integer seed) {
		sim.setSeed(seed);
	    sim.clientCreation(application);
	}
	
	@Given("a number of days {int}")
	public void a_number_of_days(Integer days) {
	    this.days = days;
	}

	@When("the number of days pass by and the location gets updated")
	public void the_number_of_days_by_and_the_location_gets_updated() {
	    for (int i = 0; i<days; i++) {
	    	sim.updateLocation(application);
	    }
	}

	@Then("the current location is in transition between the origin and destination")
	public void the_current_location_is_in_transition_between_the_origin_and_destination() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation(), "IN TRANSIT FROM EBJ TO BOD");
	}
	
	//SCENARIO 14: Update the location of a Journey from transition state to destination
	@Then("the current location is the destination")
	public void the_current_location_is_the_destination() {
		assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getCurrentLocation(), application.getJourneyContainerDat().getPastJourneys().get(0).getDestination());
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 0);
		assertEquals(application.getJourneyContainerDat().getPastJourneys().size(), 1);
	}
	

	//SCENARIO 15: Simulation of the program for 20 days
	@When("simulation is running")
	public void simulation_is_running() {
	    sim.simulation(application, days);
	}

	@Then("the amount of clients in the system should be {int}")
	public void the_amount_of_clients_in_the_system_should_be(Integer int1) {
	    assertEquals(application.getClientDat().getClients().size(), 4);
	}

	@Then("the sum of the containers in all the active journeys and in the past journeys is {int}")
	public void the_sum_of_the_containers_in_all_the_active_journeys_and_in_the_past_journeys_is(Integer int1) {
		int sum = 0;
	    for (Journey j : application.getJourneyContainerDat().getActiveJourneys()) {
	    	sum = sum + j.getContainers().size();
	    }
	    for (Journey j : application.getJourneyContainerDat().getPastJourneys()) {
	    	sum = sum + j.getContainers().size();
	    }
	    assertEquals(sum, 10);
	}
	

    

    //Persistency Layer
	
    //SCENARIO 1: Store two clients
    @Given("copying the current list of clients")
    public void copying_the_current_list_of_clients() {
    	clientsCopy = new ArrayList<Client>(application.getClientDat().getClients()); 
    }
    
    @When("storing the current list of clients")
    public void storing_the_current_list_of_clients() {
    	application.getClientDat().storeClients();
    }
    
 
    @When("reading the generated client file")
    public void reading_the_generated_client_file() {
    	cb.readClientFile();
    }

    @Then("the client file matches the copied list of clients")
    public void the_client_file_matches_the_copied_list_of_clients() {
    	assertEquals(application.getClientDat().getClients().size(), clientsCopy.size());
    	assertEquals(application.getClientDat().getClients().get(0).getEmail(), clientsCopy.get(0).getEmail());
    }
    
    
	//SCENARIO 2: Restore a client when client information is updated
	@When("update and store the client {string} name to {string}")
	public void update_the_client_name_to(String cid, String name) {
		clients.get(cid).updateName(name);
		application.getClientDat().storeClients();
	}

    @Then("the client file is the same as the copied list of clients") 
    public void the_client_file_is_the_same_as_the_copied_list_of_clients() {
    	assertEquals(application.getClientDat().getClients().get(0).getName(), clientsCopy.get(0).getName());
    	assertEquals(application.getClientDat().getClients().size(), clientsCopy.size());
    }
    

    //SCENARIO 3: Store active journey 	
	@Given("a second seed {int} pointing to a particular instance in the simulator")
	public void a_second_seed_pointing_to_a_particular_instance_in_the_simulator(Integer seed) {
		this.seed2 = seed;
	}
    
	@Given("the second set of internal-status measurements are generated for the containers in the journey {string}")
	public void the_second_set_of_internal_status_measurements_are_generated_for_the_containers_in_the_journey(String jid) {
		sim.setSeed(seed2);
		hum2 = sim.humidityInitialization(); 
		pressure2 = sim.pressureInitialization(); 
		temp2 = sim.temperatureInitialization(); 
	}
	
	@Given("the second set of data is added to the containers in the journey {string}")
	public void the_second_set_of_data_is_added_to_the_containers_in_the_journey(String jid) {
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainers().get(0);
	    application.updateData(application.getJourneyContainerDat().getActiveJourneys().get(0), c1, temp2, pressure2, hum2);
	}
	
	@Given("copying the current list of active journeys")
	public void copying_the_current_list_of_active_journeys() {
    	activeJourneysCopy = new ArrayList<Journey>(application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@When("storing the current list of active journeys")
	public void storing_the_current_list_of_active_journeys() {
		application.getJourneyContainerDat().readActiveJourneyFile();
	}
    
	@When("reading the generated activejourney file")
	public void reading_the_generated_activejourneyFile() {
		jb.readActiveJourneyFile();
	}

	@Then("the activejourney file matches the copied list of active journeys")
	public void the_activejourney_file_matches_the_copied_list_of_active_journeys() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), activeJourneysCopy.size());
		assertEquals(hum, c1.getHumList().get(0));
		assertEquals(pressure, c1.getPressureList().get(0));
		assertEquals(temp, c1.getTempList().get(0));
		assertEquals(hum2, c1.getHumList().get(1));
		assertEquals(pressure2, c1.getPressureList().get(1));
		assertEquals(temp2, c1.getTempList().get(1));
	}

	@Then("the data has specific values")
	public void the_data_has_specific_values() {
		assertNotEquals(temp, temp2);
		assertNotEquals(pressure, pressure2);
		assertNotEquals(hum, hum2);
	}
	
    
    //SCENARIO 4: Store a past journey and store the changes in the container warehouse
	@Given("copying the current list of past journeys and the containers in the container warehouse")
	public void copying_the_current_list_of_past_journeys_and_the_containers_in_the_container_warehouse() {
		pastJourneysCopy = new ArrayList<Journey>(application.getJourneyContainerDat().getPastJourneys());
		containersCopy = new ArrayList<Container>(application.getJourneyContainerDat().getContainerWarehouse());
	}
	
	@When("storing the current list of past journey and the containers in the container warehouse")
	public void storing_the_current_list_of_past_journey_and_the_containers_in_the_container_warehouse() {
		application.getJourneyContainerDat().storeEndedJourneys();
		application.getJourneyContainerDat().storeContainerWarehouse();
	}

	@When("reading the generated pastjourney file and the containwarehouse file")
	public void reading_the_generated_pastjourney_file_and_the_containwarehouse_file() {
		jb.readEndedJourneyFile();
		jb.readContainerWarehouseFile();
	}

	@Then("the pastjourney file matches the copied list of past journeys")
	public void the_pastjourney_file_matches_the_copied_list_of_past_journeys() {
    	assertEquals(application.getJourneyContainerDat().getPastJourneys().size(), pastJourneysCopy.size());
    	assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getId(), pastJourneysCopy.get(0).getId());
	}
	
	@Then("the containerwarehouse file matches the copied list of containers in the container warehouse")
	public void the_containerwarehouse_file_matches_the_copied_list_of_containers_in_the_container_warehouse() {
    	assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(), containersCopy.size());
    	assertEquals(application.getJourneyContainerDat().getContainerWarehouse().get(0).getId(), containersCopy.get(0).getId());
	}
	
	//SCENARIO 5: Store a past journey that had multiple containers and store the changes in the container warehouse
	@Then("the pastjourney file is the same as the copied list of past journeys")
	public void the_pastjourney_file_is_the_same_as_the_copied_list_of_past_journeys() {
    	assertEquals(application.getJourneyContainerDat().getPastJourneys().size(), pastJourneysCopy.size());
    	assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getId(), pastJourneysCopy.get(0).getId());
	}
	
	@Then("the containerwarehouse file is the same as the copied list of containers in the container warehouse")
	public void the_containerwarehouse_file_is_the_same_as_the_copied_list_of_containers_in_the_container_warehouse() {
    	assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(), containersCopy.size()); 
    	assertEquals(application.getJourneyContainerDat().getContainerWarehouse().get(0).getId(), containersCopy.get(0).getId());
	}

	
	//SCENARIO 6: Store the most recent client counter
	@Given("copying the most recent client counter")
	public void copying_the_most_recent_client_counter() {
		clientCounterCopy = Client.getCount();
	}
	
	@When("storing the most recent client counter")
	public void storing_the_most_recent_client_counter(){
		application.getClientDat().storeClientCounters();
	}
	
	@When("reading the generated client counter file")
	public void reading_the_enerated_client_counter_file() {
		Client.setCount(0);
		application.getClientDat().readClientCounterFile();
	}

	@Then("the most recent client counter is")
	public void the_most_recent_client_counter_is() {
		assertEquals(Client.getCount(), clientCounterCopy);
	}
	
	
	//SCENARIO 7: Store the most recent journey counter
	
	@Given("copying the most recent journey counter")
	public void copying_the_most_recent_journey_counter() {
		journeyCounterCopy = Journey.getCounter();
	}
	
	@When("storing the most recent journey counter")
	public void storing_the_most_recent_journey_counter(){
		application.getJourneyContainerDat().storeJourneyCounters();
	}
	
	@When("reading the generated journey counter file")
	public void reading_the_generated_journey_counter_file() {
		Journey.setCounter(0);
		application.getJourneyContainerDat().readJourneyCounterFile();
	}

	@Then("the most recent journey counter is")
	public void the_most_recent_journey_counter_is() {
		assertEquals(Journey.getCounter(), journeyCounterCopy);
	}
	
	
	//SCENARIO 8: Store the most recent container counter
	@Given("copying the most recent container counter")
	public void copying_the_most_recent_container_counter() {
		containerCounterCopy = Container.getcCounter();
	}
	
	@When("storing the most recent container counter")
	public void storing_the_most_recent_container_counter(){
		application.getJourneyContainerDat().storeContainerCounters();
	}
	
	@When("reading the generated container counter file")
	public void reading_the_generated_container_counter_file() {
		Container.setcCounter(0);
		application.getJourneyContainerDat().readContainerCounterFile();
	}

	@Then("the most recent container counter is")
	public void the_most_recent_container_counter_is() {
		assertEquals(Container.getcCounter(), containerCounterCopy);
	}
}