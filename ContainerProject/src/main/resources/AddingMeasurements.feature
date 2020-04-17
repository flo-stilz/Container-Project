@tag
Feature: Adding Measurements to container

 @tag1
  Scenario: Successful addition of all measurement types
		Given a new j3 "ber" "mia" "covid19" "novo"
    And uniqueID is first time occurrence
    And temp 25 and hum 10 and pressure 5 
    When add all internal measurements to containers 
    Then transfer data from tempDataMap to dataMap
    Then Display confirmation message of added measurements
    