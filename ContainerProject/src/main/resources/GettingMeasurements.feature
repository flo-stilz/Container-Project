@tag
Feature: Retrieve data to client
#Shows all data for all measurements being tracked once user inputs journey ID.

  @tag1
  Scenario: Successful retrieval of data 
			Given a new j3 "ber" "mia" "covid19" "novo"
      And a datapoint with temp 25 and hum 10 and pressure 5 
      When retrieve all internal measurements from containers
      Then display confirmation message of retrieved measurements
      
  Scenario: Failed retrieval of data due to invalid uniqueID
			Given a new j3 "ber" "mia" "covid19" "novo"
      And temp 25 and hum 10 and pressure 5 
      When retrieve all internal measurements from containers
      And display error message telling that no data is available for this uniqueID
      
  Scenario: Failed retrieval of data due to no data being present
			Given a new j3 "ber" "mia" "covid19" "novo"
      When retrieve all internal measurements from containers
      Then display error message telling that no data is available for this uniqueID
      
      
      
      
      