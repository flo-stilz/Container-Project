Feature: Keep Track of the Evolution
  This feature will handle the relevant tracking-features for a journeys set of containers
  
  Scenario: Track the journey-history of a container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "bua" destination "mia" content "covid19" and company "novo"
    And the journey "j1" is completed
    And a container with id "C0" used in the journey "j1"
    When the container id is searched for in the journey history
    Then the container history of the container "C0" is returned
        
  Scenario: Track the journey-history of a reused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j1" with origin "bua" destination "mia" content "books" and company "nemlig.dk"
    And the journey "j1" is completed
    And a journey "j2" with origin "bua" destination "mia" content "iphones" and company "EmiratesAirlines"
    And the journey "j2" is completed
    And a container with id "C0" used in journey "j1" and journey "j2"
    When the container id is searched for in the journey history
    Then the journey history of the resued container "C0" is returned
    
  Scenario: Track the journey-history of an unused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j8" with origin "nyc" destination "mad" content "covid19" and company "novo"
    And the journey "j8" is completed
    And an unsused container with id "C1"
    When the container id is searched for in the journey history
    Then the empty journey history of the unsued container "C1" is returned
    
  Scenario: Track the internal-status-history of a container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j9" with origin "par" destination "mia" content "covid19" and company "novo"
    And internal-status measurements for the containers corresponding to journey "j9" are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j9"
    And the journey "j9" is completed
    And a container with id "C0" used in the journey "j9"
    When the container id is searched for in the internal-status history
    Then the internal-status history of container "C0" is returned
    
  Scenario: Track the internal-status-history of a reused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j10" with origin "cph" destination "bej" content "books" and company "novo"
    And internal-status measurements for the containers corresponding to journey "j10" are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j10"
    And the journey "j10" is completed
    And a journey "j11" with origin "cph" destination "bej" content "iphones" and company "novo"
    And a container with id "C0" used in the journey "j11"
    And a new set of internal-status measurements for the containers corresponding to journey "j11" are being simulated
    And the new set of simulated internal-status measurements are being added to all containers in the journey "j11"
    And the journey "j11" is completed
    When the container id is searched for in the internal-status history
    Then the internal-status history of the reused container "C0" is returned
    
  Scenario: Track the internal-status-history of an unused container
    Given the journey and container counter is set to zero which describes the name of the corresponding ids
    And a journey "j12" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And internal-status measurements for the containers corresponding to journey "j12" are being simulated
    And the simulated internal-status measurements are being added to all containers in the journey "j12"
    And the journey "j12" is completed
    And a container with id "C1" not used in the journey "j12"
    When the container id is searched for in the internal-status history
    Then the empty internal-status history of the unused container "C1" is returned
    
  Scenario: Filtering for all active containers
    Given a journey "j12" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j13" with origin "cph" destination "bej" content "books" and company "novo"
    When filtering for all the active containers
    Then a list of all the active containers is returned
  
  Scenario: Filtering for all containers in the containerwarehouses
    Given a journey "j12" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j13" with origin "cph" destination "bej" content "books" and company "novo"
    And the journey "j12" is completed
    When filtering for all the containers in the containerwarehouse
    Then a list of all containers in the containerwarehouse is returned
    
  Scenario: Retrieving all containers
    Given a journey "j12" with origin "bdp" destination "tor" content "pencils" and company "novo"
    And a journey "j13" with origin "cph" destination "bej" content "books" and company "novo"
    And the journey "j12" is completed
    When all containers have been retrieved
    Then a list of all containers are returned