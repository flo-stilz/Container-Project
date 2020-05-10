Feature: Journey Simulator
   This feature will handle the data generation 

  Scenario: Pick a Company for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific company out of the 16 available is selected based on the value of the seed
    Then the company selected is "Netto"
    And the company "Netto" is removed from the list of companies
    
	Scenario: Pick a name and email for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific company out of the 39 available is selected based on the value of the seed
    Then a specific name and email is selected based on the value of the seed
    And the name and email "Hubert Saito" and "Hubert.Saito@MicrosoftCorp.com" is returned
    
	Scenario: Pick an address for the client
    Given a seed 123 pointing to a particular instance in the simulator
    When a specific address out of the 39 available is selected based on the value of the seed
    Then that selected address will be "Frimestervej 32, 2400 Koebenhavn"
    And that address will be removed from the list of addresses
    
	Scenario: Create a client
    Given a seed 123 pointing to a particular instance in the simulator
    When a client is created based on the simulated information
    Then the client exists in the database 
    
 Scenario: Pick a client for creation of a journey
    Given a client with seed 123
    And a client with seed 98765
    And a client with seed 123123
    When a client is choosen with seed 1234
    Then that clientId will be 69446
	    
	Scenario: Pick content for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific content is selected based on the value of the seed
	    Then the content "bananas" is returned
  	
	Scenario: Pick origin for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific origin is selected based on the value of the seed
	    Then the origin "CPH" is returned
	    
	Scenario: Pick destination for creation of a journey
	    Given a seed 123 pointing to a particular instance in the simulator
	    When a specific destination is selected based on the value of the seed
	    Then the destination "CPH" is returned
	    
	Scenario: Create a journey
			Given a seed 123 pointing to a particular instance in the simulator
	    And a client with seed 123
	    And a client with seed 98765
	    And a client with seed 123123
	    And a client is choosen with seed 1234
	    And a specific content is selected based on the value of the seed
	    And a specific origin is selected based on the value of the seed
	    And a specific destination is selected based on the value of the seed
	    When a journey "j1" is created
	    Then the journey exists in the database
	    
  Scenario: Initialize data for a Journey
  		Given a journey "j1" with origin "arr" destination "cph" content "phones" and company "maersk"
  		And a seed 123 pointing to a particular instance in the simulator
  		When the internal-status measurements are generated for the containers in the journey 
  		And the data is added to the containers in the journey
  		Then the data exists in the container for the journey
  		And the data has the specific values
	    
  Scenario: Generatate data based on previously entered data in a Container
  		Given a journey "j1" with origin "Gua" destination "Rot" content "soft drinks" and company "Arca Continental" 
  		And a seed 123 pointing to a particular instance in the simulator
  		And the internal-status measurements are generated for the containers in the journey
  		And the data is added to the containers in the journey
  		When the internal-status measurements are generated based on the previously generated internal-status measurements 
  		Then the internal-status measurements are added to all the containers in the journey

	Scenario: Simulate internal-status measurements thrice
			Given a client with seed 1909
			And a client with seed 20313
			And a seed 123 pointing to a particular instance in the simulator
			And a simulated journey "j2" is created
			When internal-status measurements are simulated and updated thrice with the following seeds 123 5039 3975
			Then three internal-status measurements of each type will be added to all the containers in the journey
			And the three sets of internal-status measurements are returned
  		
	Scenario: Update the location of a Journey from origin to transition state
			Given a seed 123 pointing to a particular instance in the simulator
			And a simulated client "client6" is created with seed 1909
			And a simulated client "client7" is created with seed 429
			And a simulated journey "j3" is created
			And a number of days 6
			When the number of days pass by and the location gets updated
			Then the current location is in transition between the origin and destination
			
	Scenario: Update the location of a Journey from transition state to destination
			Given a seed 123 pointing to a particular instance in the simulator
			And a simulated client "client8" is created with seed 1909
			And a simulated client "client9" is created with seed 429
			And a simulated journey "j4" is created
			And a number of days 8
			When the number of days pass by and the location gets updated 
			Then the current location is the destination
			
	Scenario: Simulation of the program for 20 days
  		Given a number of days 20
  		When simulation is running
  		Then the amount of clients in the system should be 4
  		Then the sum of the containers in all the active journeys and in the past journeys is 10
  		