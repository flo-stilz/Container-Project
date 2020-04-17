Feature: container tracker
  This feature will return any given containers journey history
  
  Scenario: Journey-History Tracking of container
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j5 with origin "bua" destination "mia" content "covid19" and company "novo"
    And the journey j5 is completed
    And a container with id "C0" used in the journey j5
    When the container id is searched for in the journey history to find all journeys the container has been used for
    Then the container C0s journey history should be returned
    
  Scenario: Journey-History Tracking of reused container
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j6 with origin "bua" destination "mia" content "books" and company "nemlig.dk"
    And the journey j6 is completed
    And a journey j7 with origin "bua" destination "mia" content "iphones" and company "EmiratesAirlines"
    And the journey j7 is completed
    And a container with id "C0" used in journey j6 and journey j7
    When the container id is searched for in the journey history to find all journeys the container has been used for
    Then the reused container C0s journey history should be returned
    
  Scenario: Journey-History Tracking of unused container
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j8 with origin "nyc" destination "mad" content "covid19" and company "novo"
    And the journey j8 is completed
    And an unsused container with id "C1"
    When the container id is searched for in the journey history to find all journeys the container has been used for
    Then the container C1s empty journey history will be returned
    
  Scenario: Internal-Status tracking of container
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j9 with origin "par" destination "mia" content "covid19" and company "novo"
    When internal-status measurements for the journey j9s containers are being simulated
    And the simulated data is being added to all containers in the journey j9
    And the journey j9 is completed
    And a container with id "C0" used in the journey j9
    And the container id is searched for in the journey history to find the internal-status of all journeys the container has been used for
    Then the container C0s internal-status history for journey j9 should be returned
    
  Scenario: Internal-Status tracking of reused container
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j10 with origin "cph" destination "bej" content "books" and company "novo"
    And internal-status measurements for the journey j10s containers are being simulated
    And the simulated internal-status measurements is being added for all containers in the journey j10
    And the journey j10 is completed
    And a journey j11 with origin "cph" destination "bej" content "iphones" and company "novo"
    And a new container with id "C0" used in the journey j11
    When internal-status measurements for the journey j11s containers are being simulated
    And the simulated internal-status measurements is being added for all containers in the jouney j11
    And the journey j11 is completed
    And the container id is again searched for in the journey history to find the internal-status of all journeys the container has been used for in j10 and j11
    Then the container C0s internal-status history for journey j10 and journey j11 should be returned
    
  Scenario: internal-Status tracking of unused container
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    And a journey j12 with origin "bdp" destination "tor" content "pencils" and company "novo"
    When internal-status measurements for the journey j12s containers are being simulated
    And the simulated internal-status measurements is being added for all containers in the journey j12
    And the journey j12 is completed
    And a new container with id "C1" not used in the journey j11 
    And the container id is searched for in the journey history to find the internal-status of all journeys the container has been used for
    Then The unused container C1s internal-status history should be returned and will be empty