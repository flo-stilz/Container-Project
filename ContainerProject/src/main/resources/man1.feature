Feature: Client Management
	This feature will handle all processes related to client registration and administration

  Scenario: Register clients
    Given a client "client1" with company name "Matas" address "DRbyen" email "matas@administration.com" contact person "Signe Nielsen" password "signe"
    And a second client "client2" with company name "Miele" address "Dortmund" email "Miele@administration.du" contact person "Karl-Heinz Rummenigge" password "karl"
    When the client "client1" is registered in the client database
    And the second client "client2" is registered in the client database
    Then the clients are present in the client database
    
  Scenario: Checking generated clientid
    Given a client "client2" with company name "Oerstad" address "Fredericia" email "Oerstad@administration.com" contact person "Henrik Poulsen" password "henrik"
    And a second client "client4" with company name "Coloplast" address "Humlebaek" email "Coloplast@administration.com" contact person "Anette Mikkelsen" password "anette"
    When the client "client3" is registered in the client database
    And the second client "client4" is registered in the client database
    Then The generated clientids are unique
    
  Scenario: Register the same client twice
    Given a client "client5" with company name "Sats" address "Lyngby" email "Sats@administration.com" contact person "Robert Stork" password "robert"
    When the client "client5" is registered in the client database
    And the client "client5" is registered in the client database
    Then only one client is present in the client database

  Scenario: Update client information
     Given a client "client6" with company name "Sats" address "Lyngby" email "Sats@administration.com" contact person "Robert Stork" password "robert"
     When the client "client6" is registered in the client database
     And the client "client6" requests their email to be updated to "Sats1@administration.com"
     And the client "client6" requests their address to be updated to "Valby"
     And the client "client6" requests their contact person to be updated to "Filip Hemmingsen"
     Then the information of the client "client6" has been updated
     
  Scenario: Search for a client with a specific email email
     Given a client "client7" with company name "Tesla" address "Pao Alto, California" email "Tesla@administration.com" contact person "Travis Fimmel" password "travis"
     And a second client "client8" with company name "Dansk Metal" address "Aarhus" email "DanskMetal@administration.com" contact person "Morten Pedersen" password "morten"
     And the client "client7" is registered in the client database
     And the second client "client8" is registered in the client database
     When searching for client in client database using email "Tesla@administration.com"
     Then the corresponding client is returned
     
     
  Scenario: Search for a client that is not present in the clientlist using a specific address
     Given a client "client9" with company name "IBM" address "Armonk New York, United States" email "IBM@administration.com" contact person "Arvind Krishna" password "arvind"
     And a second client "client10" with company name "ICBC" address "Xicheng District, Beijing, China" email "ICBC@administration.com" contact person "Angela Erkel" password "angela"
     And the client "client9" is registered in the client database
     And the second client "client10" is registered in the client database
     When searching for client in client database using address "Pao Alto, California"
     Then an empty list of clients in the client database matching the keyword is returned
     
  
     
     
  	
    
 