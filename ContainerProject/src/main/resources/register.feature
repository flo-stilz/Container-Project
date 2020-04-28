Feature: Journey Registration functionality
  This feature will handle all container registrations for future journeys.


  Scenario: Create a journey
    Given an origin "cph" destination "rio" content "bananas" and company "gls"
    When the journey "j1" is registered
    Then a unique journey id is created
    And the origin and destination for the journey is correct
    And the content and company for the journey is correct
    And the journey is in the database

  Scenario: Creating multiple journeys
    Given an origin "cph" destination "rio" content "bananas" and company "gls"
    And a second origin "ber" destination "mia" content "covid19" and company "novo"
    When the journey "j2" is registered
    When the second journey "j3" is registered
    Then journeys exist on database
    
  Scenario: Creating multiple similair journeys
    Given an origin "dys" destination "rio" content "bananas" and company "gls"
    And an origin "dys" destination "rio" content "ice cream" and company "gls"
    When the journey "j4" is registered
    When the journey "j5" is registered
    Then only one journey is created
    And the journey has two containers

  Scenario: Search for a journey that is not present in the journeylist using a specific address
    Given a journey "j6" with origin "Dub" destination "Cat" content "canned tuna" and company "Ocean More Foods Co., Limited"
    Given a journey "j7" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a keyword "pasta"
    When searching through the journey database using the keyword
    Then an empty list of journeys in the journey database matching the keyword is returned

  Scenario: Search for a journey in the journeylist using a specific origin
    Given a journey "j8" with origin "Mum" destination "Nav" content "Cotton" and company "East India Cotton Manufacturing Co., Limited"
    Given a journey "j9" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Mum"
    When searching through the journey database using the keyword
    Then the corresponding journey matching the origin-keyword is returned

  Scenario: Search for a journey in the journeylist using a specific destination
    Given a journey "j10" with origin "Dij" destination "Tia" content "Pharmaceuticals" and company "Oncodesign"
    Given a journey "j11" with origin "Shh" destination "Tou" content "Office Machine parts" and company "Shanghai Machine Tool Works Co.,Ltd."
    And a keyword "Tou"
    When searching through the journey database using the keyword
    Then the corresponding journey matching the destination-keyword is returned
    
    Scenario: Update a journeys current location
    Given a journey "j12" with origin "Mum" destination "Nav" content "Cotton" and company "East India Cotton Manufacturing Co., Limited"
    When the journeys current location is found
    Then the current location of the journey is returned to the custumor   
    
    Scenario: Update current location of a journey
    Given a journey "j13" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a new location "Phe"
    When the journeys current location is updated
    Then the journeys current location is updated in the journey database
    Then the journey "j13" is completed
    
    Scenario: Ending a journey
    Given a journey "j13" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a new location "Phe"
    When the journeys current location is updated
    And the journey "j13" is completed
    Then the journey is removed from the list of active journeys
    Then the journey is added to the list of ended journeys
    And the containers corresponding to the journey are stored in the warehouse
    
    Scenario: Assigning container from containerwarehouse
    Given Journey and container counter is set to zero, where the counter describes the name of the corresponding id's
    Given a journey "j14" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a new location "Cat"
    And the journeys current location is updated 
    And the journey "j14" is completed
    When a journey "j15" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    Then the container should be taken from containerwarehouse
    And the container for the old journey is reused for the new journey
    
    Scenario: Searching for container using a keyword of type content
    Given a journey "j16" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    Given a journey "j17" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    When searching for the container containing the content "Diamonds"
    Then a list of containers possessing this content is returned 
    
    Scenario: Searching for container using a keyword of type company
    Given a journey "j16" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    Given a journey "j17" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    When searching for the container containing the company "Riceland"
    Then a list of containers possessing this company is returned   
    
    Scenario: Searching for container using a keyword of type currentlocation
    Given a journey "j16" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    Given a journey "j17" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    When searching for the container containing the currentlocation "Gwa"
    Then a list of containers possessing this currentlocation is returned     
    
    Scenario: Searching for container using a keyword that no containers contain
    Given a journey "j16" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    Given a journey "j17" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    When searching for the container containing the content "guns"
    Then an empty list of containers will be returned   
    
    