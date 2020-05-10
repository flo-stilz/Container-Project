Feature: Keep Track of the Evolution
  This feature will handle the relevant tracking-features for a journeys set of containers
  
  Scenario: Track the journey-history of a container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "bua" destination "mia" content "covid19" and company "novo"
    And the journey "j1" is completed
    And a keyword "C0" describing the id of a container
    When searching for journeys with the same container id in the list of past journeys
    Then the container history of the searched container is returned
        
  Scenario: Track the journey-history of a reused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "bua" destination "mia" content "books" and company "nemlig.dk"
    And the journey "j1" is completed
    And a journey "j2" with origin "bua" destination "mia" content "iphones" and company "EmiratesAirlines"
    And the journey "j2" is completed
    And a keyword "C0" describing the id of a container
    When searching for journeys with the same container id in the list of past journeys
    Then the journey history of the reused container is returned
    
  Scenario: Track the journey-history of an unused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "nyc" destination "mad" content "covid19" and company "novo"
    And the journey "j1" is completed
    And a keyword "C1" describing the id of a container
    When searching for journeys with the same container id in the list of past journeys
    Then the empty journey history of the unsued container is returned
    
  Scenario: Track the internal-status-history of a container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "par" destination "mia" content "covid19" and company "novo"
    And internal-status measurements for the containers corresponding to journey are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j1"
    And the journey "j1" is completed
    And a keyword "C0" describing the id of a container
    When searching for the history of internal-status measurements for the given container
    Then the internal-status history of the container is returned
    
  Scenario: Track the internal-status-history of a reused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "cph" destination "bej" content "books" and company "novo"
    And internal-status measurements for the containers corresponding to journey are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j1"
    And the journey "j1" is completed
    And a journey "j2" with origin "cph" destination "bej" content "iphones" and company "novo"
    And a keyword "C0" describing the id of a container
    And a new set of internal-status measurements for the containers corresponding to the second journey are being simulated
    And the new set of simulated internal-status measurements are being added to all containers in the journey "j2"
    And the journey "j2" is completed
    When searching for the history of internal-status measurements for the given container
    Then the internal-status history of the reused container is returned
    
  Scenario: Track the internal-status-history of an unused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And internal-status measurements for the containers corresponding to journey are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j1"
    And the journey "j1" is completed
    And a keyword "C1" describing the id of a container
    When searching for the history of internal-status measurements for the given container
    Then the empty internal-status history of the unused container is returned
    
  Scenario: Filter for all active containers
    Given a journey "j1" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j2" with origin "cph" destination "bej" content "books" and company "novo"
    And a journey "j3" with origin "bua" destination "mia" content "iphones" and company "EmiratesAirlines"
    And the journey "j3" is completed
    When filtering a database for all the active containers
    Then a list of all the active containers is returned
  
  Scenario: Filter for all containers in the container warehouse
    Given a journey "j1" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j2" with origin "cph" destination "bej" content "books" and company "novo"
    And the journey "j1" is completed
    When filtering a database for all the containers in the containerwarehouse
    Then a list of all containers in the containerwarehouse is returned
    
  Scenario: Retrieve all containers
    Given a journey "j1" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j2" with origin "cph" destination "bej" content "books" and company "novo"
    And the journey "j1" is completed
    When retrieving all containers in the database
    Then a list of all containers are returned