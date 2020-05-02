Feature: Journey Management
  This feature will handle all container registrations for future journeys.

  Scenario: Create a journey
    Given an origin "cph" destination "rio" content "bananas" and company "My Protein"
    When the journey "j1" is registered in the database
    Then a unique journey id is created
    And the origin and destination for the journey is correct
    And the content and company for the journey is correct
    And the journey exists in the database

  Scenario: Create multiple journeys
    Given an origin "cph" destination "rio" content "bananas" and company "gls"
    And a second origin "ber" destination "mia" content "covid19" and company "novo"
    When the journey "j1" is registered in the database
    And the second journey "j2" is registered in the database
    Then the two journeys exists in the database
    
  Scenario: Create multiple similair journeys
    Given an origin "dys" destination "rio" content "bananas" and company "gls"
    And an origin "dys" destination "rio" content "ice cream" and company "My Protein"
    When the journey "j1" is registered in the database
    And the journey "j2" is registered in the database
    Then only a single journey is created
    And the journey has two containers that exists in the database

  Scenario: Search for a journey in a database using a specific content that no journey is listed under
    Given a journey "j1" with origin "Dub" destination "Cat" content "canned tuna" and company "Ocean More Foods Co., Limited"
    And a journey "j2" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a keyword "pasta" describing a journey
    When searching through the journeys in the database using the keyword
    Then an empty list of journeys in the database matching the keyword is returned

  Scenario: Search for a journey in a database using a specific origin
    Given a journey "j1" with origin "Mum" destination "Nav" content "Cotton" and company "East India Cotton Manufacturing Co., Limited"
    And a journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Mum" describing a journey
    When searching through the journeys in the database using the keyword
    Then a list of one journey matching the specific origin "Mum" is returned

  Scenario: Search for a journey in a database using a specific destination
    Given a journey "j1" with origin "Dij" destination "Tia" content "Pharmaceuticals" and company "Oncodesign"
    And a journey "j2" with origin "Shh" destination "Tou" content "Office Machine parts" and company "Shanghai Machine Tool Works Co.,Ltd."
    And a keyword "Tou" describing a journey
    When searching through the journeys in the database using the keyword
    Then a list of one journey matching the specific destination "Tou" is returned
    
    Scenario: Find a journeys current location
    Given a journey "j1" with origin "Mum" destination "Nav" content "Cotton" and company "East India Cotton Manufacturing Co., Limited"
    When the journeys current location is found
    Then the current location of the journey "Mum" is returned   
    
    Scenario: Update a journeys current location
    Given a journey "j1" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a new location "Phe"
    When the journeys current location is updated
    Then the journeys new location is "Phe"
    And the journey "j1" is completed
    
    Scenario: End a journey
    Given a journey "j1" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a new location "Phe"
    When the journeys current location is updated
    And the journey "j1" is completed
    Then the journey is removed from the list of active journeys
    And the journey is added to the list of ended journeys
    And the journeys assigned containers are stored in the container warehouse
    
    Scenario: Assign container from container warehouse
    Given a journey "j1" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a new location "Cat"
    And the journeys current location is updated 
    And the journey "j1" is completed
    When a new journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    Then the journeys assigned container should be taken from the container warehouse
    And the same container is used for both journeys
    
    Scenario: Search for a container in a database using a specific content
    Given a journey "j1" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Diamonds" describing a container
    When searching for the container using the keyword
    Then a list of containers matching the specific content is returned 
    
    Scenario: Search for a container in a database using a specific company
    Given a journey "j1" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Riceland" describing a container
    When searching for the container using the keyword
    Then a list of containers matching the specific company is returned   
    
    Scenario: Search for a container in a database using a specific location
    Given a journey "j1" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Gwa" describing a container
    When searching for the container using the keyword
    Then a list of containers matching the specfic location is returned     
    
    Scenario: Search for a container in a database using a specific origin that no containers are listed under
    Given a journey "j1" with origin "Edb" destination "Cat" content "rice" and company "Riceland"
    And a journey "j2" with origin "Gwa" destination "Phe" content "Diamonds" and company "Laxmi Diamond Pvt. Ltd"
    And a keyword "Lon" describing a container
    When searching for the container using the keyword
    Then an empty list of containers matching the keyword is returned   