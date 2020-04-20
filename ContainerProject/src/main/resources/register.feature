Feature: Journey Registration functionality
  This feature will handle all container registrations for future journeys.



  Scenario: create a new journey and check that it is the system
    Given a new port of origin "cph"
    And a new port of destination "RIO"
    And content "BANANA"
    And company "DK"
    When registered
    Then a unique Id is created
    And the origin and destination is correct
    And content and company is correct
    And journey is in database

  Scenario: the client adds more journeys to the system
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey "cph" "fra" "covid19" "gov"
    And a new journey with "ber" "mia" "covid19" "novo"
    When journeys are registered
    Then journeys exist on database
    
  Scenario: the client adds more journeys to the system, but they are the same.
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey "cph" "fra" "covid19" "gov"
    And a new journey with "cph" "fra" "bananas" "novo"
    When journeys are registered
    Then only one journey is created
    And the journey has two containers



  Scenario: Search for journeys with content: banana
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new journey j2 "ber" "mia" "covid19" "novo"
    And a keyword "banana"
    When it matches for the keyword
    Then the empty list matches is returned

  Scenario: a client searches for journeys using port of origin: ber
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new journey j2 "ber" "mia" "covid19" "novo"
    And a keyword "ber"
    When it matches for the keyword
    Then a filtered list matches containing j2 is returned

  Scenario: a client searches for journeys using port of destination: fra
  	Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new journey j2 "ber" "mia" "covid19" "novo"
    And a keyword "fra"
    When it matches for the keyword
    Then a filtered list matches containing j1 is returned
    


    Scenario: the client should be updated on the journeys current position
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    When the journeys current location is found
    Then it should be returned to the customer
    
    Scenario: the logistics company should be able to update the current position of the journey
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new position "fra"
    When the journeys current location is updated
    Then the current location should be updated
    
    Scenario: the journey ends
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new position "fra"
    When the journeys current location is updated
    And the journey is over
    Then the journey is put into history
    And the containers are emtied
    And the containers are stored in the warehouse
    
    Scenario: assigning container from containerwarehouse
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a new journey j1 "cph" "fra" "covid19" "gov"
    And a new position "fra"
    And the journeys current location is updated 
    And the journey is over
    And a new journey j2 "ber" "mia" "covid19" "novo"
    When assigning containers
    Then the container should be taken from containerwarehouse
    