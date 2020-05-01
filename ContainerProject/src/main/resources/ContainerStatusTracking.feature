Feature: Container Status Tracking
	This feature will handle the tracking functionality of internal-status measurements

  Scenario: Add internal-status measurements to a journeys container
		Given a journey "j1" with origin "ber" destination "mia" content "bricks" and company "novo"
    When adding temp 25 hum 10 and pressure 5 to the assigned containers of the journey "j1"
    Then the internal-status measurements are present for the assigned containers of the journey "j1" 
    And the internal-status measurements for the journeys container matches the values temp 25 hum 10 pressure 5
    
    Scenario: Add internal-status measurements to a journeys containers
    Given a journey "j1" with origin "ath" destination "mia" content "caps" and company "novo"
    And a journey "j2" with origin "ath" destination "mia" content "caps" and company "novo"
    And only a single journey is created
    When adding temp 28 hum 12 and pressure 4 to the assigned containers of the journey "j1"
    Then both containers contain the measurements temp 28 hum 12 and pressure 4
    
  Scenario: Add multiple internal-status measurements to a journeys container
    Given a journey "j1" with origin "ath" destination "mia" content "caps" and company "novo"
    When adding temp 28 hum 12 and pressure 4 to the assigned containers of the journey "j1"
    And readding temp 23 hum 11 and pressure 6 to the assigned containers of the journey "j1"
    Then both sets of internal-status measurements are present for the assigned containers of the journey "j1" 
    And both sets of internal-status measurements for the journeys containers matches the values temp 28 23 hum 12 11 pressure 4 6