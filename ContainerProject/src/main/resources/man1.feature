Feature: Client Management
	This feature will handle all processes related to client registration and administration

  Scenario: Register clients
    Given a client "client1" with company name "Matas" address "DRbyen" email "matas@administration.com" contact person "Signe Nielsen" password "signe"
    And a second client "client2" with company name "Miele" address "Dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    When the client "client1" is registered in the database
    And the second client "client2" is registered in the database
    Then the two clients are present in the database
    
  Scenario: Check generated clientids are unique
    Given a client "client1" with company name "Oerstad" address "Fredericia" email "Oerstad@administration.com" contact person "Henrik Poulsen" password "henrik"
    And a second client "client2" with company name "Coloplast" address "Humlebaek" email "Coloplast@administration.com" contact person "Anette Mikkelsen" password "anette"
    When the client "client1" is registered in the database
    And the second client "client2" is registered in the database
    Then the clients generated clientids are unique
    
  Scenario: Register the same client twice
    Given a client "client1" with company name "Sats" address "Lyngby" email "Sats@administration.com" contact person "Robert Stork" password "robert"
    When the client "client1" is registered in the database
    And the client "client1" is registered in the database
    Then only one client is present in the database

  Scenario: Update client information
     Given a client "client1" with company name "Sats" address "Lyngby" email "Sats@administration.com" contact person "Robert Stork" password "robert"
     When the client "client1" is registered in the database
     And the client "client1" requests their email to be updated to "Sats1@administration.com"
     And the client "client1" requests their address to be updated to "Valby"
     And the client "client1" requests their contact person to be updated to "Filip Hemmingsen"
     Then the information of the client "client1" has been updated
     
  Scenario: Search for a client in a database multiple times using different specific emails
     Given a client "client1" with company name "Tesla" address "Pao Alto, California" email "Tesla@administration.com" contact person "Travis Fimmel" password "travis"
     And a second client "client2" with company name "Dansk Metal" address "Aarhus" email "DanskMetal@administration.com" contact person "Morten Pedersen" password "morten"
     And the client "client1" is registered in the database
     And the second client "client2" is registered in the database
     And an email "Tesla@administration.com" to search for
     And searching for a client in the database using the email
     And  a list of one client matching the specific email "Tesla@administration.com" is returned
     And an email "DanskMetal@administration.com" to search for
     When searching for a client in the database using the email
     Then a list of one client matching the specific email "DanskMetal@administration.com" is returned
     
  Scenario: Search for a client in a database using a specific address that is no client is listed under
     Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Arvind Krishna" password "arvind"
     And a second client "client2" with company name "ICBC" address "Xicheng District, Beijing, China" email "ICBC@administration.com" contact person "Angela Erkel" password "angela"
     And the client "client1" is registered in the database
     And the second client "client2" is registered in the database
     And an address "Pao Alto, California" to search for
     When searching for client in the database using the address
     Then an empty list of clients matching the keyword is returned
     
  Scenario: Validate client with valid clientdetails
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Arvind Krishna" password "arvind"
    When checking if the client "client1" fulfills the requirements for registration
    Then the client "client1" is registered and added to the list of clients
     
  Scenario: Validate client with invalid password
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Arvind Krishna" password "arv"
    When checking if the client "client1" fulfills the requirements for registration
    Then the client "client1" is not registered due to invalid password
    
  Scenario: Check type of login for a client
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Arvind Krishna" password "arvin"
    And the client "client1" is registered in the database
    When checking the type of login for the clients details
    Then the login of the client is of type client
    
  Scenario: Check type of login for an admin
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "admin" password "12345"
    And the client "client1" is registered in the database
    When checking the type of login for the clients details
    Then the login of the client is of type admin 
    
  Scenario: Check type of login for a client with invalid name and valid password
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Greg Gallahger" password "gregg"
    When checking the type of login for the clients information
    Then the login of the client is not valid
      
  Scenario: Check type of login for a client with invalid username and invalid password
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Greg Gallahger" password "gregg"
    And the client "client1" is registered in the database
    When checking the type of login for the clients data
    Then the login of the client is invalid 
    
  Scenario: Check for a clients list of past and present journeys in the database
    Given a client "client1" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Greg Gallahger" password "gregg"
    And the client "client1" is registered in the database
    And a journey "j1" with origin "Dij" destination "Tia" content "Pharmaceuticals" and company "IBM"
    And checking the type of login for the clients details
    When searching for all journeys that are listed under the same company as the client
    Then a set of journeys associated to the client are returned
   
 Scenario: Check for a clients empty list of past and present journeys in the database
    Given a client "client1" with company name "Lyft" address "Armonk New York, United States" email "IBM@administration.com" contact person "Greg Gallahger" password "gregg"
    And the client "client1" is registered in the database
    And a journey "j1" with origin "Dij" destination "Tia" content "Pharmaceuticals" and company "IBM"
    And checking the type of login for the clients details
    When searching for all journeys that are listed under the same company as the client
    Then an empty set of journeys associated to the client is returned