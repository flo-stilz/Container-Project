import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Container;
import model.Application;
import model.Journey;
import model.Simulator;
import model.client;

public class stepDef {
	
	Application application = new Application();
	
	//Imports for Client Management
	Map<String, client> clients = new HashMap<String, client>(); 
	ArrayList<client> results = new ArrayList<client>();
	client client1;
	client client2;
	String companyName;
	String name;
	String mail;
	String address;
	String companyName2;
	String name2;
	String mail2;
	String address2;
	client client; 
	String password;
	String password2;
	
	
	//Imports for Journey management
	Map<String, Journey> journeys = new HashMap<String, Journey>(); 
	ArrayList<Journey> matches = new ArrayList<Journey>();
	String search;
	String newloc;
	String loc;
	String destination;
	String content;
	String company;
	String origin;
	String destination2;
	String content2;
	String company2;
	String origin2;
	
	
	
	Container c1;
	Container c2;
	private Integer temp1;
	private Integer pressure1;
	private Integer hum1;
	private Integer temp;
	private Integer pressure;
	private Integer hum;


	
	//Simulator variables
	private Simulator sim = new Simulator();
	private int seed;
	private int days;
	
	String keyword;
	
	
	
	//Imports for Container Status Tracking
	ArrayList<Container> containerJourneyHistoryList = new ArrayList<Container>();
	ArrayList<ArrayList<ArrayList<Integer>>> containerInternalStatusHistoryList = new ArrayList<ArrayList<ArrayList<Integer>>>();
	
	Journey journey;
	private Integer seed2;
	
	
	private Integer temp2;
	private Integer pressure2;
	private Integer hum2;
	
	
	ArrayList<Container> res = new ArrayList<Container>();
	ArrayList<Container> filteredcontainer = new ArrayList<Container>();
	ArrayList<Container> allcontainers = new ArrayList<Container>();
	
	
	
	
	
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
	
	@When("the client {string} is registered in the client database")
	public void the_client_is_registered_in_the_client_database(String cid) {
		clients.put(cid, application.createClient(companyName, address, mail, name, password));
	}
	
	@When("the second client {string} is registered in the client database")
	public void the_second_client_is_registered_in_the_client_database(String cid) {
		clients.put(cid, application.createClient(companyName2, address2, mail2, name2, password2));
	}
	
	@Then("the clients are present in the client database")
	public void the_clients_are_present_in_the_client_database() {
		assertEquals(application.getClientDat().getClients().size(),2); 
		assertEquals(application.getClientDat().getClients().get(0).getCompany(), companyName);
		assertEquals(application.getClientDat().getClients().get(1).getCompany(), companyName2);
	}
	
	
	//SCENARIO 2: Checking generated clientid
	@Then("The generated clientids are unique")
	public void the_generated_clientids_are_unique() {
		assertNotEquals(application.getClientDat().getClients().get(0).getId(), application.getClientDat().getClients().get(1).getId());
	}
	

