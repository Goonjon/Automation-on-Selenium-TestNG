# Automation on Selenium-TestNG
### Project Summary: This project automates user registration, admin validation, and cost management for a finance website using Selenium and TestNG. It includes test cases for various user registration scenarios, updating profile images, and adding expenditures via CSV. The project also verifies data integrity with JSON assertions, utilizes Page Object Model (POM) structure, and supports smoke and regression test suites with Allure report generation for detailed test analysis.

### Technologies I have used: 
- Language: Java
- Build System: Gradle
- Automation tool and framework: Selenium and TestNG
- Data manipulation: Simple JSON and CSV Parser

### Project Flow:
- Register user with all fields, only mandatory fields, and without any mandatory fields, then assert and save to JSON.
- Admin login from terminal, verify last registered user on the dashboard, print and assert details from JSON.
- Log in as the last user, update profile image.
- Add expenditure using a CSV file with 5 entries, loop 5 times.
- Print and assert total cost from CSV data.
- Search an item, verify cost matches item's price.
- Create smoke and regression suites; smoke runs cases 5, 6, 7.
- Generate Allure report for regression suite.
- Follow POM pattern for project structure.

### How to run?
1. Open IntelliJ IDEA and select New Project.
2. Create a Java project and name it.
3. Open the project in IntelliJ: File > Open > Select and expand folder > Open as project.
4. To run the test suites, use the following commands:
5. For regression suite: ```gradle clean test -PsuiteName="regressionSuite.xml"```
6. For smoke suite: ```gradle clean test -PsuiteName="smokeSuite.xml"```
7. To generate the Allure report, run: ```allure generate allure-results --clean```
                                       ```allure serve allure-results```

### Screenshots Of the Allure Report:
<img width="960" alt="Screenshot 2024-10-20 101955" src="https://github.com/user-attachments/assets/5b917e6c-0ff4-466d-adc8-85be52c976e4">
<img width="960" alt="Screenshot 2024-10-20 102046" src="https://github.com/user-attachments/assets/8511b387-ad40-4d5d-b058-daeb613beaec">


### Video Record of DailyFinance Automation
### 


