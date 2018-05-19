  # TODO 04 (Let's read: Our feature file)
  # The feature file is used to describe the different scenarios we want to test to validate our system.
  # In this case, we want to validate our basket. Whether we're able to create an empty basket, fill it with Tapas, and calculate the total cost.
  # Be careful, the scenarios are case-sensitive

Feature: A new empty basket can be created and filled with Tapas

  # TODO 05 Uncomment the first scenario, and try running the CucumberTest.java file as a Test (it will be ignored, so let's correct this in the next step!)
  #
  Scenario: Client creates a new Basket, and verifies it's empty
    When the user creates a new Basket
    Then the total number of items in the Basket with id 1 equals 0

  # TODO 07 Add a scenario where we verify whether the Client can add a Tapas to his Basket.
  # We want our test to verify that:
  # When the user creates a new Basket
  # And the user adds 5 Tapas with id 3 to the Basket with id 1
  # Then the number of items with id 3 in the basket with id 1 equals 5
  # And the total number of items in the basket with id 1 equals 5
  #
  # In your step definitions file you can annotate steps with @When and @Then like you would for other steps but also use them with "and" in your feature files
  Scenario: Client can add a Tapas to his Basket
    When the user creates a new Basket
    And the user adds 5 Tapas with id 3 to the Basket with id 1
    Then the number of items with id 3 in the Basket with id 1 equals 5
    And the total number of items in the Basket with id 1 equals 5

  # TODO 08 Add a scenario where we add several Tapas to the basket, and try to count the total number of items in the Basket
  # Hint: it's possible the API makes it a bit harder to verify the total, this means you might need to use a little bit of extra Java code
  Scenario: Client can add several Tapas to his Basket
    When the user creates a new Basket
    And the user adds 5 Tapas with id 3 to the Basket with id 1
    And the user adds 3 Tapas with id 5 to the Basket with id 1
    Then the number of items with id 3 in the Basket with id 1 equals 5
    And the number of items with id 5 in the Basket with id 1 equals 3
    And the total number of items in the Basket with id 1 equals 8

  # TODO 09 (EXTRA) Create a scenario where you add a couple of Tapas to the Basket and Calculate the Total Cost of it
  Scenario: Client can Calculate a Total Cost for all the Tapas in his Basket
    When the user creates a new Basket
    And the user adds 5 Tapas with id 3 to the Basket with id 1
    And the user adds 3 Tapas with id 5 to the Basket with id 1
    Then the Total Cost for all the Tapas in the Basket with id 1 equals 24

  # TODO 10 (EXTRA) Finally let's try to add a scenario where we add and remove several Tapas and validate the total number of Tapas in the Basket and costs in between
  # Hint: You can add a new "When" after a "Then" to do intermittent validations
  Scenario: Client can Add and then Remove Tapas from his Basket
    When the user creates a new Basket
    And the user adds 5 Tapas with id 3 to the Basket with id 1
    And the user adds 3 Tapas with id 5 to the Basket with id 1
    Then the Total Cost for all the Tapas in the Basket with id 1 equals 24
    When the user adds -3 Tapas with id 3 to the Basket with id 1
    Then the Total Cost for all the Tapas in the Basket with id 1 equals 15