#Author: your.email@your.domain.com
#Keywords Summary :
#Feature: List of scenarios.
#Scenario: Business rule through list of steps with arguments.
#Given: Some precondition step
#When: Some key actions
#Then: To observe outcomes or validation
#And,But: To enumerate more Given,When,Then steps
#Scenario Outline: List of steps for data-driven as an Examples and <placeholder>
#Examples: Container for s table
#Background: List of steps run before each of the scenarios
#""" (Doc Strings)
#| (Data Tables)
#@ (Tags/Labels):To group Scenarios
#<> (placeholder)
#""
## (Comments)
#Sample Feature Definition Template
@tag
Feature: Client Management
  

  @tag1
  
  
  Scenario: Register a client
    Given a new client company name "banananass", address "place", email "mail2", contact person "jim"
    When client is registered
    Then client exists on database
    
	Scenario: update a client
    Given a new client company name "company", address "place", email "mail", contact person "name"
    When client is registered
    And client wants to update email to "ex@dtu.dk"
    And client wants to update adress to "dongleland"
    And client wants to update contact person to "jimmyjon"
    Then client exists on database
    And client has been updated
  
  
    Scenario: search a client
    Given a new client company name "banananass", address "place", email "mail", contact person "jim"
    And client is registered
    And a new client company name "coconuts", address "dtu", email "mail", contact person "log"
    And client is registered
    When string "coconuts" apears as client infornmation
    Then a list of possible clients is returned
    
    
  
     Scenario: search a client with other parameters 
     When string "place" apears as client infornmation
     When string "jon" apears as client infornmation
     When string "company" apears as client infornmation
    
    
    
  
  	
    
 