	//SCENARIO 3: Register the same client twice
	@Then("only one client is present in the client database")
	public void the_client_only_is_present_in_the_client_database() {
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
	     
	
	//SCENARIO 5: Search for a client with a specific email email
	@When("searching for client in client database using email {string}")
	public void searching_for_client_in_client_database_using_email(String mail) {
		results = application.search(mail);
	}
	
	@Then("the corresponding client is returned")
	public void the_corresponding_client_is_returned() {
		assertEquals(results.size(),1);
	}
	
	
	//SCENARIO 6: Search for a client that is not present in the clientlist using a specific address
	@When("searching for client in client database using address {string}")
	public void searching_for_client_in_client_database_using_address(String address) {
	    results = application.search(address);
	}

	@Then("an empty list of clients in the client database matching the keyword is returned")
	public void an_empty_list_clients_in_the_client_database_matching_the_keyword_is_returned() {
	    assertEquals(results.size(),0); 
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
	
	@When("the journey {string} is registered")
	public void the_journey_is_registered(String jid) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}

	@Then("a unique journey id is created")
	public void a_unique_journey_id_is_created() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getId(), "CPHRIO26");
	}
	
	@Then("the origin and destination for the journey is correct")
	public void the_origin_and_destination_for_the_journey_is_correct() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getOrigin(),"CPH");
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getDestination(),"RIO");
	}
	
	@Then("the content and company for the journey is correct")
	public void the_content_and_company_for_the_journey_is_correct() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0).getContent(), "bananas");
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0).getCompany(),"gls");
	}
	
	@Then("the journey is in the database")
	public void the_journey_is_in_the_database() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	}
	

	//SCENARIO 2: Creating multiple journeys
	@Given("a second origin {string} destination {string} content {string} and company {string}")
	public void a_second_origin_destination_content_and_company(String origin, String destination, String content, String company) {
		this.origin2 = origin;
		this.destination2 = destination;
		this.content2 = content;
		this.company2 = company;
	}
	
	@When("the second journey {string} is registered")
	public void the_second_journey_is_registered(String jid) {
		journeys.put(jid, application.createJourney(origin2, destination2, content2, company2));
	}
	

	@Then("journeys exist on database")
	public void journeys_exist_on_database() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 2);
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getOrigin(), origin.toUpperCase());
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(1).getOrigin(), origin2.toUpperCase());
	}
	
	
	//SCENARIO 3: Creating multiple similair journeys
	@Then("only one journey is created")
	public void only_one_journey_is_created() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	}

	@Then("the journey has two containers")
	public void the_journey_has_two_containers() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().size(), 2);
	}

	
	//SCENARIO 4: Search for a journey that is not present in the journeylist using a specific address
	@Given("a journey {string} with origin {string} destination {string} content {string} and company {string}")
	public void a_journey_with_origin_destination_content_and_company(String jid, String origin, String destination, String content, String company) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}
	
	@Given("a keyword {string}")
	public void a_keyword(String keyword) {
	    this.search = keyword;   
	}
	
	@When("searching through the journey database using the keyword")
	public void searching_through_the_journey_database_using_the_keyword() {
	    matches = application.findUsingLoop(search, application.getJourneyContainerDat().getActiveJourneys());
	}

	@Then("an empty list of journeys in the journey database matching the keyword is returned")
	public void an_empty_list_of_journeys_in_the_journey_database_matching_the_keyword_is_returned() {
		assertEquals(matches.size(), 0);
	}

	
	//SCENARIO 5: Search for a journey in the journeylist using a specific origin
	@Then("the corresponding journey matching the origin-keyword is returned")
	public void the_corresponding_journey_matching_the_origin_keyword_is_returned() {
		assertEquals(matches.get(0).getOrigin(), "MUM");
		assertEquals(matches.size(), 1);
	}

	
	//SCENARIO 6: Search for a journey in the journeylist using a specific destination
	@Then("the corresponding journey matching the destination-keyword is returned")
	public void the_corresponding_journey_matching_the_destination_keyword_is_returned() {
		assertEquals(matches.get(0).getDestination(), "TOU");
		assertEquals(matches.size(), 1);
	}


	//SCENARIO 7: Update a journeys current location
	@When("the journeys current location is found")
	public void the_journeys_current_location_is_found() {
		loc = application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation();
	}

	@Then("the current location of the journey is returned to the custumor")
	public void the_current_location_of_the_journey_is_returned_to_the_custumor() {
		assertEquals(loc, "MUM");
	}
	
	
	// SCENARIO 8: Update current location of a journey
	@Given("a new location {string}")
	public void a_new_location(String newlocation) {
		newloc = newlocation;
	}

	@When("the journeys current location is updated")
	public void the_journeys_current_location_is_updated() {
		application.updateCurrentLocation(application.getJourneyContainerDat().getActiveJourneys().get(0), newloc);
	}

	@Then("the journeys current location is updated in the journey database")
	public void the_journeys_current_location_is_updated_in_the_journey_database() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation(), "PHE");
	}

	
	//SCENARIO 9: Ending a journey
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

	@Then("the containers corresponding to the journey are stored in the warehouse")
	public void the_containers_corresponding_to_the_journey_are_stored_in_the_warehouse() {
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(),1);
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().get(0).getCurrentLocation(), "container warehouse");
	}
	

	//SCENARIO 10: Assigning container from containerwarehouse
	@Then("the container should be taken from containerwarehouse")
	public void the_container_should_be_taken_from_containerwarehouse() {
		assertEquals(application.getJourneyContainerDat().getContainerWarehouse().size(), 0);
	}
	
	@Then("the container for the old journey is reused for the new journey")
	public void the_container_for_the_old_journey_is_reused_for_the_new_journey() {
		assertEquals(application.getJourneyContainerDat().getPastJourneys().get(0).getContainerList().get(0).getContainerId(), "C0");
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0).getContainerId(), "C0");
	}
	
	
	//SCENARIO 11: Searching for container using a keyword of type content
	@When("searching for the container containing the content {string}")
	public void searching_for_the_container_containing_the_content(String keyword) {
		this.keyword = keyword;
		res = application.findContainer(keyword, application.getJourneyContainerDat().getActiveJourneys());	
	}

	@Then("a list of containers possessing this content is returned")
	public void a_list_of_containers_possessing_this_keyword_is_returned() {
		assertEquals(res.size(), 1);
		assertEquals(res.get(0).getContent(), keyword);
	}

	//SCENARIO 12: Searching for container using a keyword of type company
	@When("searching for the container containing the company {string}")
	public void searching_for_the_container_containing_the_company(String keyword) {
		this.keyword = keyword;
		res = application.findContainer(keyword, application.getJourneyContainerDat().getActiveJourneys());	
	}

	@Then("a list of containers possessing this company is returned")
	public void a_list_of_containers_possessing_this_company_is_returned() {
		assertEquals(res.size(), 1);
		assertEquals(res.get(0).getCompany(), keyword);
	}
	
	//SCENARIO 13: Searching for container using a keyword of type currentlocation
	@When("searching for the container containing the currentlocation {string}")
	public void searching_for_the_container_containing_the_currentlocation(String keyword) {
		this.keyword = keyword;
		res = application.findContainer(keyword, application.getJourneyContainerDat().getActiveJourneys());	
	}

	@Then("a list of containers possessing this currentlocation is returned")
	public void a_list_of_containers_possessing_this_currentlocation_is_returned() {
		assertEquals(res.size(), 1);
		assertEquals(res.get(0).getCurrentLocation(), keyword);
	}
	
	//SCENARIO 14: Searching for container using a keyword that no containers contain
	@Then("an empty list of containers will be returned")
	public void an_empty_list_of_containers_will_be_returned() {
		assertEquals(res.size(),0); 
	}
	
	
	//SCENARIO 15: Filtering for all active containers
	@When("filtering for all the active containers")
	public void filtering_for_all_the_active_containers() {
		filteredcontainer = application.getJourneyContainerDat().getfilteredContainers(application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@Then("a list of all the active containers is returned")
	public void a_list_of_all_the_active_containers_is_returned() {
		assertEquals(filteredcontainer.size(),2);
		assertEquals(filteredcontainer.get(0).getContainerId(), application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0).getContainerId());
	}
	
	
	//SCENARIO 16: Filtered for all containers in the containerwarehouse
	@When("filtering for all the containers in the containerwarehouse")
	public void filtering_for_all_containers_in_the_containerwarehouse() {
		filteredcontainer = application.getJourneyContainerDat().getfilteredContainers(application.getJourneyContainerDat().getActiveJourneys());
	}
	
	@Then("a list of all containers in the containerwarehouse is returned")
	public void a_list_of_all_containers_in_the_containerwarehouse_is_returned() {
		assertEquals(filteredcontainer.size(),1);
		assertEquals(filteredcontainer.get(0).getContainerId(), "C4");
	}
	
	
	//SCENARIO 17: Retrieving all containers
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
	
	
	
	//Container Status Tracking
	
	//SCENARIO 1: Addition of internal-status measurements
	@When("adding temp {int} hum {int} and pressure {int} to an assigned container of the journey {string}")
	public void adding_temp_hum_and_pressure_to_an_assigned_container_of_the_journey(int temp, int pressure, int humidity, String jid) {
		application.addData(journeys.get(jid).getContainerList().get(0), temp, pressure, humidity);	
	}
	
	@Then("the internal-status measurements are present for an assigned container of the journey {string}")
	public void the_internal_status_measurements_are_present_for_an_ssigned_container_of_the_journey(String jid) {
		assertEquals(journeys.get(jid).getContainerList().get(0).getTempList().size(), 1);
		assertEquals(journeys.get(jid).getContainerList().get(0).getPressureList().size(), 1);
		assertEquals(journeys.get(jid).getContainerList().get(0).getHumList().size(), 1);
	}
	
	
	//SCENARIO 2: Readdition of internal-status measurements
	@When("reading temp {int} hum {int} and pressure {int} to an assigned container of the journey {string}")
	public void readding_temp_hum_and_pressure_to_an_assigned_container_of_the_journey(int temp, int pressure, int humidity, String jid) {
		application.addData(journeys.get(jid).getContainerList().get(0), temp, pressure, humidity);	
	}
	
	@Then("both sets of internal-status measurements are present for an assigned container of the journey {string}")
	public void both_sets_of_internal_status_measurements_are_present_for_an_assigned_container_of_the_journey(String jid) {
		assertEquals(journeys.get(jid).getContainerList().get(0).getTempList().size(), 2);
		assertEquals(journeys.get(jid).getContainerList().get(0).getPressureList().size(), 2);
		assertEquals(journeys.get(jid).getContainerList().get(0).getHumList().size(), 2);
	} 
	
	
	//SCENARIO 3: Update of internal-status measurements
	@When("updating temp {int} hum {int} pressure {int} for {string}")
	public void updating_temp_hum_pressure_for(int temp, int pressure, int humidity, String jid) {
	    application.updateData(journeys.get(jid), journeys.get(jid).getContainerList().get(0), temp, pressure, humidity);
	}
	
	@Then("measurements are present for container of {string}")
	public void measurements_are_present_for_container_of(String jid) {
		assertEquals(journeys.get(jid).getContainerList().get(0).getTempList().size(), 1);
		assertEquals(journeys.get(jid).getContainerList().get(0).getPressureList().size(), 1);
		assertEquals(journeys.get(jid).getContainerList().get(0).getHumList().size(), 1);	
	}
	
	
    
	    
	//Journey Simulator
	    
	//SCENARIO 1: Picking a Company for the client
	@Given("a seed {int} pointing to a particular instance in the simulator")
	public void a_seed_pointing_to_a_particular_instance_in_the_simulator(int seed) {
	    this.seed = seed;
	}
	
	@When("a specific company out of the {int} available is selected based on the value of the seed")
	public void a_specific_company_out_of_the_is_selected_based_on_the_value_of_the_seed(int numberOfSimulatedCompanies) {
		sim.setSeed(seed);
		//??
//		assertEquals(sim.getCompanies().size(),numberOfSimulatedCompanies);
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
	
	
	//SCENARIO 2: Picking a name and email for the client
	@When("a specific name and email is selected based on the value of the seed")
	public void a_specific_name_and_email_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    name = sim.nameSelection();
	    mail = sim.emailCreation(companyName, name);
	}
	
	@Then("that name and email will be {string} and {string}")
	public void that_name_and_email_will_be_and(String generatedName, String generatedMail) {
		assertEquals(name, generatedName);
		assertEquals(mail, generatedMail);
	}
	
	
	//SCENARIO 3: Picking an address for the client
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
	
	
	//SCENARIO 4: Creating a client
	@When("a client {string} is created based on the simulated information")
	public void a_client_is_created_based_on_the_simulated_information(String cid) {
		sim.setSeed(seed);
		company = sim.companySelection();
		address = sim.addressSelection();
		name = sim.nameSelection();
		mail = sim.emailCreation(name, company);
		clients.put(cid, application.createClient(company, address, mail, name, password));
	}

	@Then("that client should be in the database")
	public void that_client_should_be_in_the_database() {
	    assertEquals(application.getClientDat().getClients().size(), 1);
	}
	
	
	//SCENARIO 5: Picking a client for creation of a journey
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
	
	
    
	//SCENARIO 6: Picking content for creation of a journey
	@When("a specific content is selected based on the value of the seed")
	public void a_specific_content_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
		content = sim.contentSelection();
	}

	@Then("that content will be {string}")
	public void that_content_will_be(String generatedContent) {
	    assertEquals(content, generatedContent);
	}
	

	//SCENARIO 7: Picking origin for creation of a journey
	@When("a specific origin is selected based on the value of the seed")
	public void a_specific_origin_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    origin = sim.originSelection();
	}

	@Then("that origin will be {string}")
	public void that_origin_will_be_CPH(String generatedOrigin) {
	    assertEquals(origin, generatedOrigin);
	}
	
	
	//SCENARIO 8: Picking destination for creation of a journey
	@When("a specific destination is selected based on the value of the seed")
	public void a_specific_destination_is_selected_based_on_the_value_of_the_seed() {
		sim.setSeed(seed);
	    destination = sim.destinationSelection(origin);
	}

	@Then("that destination will be {string}")
	public void that_destination_will_be(String generatedDestination) {
	    assertEquals(destination, generatedDestination);
	}

	
	//SCENARIO 9: creating a journey
	@When("a journey {string} is created")
	public void a_journey_is_created(String jid) {
		journeys.put(jid, application.createJourney(origin, destination, content, company));
	}

	@Then("the journey exists in the database")
	public void the_journey_exists_in_the_database() {
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	    assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getId(),"CPHRIX10");
	}
	
	
	// SCENARIO 10: Initializing data for a Journey
	@When("the internal-status measurements are generated for the containers in the journey {string}")
	public void the_internal_status_measurements_are_generated_for_the_containers_in_the_journey(String jid) {
		sim.setSeed(seed);
		temp = sim.temperatureInitialization();
	    pressure = sim.pressureInitialization();
	    hum = sim.humidityInitialization();
	}

	@When("the data is added to the containers in the journey {string}")
	public void the_data_is_added_to_the_containers_in_the_journey(String jid) {
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0);
	    application.updateData(application.getJourneyContainerDat().getActiveJourneys().get(0), c1, temp, pressure, hum);
	}

	@Then("the data will exists in the container for the journey {string}")
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
	
	
	//SCENARIO 11: Generating data based on previous data in a Container
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
	
	
	//SCENARIO 12: Simulate data for 3 times
	@When("a simulated journey {string} is created")
	public void a_journey_is_creatednew(String jid) {
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
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0);
	    assertEquals(c1.getTempList().size(),3);
	}

	@Then("the three different data points are the following")
	public void the_three_different_data_points_are_the_following() {
	    assertEquals(c1.getTempList(), new ArrayList<Integer>(Arrays.asList(17,20,18)));
	    assertEquals(c1.getPressureList(), new ArrayList<Integer>(Arrays.asList(980,946,898)));
	    assertEquals(c1.getHumList(), new ArrayList<Integer>(Arrays.asList(56,51,47)));
	}

	
	//SCENARIO 13: Update Location of a Journey from origin to transition state
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

	@Then("the current location is in transition")
	public void the_current_location_is_in_transition() {
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().get(0).getCurrentLocation(), "IN TRANSIT FROM EBJ TO BOD");
	}
	
	//SCENARIO 14: Update Location of a Journey from transit to destination
	@Then("the current location should be the destination")
	public void the_current_location_should_be_the_destination() {
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
	    	sum = sum + j.getContainerList().size();
	    }
	    for (Journey j : application.getJourneyContainerDat().getPastJourneys()) {
	    	sum = sum + j.getContainerList().size();
	    }
	    assertEquals(sum, 10);
	}
	
	
	
	//CONTAINER TRACKING
    
	//SCENARIO 1
	//CHANGE CHANGE CHANGE
	//CHANGE CHANGE CHANGE
	//CHANGE CHANGE CHANGE
	@Given("Journey and container counter is set to zero, where the counter describes the name of the corresponding id's")
	public void journey_and_container_counter_is_set_to_zero_where_the_counter_describes_the_name_of_the_corresponding_id_s() {
    	Container.setcCounter(0);   
    	Journey.setCounter(0);
    }
	
    @Given("a container with id {string} used in the journey {string}")
    public void a_container_with_id_used_in_the_journey(String search, String jid) {
    	this.search = search; 
    	
    }

    @When("the container id is searched for in the journey history to find all appearences of the given container")
    public void the_container_id_is_searched_for_in_the_journey_history_to_find_all_appearences_of_the_given_container() {
    	containerJourneyHistoryList = application.findContainer(search, application.getJourneyContainerDat().getPastJourneys());
    }

    @Then("the container history of container C0 should be returned")
    public void the_container_history_of_container_C0_should_be_returned() {
    	assertEquals(containerJourneyHistoryList.size(), 1);
    	assertEquals(containerJourneyHistoryList.get(0).getId(), "BUAMIA0"); 	
    }
    
    //SCENARIO 2
    @Given("a container with id {string} used in journey {string} and journey {string}")
    public void a_container_with_id_used_in_journey_and_journey(String search, String jid1, String jid2) {
        this.search = search; 
    }
    
    @Then("the journey history of the resued container {string} should be returned")
    public void the_journey_history_of_the_resued_container_should_be_returned(String search) {
    	assertEquals(containerJourneyHistoryList.get(0).getId(), "BUAMIA0");
    	assertEquals(containerJourneyHistoryList.get(1).getId(), "BUAMIA1");
    	assertEquals(containerJourneyHistoryList.size(),2); 
    }
    
    
    //SCENARIO 3
    @Given("an unsused container with id {string}")
    public void an_unsused_container_with_id(String search) {
        this.search = search;
    }
    
    @Then("the empty journey history of the unsued container {string} should be returned")
    public void the_empty_journey_history_of_the_unsued_container_should_be_returned(String string) {
    	assertEquals(containerJourneyHistoryList.size(),0);
    }
    
    
    //SCENARIO 4
    @When("internal-status measurements for the containers corresponding to journey {string} are being simulated")
    public void internal_status_measurements_for_the_journey_containers_are_being_simulated(String jid) {
    	sim.setSeed(123);
    	temp = sim.temperatureInitialization();
    	pressure = sim.pressureInitialization();
    	hum = sim.humidityInitialization(); 
    }
    
    @When("the simulated internal-status measurements are being added to all containers in the journey {string}")
    public void the_simulated_internal_status_measurements_are_being_added_to_all_containers_in_the_journey(String jid) {
    	application.updateData(journeys.get(jid), journeys.get(jid).getContainerList().get(0), temp, pressure, hum);
    }
    
    @When("the container id is searched for in the journey history to find the internal-status of all journeys the container has been used for")
    public void the_container_id_is_searched_for_in_the_journey_history_to_find_the_internal_status_of_all_journeys_the_container_has_been_used_for() {
    	containerInternalStatusHistoryList = application.containerInternalStatusHistory(search, application.getJourneyContainerDat().getPastJourneys());		
    }
    
    @Then("the internal-status history of container {string} should be returned")
    public void the_internal_status_history_of_container_should_be_returned(String search){
    	assertEquals(containerInternalStatusHistoryList.size(), 1);
    }
 
    
    // SCENARIO 5
    
    @When("a new set of internal-status measurements for the containers corresponding to journey {string} are being simulated")
    public void a_new_set_of_internal_status_measurements_for_the_containers_corresponding_to_journey_are_being_simulated(String string) {
    	sim.setSeed(321);
    	temp1 = sim.temperatureInitialization();
    	pressure1 = sim.pressureInitialization();
    	hum1 = sim.humidityInitialization(); 
    }
    
    @When("the new set of simulated internal-status measurements are being added to all containers in the journey {string}")
    public void the_new_set_of_simulated_internal_status_measurements_are_being_added_to_all_containers_in_the_journey(String jid) {
    	application.updateData(journeys.get(jid), journeys.get(jid).getContainerList().get(0), temp1, pressure1, hum1);
    }
    
    
    @Then("the internal-status history of the reused container {string} should be returned")
    public void the_internal_status_history_of_the_reused_container_should_be_returned(String search) {
    	assertEquals(containerInternalStatusHistoryList.size(),2);
    }
    
    
    //SCENARIO 6
    @When("a container with id {string} not used in the journey {string}")
    public void a_container_with_id_not_used_in_the_journey(String search, String jid) {
    	this.search = search; 
    }
    
    @Then("the empty internal-status history of the unused container {string} should be returned")
    public void the_empty_internal_status_history_of_the_unused_container_should_be_returned(String search) {
        assertEquals(containerInternalStatusHistoryList.size(),0);
    }

    
    

    //PERSISTENCY LAYER

    //SCENARIO 1: Storing two clients    
	@Then("the clients are present in the Clients xml")
	public void the_active_clients_are_present_in_the_Clients_xml() {
		System.out.println("yay" + application.getClientDat().readClientFile());
		assertEquals(application.getClientDat().readClientFile().size(),2);
		assertEquals(application.getClientDat().readClientFile(), application.getClientDat().getClients());
		assertEquals(application.getClientDat().readClientFile().get(0).getId(), application.getClientDat().getClients().get(0).getId());
	}
	
	//SCENARIO 2: Restoring a client when client information is updated
	@When("update and store the client {string} name to {string}")
	public void update_the_client_name_to(String cid, String name) {
		clients.get(cid).updateName(name);
		application.getClientDat().storeClients();
	}

	@Then("the clients correct information is present in the Clients xml")
	public void the_clients_correct_information_is_present_in_the_Clients_xml() {
		assertEquals(application.getClientDat().readClientFile().size(),1);
		assertEquals(application.getClientDat().readClientFile(), application.getClientDat().getClients());
		assertEquals(application.getClientDat().readClientFile().get(0).getId(), application.getClientDat().getClients().get(0).getId());
	}
    
    
    //SCENARIO 3: Storing active journey 	
	@Given("a second seed {int} pointing to a particular instance in the simulator")
	public void a_second_seed_pointing_to_a_particular_instance_in_the_simulator(Integer int2) {
		this.seed2 = int2;
	}
    
	@When("the second set of internal-status measurements are generated for the containers in the journey {string}")
	public void the_second_set_of_internal_status_measurements_are_generated_for_the_containers_in_the_journey(String jid) {
		sim.setSeed(seed2);
		hum2 = sim.humidityInitialization(); 
		pressure2 = sim.pressureInitialization(); 
		temp2 = sim.temperatureInitialization(); 
	}
	
	@When("the second set of data is added to the containers in the journey {string}")
	public void the_second_set_of_data_is_added_to_the_containers_in_the_journey(String jid) {
		c1 = application.getJourneyContainerDat().getActiveJourneys().get(0).getContainerList().get(0);
	    application.updateData(application.getJourneyContainerDat().getActiveJourneys().get(0), c1, temp2, pressure2, hum2);
	}

	@Then("the internal-status measurements will be stored in the container for the journey {string}")
	public void the_internal_status_measurements_will_be_stored_in_the_container_for_the_journey(String jid) {
		application.getJourneyContainerDat().readActiveJourneyFile();
		assertEquals(application.getJourneyContainerDat().getActiveJourneys().size(), 1);
	}

	@Then("the data has specific values")
	public void the_data_has_specific_values() {
		assertEquals(hum, c1.getHumList().get(0));
		assertEquals(pressure, c1.getPressureList().get(0));
		assertEquals(temp, c1.getTempList().get(0));
		assertEquals(hum2, c1.getHumList().get(1));
		assertEquals(pressure2, c1.getPressureList().get(1));
		assertEquals(temp2, c1.getTempList().get(1));
		assertNotEquals(temp, temp2);
		assertNotEquals(pressure, pressure2);
		assertNotEquals(hum, hum2);
	}
	
    
    //SCENARIO 4: Storing Journey history and ContainerWarehouse
    @Then("the ended journey are present in the EndedJourney xml")
    public void the_ended_journey_are_present_in_the_EndedJourney_xml() {
    	assertEquals(application.getJourneyContainerDat().readEndedJourneyFile().size(), 1); 
    	assertEquals(application.getJourneyContainerDat().readEndedJourneyFile().get(0).getContainerList().get(0).getId(), application.getJourneyContainerDat().getPastJourneys().get(0).getId());
    }
  	 
	@Then("the container assigned to the given journeys are present in the ContainerWarehouse xml")
	public void the_container_assigned_to_the_given_journeys_are_present_in_the_ContainerWarehouse_xml() {
		assertEquals(application.getJourneyContainerDat().readContainerWarehouseFile().size(),1);
		assertEquals(application.getJourneyContainerDat().readContainerWarehouseFile(), application.getJourneyContainerDat().getContainerWarehouse());
		assertEquals(application.getJourneyContainerDat().readContainerWarehouseFile().get(0).getId(), application.getJourneyContainerDat().getContainerWarehouse().get(0).getId());
	}
	
	
	//SCENARIO 5: Storing Journey history and ContainerWarehouse for journey with multiple containers
	@Then("the containers assigned to the given journeys are present in the ContainerWarehouse xml")
	public void the_containers_assigned_to_the_given_journeys_are_present_in_the_ContainerWarehouse_xml() {
		assertEquals(application.getJourneyContainerDat().readContainerWarehouseFile().size(),2);
		assertEquals(application.getJourneyContainerDat().readContainerWarehouseFile(), application.getJourneyContainerDat().getContainerWarehouse());
	}	
}

