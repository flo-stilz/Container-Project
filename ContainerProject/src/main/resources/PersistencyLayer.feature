Feature: Persistency Layer
	This feature will apply a persistency layer to all the relevant data

  Scenario: Storing two clients
    Given a client "client8" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And a second client "client9" with company name "Miele" address "dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    And the client "client8" is registered in the client database
    And the second client "client9" is registered in the client database
    When a deep copy of the clients are made
    And reading the generated ClientFile
    Then the size of the client file is the same as the deep copy 
    
  Scenario: Restoring a client when client information is updated
    Given a client "client10" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And the client "client10" is registered in the client database
    And update and store the client "client10" name to "Mads Mikkelsen"
    When a deep copy of the updated list of clients
    And read the generated ClientFile
    Then test2

  Scenario: Storing active journey 
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey "j20" with origin "Bar" destination "Val" content "bricks" and company "gls"
    And a seed 123 pointing to a particular instance in the simulator
    And a second seed 321 pointing to a particular instance in the simulator
    When the internal-status measurements are generated for the containers in the journey "j20"
    And the second set of internal-status measurements are generated for the containers in the journey "j20"
    And the data is added to the containers in the journey "j20"
    And the second set of data is added to the containers in the journey "j20"
    And copying the most recent list of journeys
    And reading the generated ActiveJourney file
    Then the size and content of the journey file is the same 
    And the data has specific values
    
  Scenario: Storing Journey history and ContainerWarehouse
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
  	And a journey "j21" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	When the internal-status measurements are generated for the containers in the journey "j21"
  	And the data is added to the containers in the journey "j21"
  	And the journey "j21" is completed
  	And a deep copy of the list of past journeys
    And read the generated pastjourneyfile and the containwarehousefile
    Then the size and content of the two files are the right
  	
  Scenario: Storing Journey history and ContainerWarehouse for journey with multiple containers
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
  	And a journey "j22" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a journey "j23" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	When the internal-status measurements are generated for the containers in the journey "j22"
  	And the data is added to the containers in the journey "j22"
  	And the journey "j22" is completed
  	And a deep copy of the list of past journeys2
    And read the generated pastjourneyfile and the containwarehousefile2
    Then the size and content of the two files are the right2
  	
  Scenario: Store Client Counter 
  	Given a client "client8" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson" password "peter"
    And a second client "client9" with company name "Miele" address "dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    When copying the most recent client counter
    And reading the generated client counter file
    Then the most recent client counter is 
  
  Scenario: Store Journey Counter
    Given a journey "j22" with origin "mad" destination "mal" content "candles" and company "gls"
  	And a journey "j23" with origin "tri" destination "mal" content "phones" and company "gls"
  	And copying the most recent journey counter
  	And reading the generated journey counter file
  	Then the most recent journey counter is 

  Scenario: Store Container Counter
    And a journey "j23" with origin "mad" destination "mal" content "candles" and company "gls"
  	And a journey "j23" with origin "mad" destination "mal" content "candles" and company "gls"
  	And copying the most recent container counter
  	And reading the generated container counter file
  	Then the most recent container counter is