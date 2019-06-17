# retailstore
Spring Boot Retail Store App 

# Problem Statement

Build the domain model only for a checkout counter in an online retail store that scans products and
generates an itemized bill.
The bill should also total the cost of all the products and the applicable sales tax for each product.
The total cost and total sales tax should be printed
Sales tax varies based on the type of products
- Category A products carry a levy of 10%
- Category B products carry a levy of 20%
- Category C products carry no levy

# Solution

1) Spring Boot App is created with exposed Rest Webservices 
2) Product Service are exposed for create/update/get product information
3) Billing Service is exposed which create Bill and add product information in Bills.
4) Logic is created in Service layer where Cost is calculated on the basis of product category
5) Swagger API/UI is available at Link http://localhost:8080/swagger-ui.html
6) Actuator is used available at http://localhost:8080/actuator
7) H2 Database is used for persisting product and Billing info
8) Test Cases are created
9) Important Links of application is available below.can import in Postman

https://www.getpostman.com/collections/86f1dc5808228e277876

