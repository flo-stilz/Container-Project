#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template

Feature: Simulator

  Scenario: Picking a Company for the client
    Given a seed 123
    When A company is choosen randomly
    Then that company will be Netto
    And Netto is removed from the list of companies
    
	Scenario: Picking a  name and email for the client
    Given a seed 123
    And A company is choosen randomly
    When A name and email is choosen randomly
    Then that name will be 
    
	Scenario: Picking an address for the client
    Given a seed 123
    When An address is choosen randomly
    Then that address will be 
    And that address will be removed from the list of addresses
    
	Scenario: Creating a client
    Given a seed 123
    When a client is created
    Then that client should be in the database 
    
 Scenario: Picking a client for creation of a journey
    Given a client with seed 123
    And a client with seed 98765
    And a client with seed 123123
    When a client is choosen with seed 1234
    Then that clientId will be
	    
	Scenario: Picking content for creation of a journey
	    Given a seed 123
	    When a content is choosen 
	    Then that content will be bananas
  	
	Scenario: Picking origin for creation of a journey
	    Given a seed 123
	    When a origin is choosen 
	    Then that origin will be CPH
	    
	Scenario: Picking destination for creation of a journey
	    Given a seed 123
	    And a origin is choosen
	    When a destination is choosen 
	    Then that destination will be RIX
	    
	Scenario: creating a journey
			Given a seed 123
	    And a client with seed 123
	    And a client with seed 98765
	    And a client with seed 123123
	    And a client is choosen with seed 1234
	    And a content is choosen
	    And a origin is choosen
	    And a destination is choosen
	    When a journey is created
	    Then that journey should be in the database
	    
  Scenario: Initializing data for a Journey
  		Given a Journey "CPH" "AAR" "covid19" "maersk"
  		And a seed 123
  		When data is generated
  		And data is added to the Journey
  		Then the data will be stored in the Container
  		And the data has the specific values
	    
  Scenario: Generating data based on previous data in a Container
  		Given a Journey "CPH" "AAR" "covid19" "maersk"
  		And a seed 123
  		And data is generated
  		And data is added to the Journey
  		When data is generated based on previous data
  		Then the data is the following
  		And data was added to container
  		
	Scenario: Simulate data for 3 times
			Given a client is created with seed 1909
			And a client is created with seed 20313
			And a seed 123
			And a Journey is created
			When data is simulated and updated thrice with the following seeds 123 5039 3975
			Then the there will be three datapoints of each type stored in the container
			Then the three different data points are the following
  		
	Scenario: Update Location of a Journey from origin to transition state
			Given a seed 123
			And a client is created with seed 1909
			And a client is created with seed 429
			And a Journey is created
			And amount of days 6
			When days pass by and location gets updated
			Then the current Location should be in transition
			
	Scenario: Update Location of a Journey from transit to destination
			Given a seed 123
			And a client is created with seed 1909
			And a client is created with seed 429
			And a Journey is created
			And amount of days 7
			When days pass by and location gets updated 
			Then the current location should be destination
			
	Scenario: Simulation of the program for 20 days
  		Given amount of days 20
  		When simulation is running
  		Then the amount of clients in the system should be 4
  		Then the sum of the containers in all the active journeys and in the past journeys is 10 
  	
  	
    
 

