This application was built using Netbeans IDE 8.2. The Java JDK 1.8 platform was used, however, the project has been verified to work with OpenJDK8.
The project is built with Apache ANT.

The project uses JPA for the persistence layer and uses Jersey as the restful framework.


Resfull API calls:

@GET http://<App-server>/FamousQuotes/webresources/quotes returns: json/xml
@POST http://<App-Server>/FamousQuotes/webresources/quotes return: 202 (accepted) or 204 (No content)

Sample Objects service calls:

@GET http://localhost:8080/FamousQuotes/webresources/quotes/sample/quote returns: 200 and payload is Quote Object
@GET http://localhost:8080/FamousQuotes/webresources/quotes/sample/quotes returns 200 and payload is Qoutes Object
