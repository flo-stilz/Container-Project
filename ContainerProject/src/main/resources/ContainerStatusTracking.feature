Feature: Container Status Tracking
	This feature will handle the tracking functionality of internal-status measurements

  Scenario: Addition of internal-status measurements
		Given a journey "j2" with origin "ber" destination "mia" content "bricks" and company "novo"
    When adding temp 25 hum 10 and pressure 5 to an assigned container of the journey "j2"
    Then the internal-status measurements are present for an assigned container of the journey "j2" 
    
  Scenario: Readdition of internal-status measurements
    Given a journey "j3" with origin "ath" destination "mia" content "caps" and company "novo"
    Given adding temp 28 hum 12 and pressure 4 to an assigned container of the journey "j3"
    When reading temp 23 hum 11 and pressure 6 to an assigned container of the journey "j3"
    Then both sets of internal-status measurements are present for an assigned container of the journey "j3" 
    
  Scenario: Update of internal-status measurements
    Given a journey "j4" with origin "ath" destination "mia" content "caps" and company "novo"
    When updating temp 24 hum 23 pressure 3 for "j4"
    Then measurements are present for container of "j4"
