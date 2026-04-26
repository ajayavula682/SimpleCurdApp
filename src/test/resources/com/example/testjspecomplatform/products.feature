Feature: Product API Testing

  Background:
    * url baseUrl

  Scenario: Get all products
    Given path '/api/products'
    When method GET
    Then status 200
    And match response == '#array'

  Scenario: Create a new product and verify
    # Generate random name to avoid conflicts
    * def randomName = 'Test Product ' + java.util.UUID.randomUUID().toString()
    * def payload = { name: '#(randomName)', description: 'A product for testing', price: 99.99, quantity: 10, category: 'Testing' }
    
    Given path '/api/products'
    And request payload
    When method POST
    Then status 201
    And match response.id == '#notnull'
    And match response.name == randomName
    
    # Store the generated ID for later retrieval
    * def productId = response.id
    
    # Retrieve the created product
    Given path '/api/products', productId
    When method GET
    Then status 200
    And match response.id == productId
    And match response.name == randomName
    
    # Delete the created product to clean up
    Given path '/api/products', productId
    When method DELETE
    Then status 204
    
    # Verify deletion
    Given path '/api/products', productId
    When method GET
    Then status 404
