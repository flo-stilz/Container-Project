Feature: Persistency Layer
	This feature will apply a persistency layer to all the relevant data


  Scenario: Storing two clients
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a client "client8" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson"
    And a second client "client9" with company name "Miele" address "dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge"
    When the client "client8" is registered in the client database
    And the second client "client9" is registered in the client database
    Then the clients are present in the Clients xml
    
  Scenario: Restoring a client when client information is updated
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a client "client10" with company name "Nike" address "madisonsquaregarden" email "nike@administration.com" contact person "Peter Jackson"
    When the client "client10" is registered in the client database
    And update and store the client "client10" name to "Mads Mikkelsen"
    Then the clients correct information is present in the Clients xml

  Scenario: Storing active journey 
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey "j20" with origin "Bar" destination "Val" content "bricks" and company "gls"
    And a seed 123 pointing to a particular instance in the simulator
    And a second seed 321 pointing to a particular instance in the simulator
    When the internal-status measurements are generated for the containers in the journey "j20"
    And the second set of internal-status measurements are generated for the containers in the journey "j20"
    And the data is added to the containers in the journey "j20"
    And the second set of data is added to the containers in the journey "j20"
    Then the internal-status measurements will be stored in the container for the journey "j20"
    And the data has specific values
    
  Scenario: Storing Journey history and ContainerWarehouse
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
  	And a journey "j21" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	When the internal-status measurements are generated for the containers in the journey "j21"
  	And the data is added to the containers in the journey "j21"
  	And the journey "j21" is completed
  	Then the ended journey are present in the EndedJourney xml
  	And the container assigned to the given journeys are present in the ContainerWarehouse xml
  	
  Scenario: Storing Journey history and ContainerWarehouse for journey with multiple containers
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
  	And a journey "j22" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a journey "j23" with origin "tri" destination "mal" content "phones" and company "gls"
  	And a seed 123 pointing to a particular instance in the simulator
  	When the internal-status measurements are generated for the containers in the journey "j22"
  	And the data is added to the containers in the journey "j22"
  	And the journey "j22" is completed
  	Then the ended journey are present in the EndedJourney xml
  	And the containers assigned to the given journeys are present in the ContainerWarehouse xml
  	
    	
  	
    