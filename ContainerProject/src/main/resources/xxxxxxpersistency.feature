Feature: Persistency Layer
	This feature will apply a persistency layer to all the relevant data

  Scenario: Store two clients
    Given a client "client1" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And a second client "client2" with company name "Miele" address "dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    And the client "client1" is registered in the database
    And the second client "client2" is registered in the database
    And copying the current list of clients
    When storing the current list of clients
    And reading the generated client file
    Then the client file matches the copied list of clients 
    
  Scenario: Restore a client when client information is updated
    Given a client "client1" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And the client "client1" is registered in the database
    And update and store the client "client1" name to "Mads Mikkelsen"
    When copying the current list of clients
    When storing the current list of clients
    And reading the generated client file
    Then the client file is the same as the copied list of clients

  Scenario: Store active journey 
    Given a journey "j1" with origin "Bar" destination "Val" content "bricks" and company "gls"
    And a seed 123 pointing to a particular instance in the simulator
    And a second seed 321 pointing to a particular instance in the simulator
    And the internal-status measurements are generated for the containers in the journey "j1"
    And the second set of internal-status measurements are generated for the containers in the journey "j1"
    And the data is added to the containers in the journey "j1"
    And the second set of data is added to the containers in the journey "j1"
    And copying the current list of active journeys
    When storing the current list of active journeys
    And reading the generated activejourney file
    Then the activejourney file matches the copied list of active journeys 
    And the data has specific values
    
  Scenario: Store a past journey and store the changes in the container warehouse
  	Given a journey "j21" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	When the internal-status measurements are generated for the containers in the journey "j21"
  	And the data is added to the containers in the journey "j21"
  	And the journey "j21" is completed
  	And copying the current list of past journeys and the containers in the container warehouse
  	When storing the current list of past journey and the containers in the container warehouse
    And reading the generated pastjourney file and the containwarehouse file
    Then the pastjourney file matches the copied list of past journeys
    And the containerwarehouse file matches the copied list of containers in the container warehouse
  	
  Scenario: Store a past journey that had multiple containers and store the changes in the container warehouse
   	Given a journey "j1" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a journey "j2" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	And the internal-status measurements are generated for the containers in the journey "j1"
  	And the data is added to the containers in the journey "j1"
  	And the journey "j1" is completed
  	And copying the current list of past journeys and the containers in the container warehouse 
  	When storing the current list of past journey and the containers in the container warehouse
    And reading the generated pastjourney file and the containwarehouse file
    Then the pastjourney file is the same as the copied list of past journeys
    And the containerwarehouse file is the same as the copied list of containers in the container warehouse
  	
  Scenario: Store the most recent client counter
  	Given a client "client1" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And a second client "client1" with company name "Miele" address "dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    And copying the most recent client counter
    When storing the most recent client counter
    And reading the generated client counter file
    Then the most recent client counter is 
  
  Scenario: Store the most recent journey counter
    Given a journey "j1" with origin "mad" destination "mal" content "candles" and company "gls"
  	And a journey "j2" with origin "tri" destination "mal" content "phones" and company "gls"
  	And copying the most recent journey counter
  	When storing the most recent journey counter
  	And reading the generated journey counter file
  	Then the most recent journey counter is 

  Scenario: Store the most recent container counter
    Given a journey "j1" with origin "mad" destination "mal" content "candles" and company "gls"
  	And a journey "j2" with origin "mad" destination "mal" content "candles" and company "gls"
  	And copying the most recent container counter
  	When storing the most recent container counter
  	And reading the generated container counter file
  	Then the most recent container counter is