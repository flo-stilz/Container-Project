Feature: Journey Simulator
   This feature will handle the data generation 

  Scenario: Picking a Company for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific company out of the 16 available is selected based on the value of the seed
    Then the company selected is "Netto"
    And the company "Netto" is removed from the list of companies
    
	Scenario: Picking a name and email for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific company out of the 16 available is selected based on the value of the seed
    Then a specific name and email is selected based on the value of the seed
    And that name and email will be "Bill Silva" and "Bill.Silva@Netto.com"
    
	Scenario: Picking an address for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific address out of the 16 available is selected based on the value of the seed
    Then that selected address will be "Kings Street 100"
    And that address will be removed from the list of addresses
    
	Scenario: Creating a client
    Given a seed 123 pointing to a particular instance in the simulator
    When a client "client1" is created based on the simulated information
    Then that client should be in the database 
    
 Scenario: Picking a client for creation of a journey
    Given a client "client1" with seed 123
    And a client "client2" with seed 98765
    And a client "client3" with seed 123123
    When a client is choosen with seed 1234
    Then that clientId will be 69434
	    
	Scenario: Picking content for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific content is selected based on the value of the seed
	    Then that content will be "bananas"
  	
	Scenario: Picking origin for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific origin is selected based on the value of the seed
	    Then that origin will be "CPH"
	    
	Scenario: Picking destination for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific destination is selected based on the value of the seed
	    Then that destination will be "CPH"
	    
	Scenario: creating a journey
			Given a seed 123 pointing to a particular instance in the simulator
	    And a client "client4" with seed 123
	    And a client "client5" with seed 98765
	    And a client "client6" with seed 123123
	    And a client is choosen with seed 1234
	    And a specific content is selected based on the value of the seed
	    And a specific origin is selected based on the value of the seed
	    And a specific destination is selected based on the value of the seed
	    When a journey "j1" is created
	    Then the journey exists in the database
	    
  Scenario: Initializing data for a Journey
  		Given a journey "j16" with origin "arr" destination "cph" content "phones" and company "maersk"
  		And a seed 123 pointing to a particular instance in the simulator
  		When the internal-status measurements are generated for the containers in the journey "j16"
  		And the data is added to the containers in the journey "j16"
  		Then the data will exists in the container for the journey "j16"
  		And the data has the specific values
	    
  Scenario: Generating data based on previous data in a Container
  		Given a journey "j17" with origin "Gua" destination "Rot" content "soft drinks" and company "Arca Continental" 
  		And a seed 123 pointing to a particular instance in the simulator
  		And the internal-status measurements are generated for the containers in the journey "j17"
  		And the data is added to the containers in the journey "j17"
  		When the internal-status measurements are generated based on the previously generated internal-status measurements 
  		Then the internal-status measurements are added to all the containers in the journey
  		Then the internal-status measurements are different from the internal-status measurements initially generated
  		
	Scenario: Simulate data for 3 times
			Given a client "client5" with seed 1909
			And a client "client4" with seed 20313
			And a seed 123 pointing to a particular instance in the simulator
			And a simulated journey "j2" is created
			When internal-status measurements are simulated and updated thrice with the following seeds 123 5039 3975
			Then three internal-status measurements of each type will be added to all the containers in the journey
			Then the three different data points are the following
  		
	Scenario: Update Location of a Journey from origin to transition state
			Given a seed 123 pointing to a particular instance in the simulator
			And a simulated client "client6" is created with seed 1909
			And a simulated client "client7" is created with seed 429
			And a simulated journey "j3" is created
			And a number of days 6
			When the number of days pass by and the location gets updated
			Then the current location is in transition
			
	Scenario: Update Location of a Journey from transit to destination
			Given a seed 123 pointing to a particular instance in the simulator
			And a simulated client "client8" is created with seed 1909
			And a simulated client "client9" is created with seed 429
			And a simulated journey "j4" is created
			And a number of days 7
			When the number of days pass by and the location gets updated 
			Then the current location should be the destination
			
	Scenario: Simulation of the program for 20 days
  		Given a number of days 20
  		When simulation is running
  		Then the amount of clients in the system should be 4
  		Then the sum of the containers in all the active journeys and in the past journeys is 10 
  		