# To start the application
 mvn clean install
#Mock Api https://17d809a3-b340-4c37-9aed-9fbee30ab7af.mock.pstmn.io/v2/price
#H2 Db configuration:
   create folder to store data D:/ANISH KUMAR/testdb
   spring.datasource.url=jdbc:h2:file:D:/ANISH KUMAR/testdb
#store Product data
 Run to store store http://localhost:8002/myRetail/products/13860428
 product details from https://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
 and price from mock api  https://17d809a3-b340-4c37-9aed-9fbee30ab7af.mock.pstmn.io/v2/price into
 H2 db is used to Store the product information(productId, title,price and currency code) in product

#Myretail hosts four REST services:

#1) GET /products/{productId}**
   
  This Rest Service aggregates price information and product Title from external Target API and
  provides a JSON Response.
  
  **Sample:**
  
  **Request:** 
  GET   http://localhost:8002/myretail/products/13860428
  Content-Type: application/json
  
  **Response:**
  {
      "id": 13860428,
      "name": "The Big Lebowski (Blu-ray)",
      "current_price": {
          "value": "12.49",
          "currency_code": "USD"
      }
  }
#2)GET  /product/{productId}/price
  This Rest Service aggregates price information from mock api and product Title from external Target API and
    provides a JSON Response.
    mock api: https://17d809a3-b340-4c37-9aed-9fbee30ab7af.mock.pstmn.io/v2/price

**Sample:**

**Request:**
  GET  http://localhost:8002/myRetail/product/13860428/price

  Content-Type: application/json

  **Response:**
  {
      "id": 13860428,
      "name": "The Big Lebowski (Blu-ray)",
      "current_price": {
          "value": "80.49",
          "currency_code": "USD"
      }
  }

#3)POST  /products/{productId}/price
  This Rest Service aggregates price information from mock api and product Title from external Target API and
    provides a JSON Response and save into h2 db
    mock api: https://17d809a3-b340-4c37-9aed-9fbee30ab7af.mock.pstmn.io/v2/price
**Sample:**
**Request:**
  GET  http://localhost:8002/myRetail/products/13860428

  Content-Type: application/json

  **Response:**
  201 Created

#4) PUT /products/{productid}**
  
  This Rest Service is used to update the price of an existing product in H2 db

  **Sample:**
  POSt http://localhost:8002/myRetail/products/13860428
  Content-Type: application/json

  **Request:**
   {
       "id": 13860428,
       "name": "The Big Lebowski (Blu-ray)",
       "current_price": {
           "value": "100.00",
           "currency_code": "USD"
       }
   }
   **Response:**
   20 Ok

