This is a Maven-based project repository designed for automated testing. It includes:

```
* Test Scripts: Contains the automated test cases for different scenarios.
* Page Objects: Implements the Page Object Model (POM) design pattern for better maintainability
and reusability.
* Test Data: Utilizes data from sources like Excel for test scenarios.
```

Key Features:
```
* Hybrid Driven Framework: Combines both data-driven and design pattern-based approaches.
* Design Patterns: Utilizes Singleton and Page Object Model (POM) design patterns for better structure
and code management.
```

Technologies Used:
```
* Apache POI Library: For automating test cases involving Excel data.
* RestAssured Library: For automating API test cases using Java.
```


### Repo organization ###
This repository contains the following directories:

   ```
   |____src/main/java
   | |____constants
   | |____exceptions
   | |____pages
   | |____pojoModels
   | |____utils
   |
   |
   |____src/test/java
   | |____testScripts
   |
   |____resources
   | |____testData
   | |____testExecutor
   |
   ```

1. **testScripts** - Contains java test automation scripts to implement test steps for various test scenarios under 
excel and api application.
   Examples:
   ```
   |____testScripts
   | |____APIBaseClass.java
   | |____APITests.java
   | |____ExcelBaseClass.java
   | |____ExcelTests.java
   ```

2. **pages** - API Tests are designed based on the Page Object Model (POM) design pattern. Under this model, each endpoint 
has a corresponding `page class` containing `page methods` which perform operations on specific endpoint. 
Each endpoint has a dedicated page object `.java` file available under this directory. 
Every page class follows `Singleton` design pattern.
   Examples:
   ```
   |____pages
   | |____BasePage.java
   | |____FilmsPage.java
   | |____PeoplePage.java
   | |____PlanetsPage.java
   | |____ResidentsPage.java
   | |____VehiclesPage.java
   ```

3. **pojoModels** - package contains Plain Old Java Objects (POJOs) used for API request  and response models, providing a simple and structured way to handle data serialization and deserialization.
   Examples:
   ```
   |____pojoModels
   | |____Films.java
   | |____FilmsResponse.java
   | |____People.java
   | |____PeopleResponse.java
   | |____Planet.java
   | |____PlanetResponse.java
   | |____Residents.java
   | |____Vehicle.java
   | |____VehiclesResponse.java 
   ```

4. **Other directories**
   ```
   | Directory         | Description                                                          |
   | :---------------- | :------------------------------------------------------------------  |
   | utils             | contains utility methods for excel and API requests                  |                            |
   | constants         | contains data which is constant like filepaths, api endpoints        |
   | exceptions        | contains custom exceptions to be used for excel operations           | 
   | testdata          | contains excel sheets with sample test data                          |
   ```

### Getting Started ###

Following are the instructions to set up a development environment to develop, execute and maintain automated tests.

### Libraries Used in Framework ###

Following are the list of libraries used while building the framework:

   ```
   | Library Name      | Version  | Description                                                                                                 |
   | :---------------- | :------- | :-----------------------------------------------------------------------------------------------------------|
   | java              | 17.0.2   | Programming language                                                                                        |
   | testng            | 7.8.0    | Testing framework which supports test configured by annotations, data-driven testing, parametric tests, etc.|
   | apache poi        | 5.3.0    | Java API To Access Microsoft Format Files, used for reading excel sheet data                                |
   | restassured       | 5.5.0    | A Java library for testing and automating RESTful API interactions, making it easier to validate responses. |
   | jackson           | 2.18.2   | A popular library for parsing and binding JSON data to Java objects and vice versa.                         |
   | surefire          | 3.1.2    | A plugin for Maven to run unit tests and generate test reports, typically used with frameworks likeTestNG   |
   ```

### Prerequisites ###

1. Install IDE like IntelliJ IDEA/ Eclipse.
   
2. Install maven command line.

4. Install git on the system to perform git commands.

5. Download and setup java v17.0.2. Follow instructions on:

### Environment setup ###

1. Clone the code from repo
    - `https://github.com/VarshaTi97/AutomationFrameworkForAPIAndExcel.git`

2. Install dependencies
    - Install required dependencies.
      ```
      cd AutomationFrameworkForAPIAndExcel/
      mvn clean compile
      ```
3. Execute the testscripts
      ```
      mvn test
      ```
      or right-click test file and click on run
  
### Execution Report ###

- Execution report can be found inside the `target\surefire-reports` folder. Report file is `emailable-report.html` 

### Author ###

- Varsha Tiwari
