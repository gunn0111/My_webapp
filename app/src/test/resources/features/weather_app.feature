Feature: Weather App Navigation, City Search, and Settings

  # ---------------- Home Screen ----------------
  Scenario: Home screen shows welcome elements
    Given I am on the home screen

  Scenario: Navigate from Home to Cities screen
    Given I am on the home screen
    When I tap the Get Started button
    Then I should see the city search screen

  Scenario: Cannot tap Get Started from Cities screen
    Given I am on the cities screen
    When I tap the Get Started button
    Then I should see snackbar message "Cannot tap Get Started from cities"

  # ---------------- Cities Screen ----------------
  Scenario: Search valid city adds to list
    Given I am on the cities screen
    When I search for city "Bangalore"
    Then I should see "Bangalore" in the city list
    And I should see snackbar message "Bangalore added to list"

  Scenario: Search blank input shows error
    Given I am on the cities screen
    When I search for city ""
    Then I should see an error snackbar
    And I should see snackbar message "City not found or invalid input"

  Scenario: Search numeric input shows error
    Given I am on the cities screen
    When I search for city "123"
    Then I should see an error snackbar
    And I should see snackbar message "City not found or invalid input"

  Scenario: Search special characters shows error
    Given I am on the cities screen
    When I search for city "@Delhi!"
    Then I should see an error snackbar
    And I should see snackbar message "City not found or invalid input"

  Scenario: Search multiple valid cities
    Given I am on the cities screen
    When I search for multiple cities "Mumbai" and "Chennai"
    Then I should see both "Mumbai" and "Chennai" in the city list

  Scenario: Search a city that already exists
    Given I am on the cities screen
    When I search for city "Bangalore"
    And I search for city "Bangalore"
    Then I should see "Bangalore" in the city list
    And the city "Bangalore" should appear at the top of the city list

  Scenario: Add city after invalid search
    Given I am on the cities screen
    When I search for city "123"
    And I search for city "Hyderabad"
    Then I should see "Hyderabad" in the city list

  Scenario: City displays temperature and wind units correctly
    Given I am on the settings screen
    When I select temperature unit "F"
    And I select wind unit "MPS"
    Given I am on the cities screen
    When I search for city "Kolkata"
    Then the city "Kolkata" should display temperature in "F" and wind in "MPS"

  # For my Settings Screen
  Scenario: Settings screen default units
    Given I am on the settings screen
    Then I should see temperature unit "C" selected
    Then I should see wind unit "KPH" selected

  Scenario: Change temperature unit to Fahrenheit
    Given I am on the settings screen
    When I select temperature unit "F"
    Then I should see temperature unit "F" selected
    And I should see message "Fahrenheit selected"

  Scenario: Change temperature unit back to Celsius
    Given I am on the settings screen
    When I select temperature unit "C"
    Then I should see temperature unit "C" selected
    And I should see message "Celsius selected"

  Scenario: Change wind unit to m/s
    Given I am on the settings screen
    When I select wind unit "MPS"
    Then I should see wind unit "MPS" selected
    And I should see message "m/s selected"

  Scenario: Change wind unit back to km/h
    Given I am on the settings screen
    When I select wind unit "KPH"
    Then I should see wind unit "KPH" selected
    And I should see message "km/h selected"

  Scenario: Change both units simultaneously
    Given I am on the settings screen
    When I select temperature unit "F"
    And I select wind unit "MPS"
    Then I should see temperature unit "F" selected
    And I should see wind unit "MPS" selected
    And I should see message "m/s selected"

  # Navigation between screens and settings persistence
  Scenario: Navigate Home → Cities → Settings
    Given I am on the home screen
    When I tap the Get Started button
    Then I should see the city search screen
    Given I am on the settings screen
    When I select temperature unit "F"
    Then I should see temperature unit "F" selected

  Scenario: Navigate Cities → Home → Settings
    Given I am on the cities screen
    When I tap the Get Started button
    Then I should see snackbar message "Cannot tap Get Started from cities"
    Given I am on the home screen
    When I tap the Get Started button
    Then I should see the city search screen
    Given I am on the settings screen
    When I select wind unit "MPS"
    Then I should see wind unit "MPS" selected

# -- Additional scenarios for edge cases and combined flows
  Scenario: Add three cities including duplicate
    Given I am on the cities screen
    When I search for city "Delhi"
    And I search for city "Mumbai"
    And I search for city "Delhi"
    Then the city "Delhi" should appear at the top of the city list
    And I should see "Mumbai" in the city list

  Scenario: Search city with mixed case letters
    Given I am on the cities screen
    When I search for city "bAnGalore"
    Then I should see "bAnGalore" in the city list

  Scenario: Add city, then empty string input
    Given I am on the cities screen
    When I search for city "Lucknow"
    And I search for city ""
    Then the city "Lucknow" should appear at the top of the city list
    And I should see snackbar message "City not found or invalid input"

  Scenario: Add city with space in name
    Given I am on the cities screen
    When I search for city "New Delhi"
    Then I should see "New Delhi" in the city list

  Scenario: Add city, then special characters input
    Given I am on the cities screen
    When I search for city "Ch@nnai"
    Then the city list should not contain "Ch@nnai"
    And I should see snackbar message "City not found or invalid input"

  Scenario: Add cities, duplicate and check top order
    Given I am on the cities screen
    When I search for city "Kolkata"
    And I search for city "Chennai"
    And I search for city "Kolkata"
    Then the city "Kolkata" should appear at the top of the city list
    And the city "Chennai" should still be in the list

  Scenario: Add multiple cities, change temp only
    Given I am on the cities screen
    When I search for multiple cities "Pune" and "Surat"
    Given I am on the settings screen
    When I select temperature unit "F"
    Given I am on the cities screen
    Then the city "Pune" should display temperature in "F" and wind in "KPH"
    And the city "Surat" should display temperature in "F" and wind in "KPH"

  Scenario: Add multiple cities, change wind only
    Given I am on the cities screen
    When I search for multiple cities "Patna" and "Bhubaneswar"
    Given I am on the settings screen
    When I select wind unit "MPS"
    Given I am on the cities screen
    Then the city "Patna" should display temperature in "C" and wind in "MPS"
    And the city "Bhubaneswar" should display temperature in "C" and wind in "MPS"

  Scenario: Add city, change both units, search duplicate
    Given I am on the cities screen
    When I search for city "Kanpur"
    Given I am on the settings screen
    When I select temperature unit "F"
    And I select wind unit "MPS"
    Given I am on the cities screen
    When I search for city "Kanpur"
    Then the city "Kanpur" should appear at the top of the city list
    And the city "Kanpur" should display temperature in "F" and wind in "MPS"
