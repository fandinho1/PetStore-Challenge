Feature: Pets CRUD
  As an user I want to find, create, update and delete pets
  
  Background: 
  	Given the user wanted to consume pets apis

  Scenario Outline: Get a pet by entering a correct id
    When he tries to get a pet by id '<id>'
    Then he should see the expected pet '<expectedPet>' for that id

    Examples: 
      | id | expectedPet   |
      |  3 | pet_id_3.json |
      |  7 | pet_id_7.json |

  Scenario Outline: Try to get a pet by sending an id that does not exist
    When he tries to get a pet by id '<id>'
    Then he should see the message "Pet not found"

    Examples: 
      | id  |
      | 300 |
      |  15 |

  Scenario Outline: Try to get a pet by sending an invalid value as id instead of integer
    When he tries to get a pet by id '<id>'
    Then he should see an error due to a bad request '<id>'

    Examples: 
      | id  |
      | a   |
      | ,   |
      | %   |
      |   0 |
      |  -1 |
      | 9.5 |

  Scenario Outline: Update an existing pet correctly
    When he tries to update a pet using correct data '<data>'
    Then he should see the pet is updated correctly

    Examples: 
      | data              |
      | update_pet_9.json |

  Scenario: Add a new pet to the store successfully
    When he adds a new pet using correct data 'data_to_add_new_pet.json'
    Then he should see the pet is added correctly

  Scenario: Delete a pet successfully
    When he adds a new pet using correct data 'data_to_add_new_pet.json'
    And he deletes the pet created
    Then he should see the pet is deleted correctly
