# SauceDemo UI Automation

*GitHub* - [etoile-venus/SaucedemoUIAutomation](https://github.com/etoile-venus/SaucedemoUIAutomation)

**End-to-End (E2E) test automation framework** for the [SauceDemo](https://www.saucedemo.com/) web application.  
Built with Java, Selenium WebDriver, and TestNG following the Page Object Model design pattern.

## Built With

![Java](https://img.shields.io/badge/Java-%23b07219?style=for-the-badge&logo=java&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-%2343B02A?style=for-the-badge&logo=selenium&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-%236DB33F?style=for-the-badge&logo=testng&logoColor=white)
![Apache POI](https://img.shields.io/badge/Apache_POI-%23D22128?style=for-the-badge&logo=apache&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-%23C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![POM](https://img.shields.io/badge/Page--Object--Model-%2368655F?style=for-the-badge)
![Git](https://img.shields.io/badge/Git-%23F1502F?style=for-the-badge&logo=git&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ_IDEA-%23000000?style=for-the-badge&logo=intellij-idea&logoColor=white)

## Project Setup

1. **Clone the repository:**
```bash
git clone https://github.com/etoile-venus/SaucedemoUIAutomation.git
```
2. **Build the project and download dependencies:**
Make sure you have Maven installed. Then, run:
```bash
mvn clean install
```
3. **Run tests:**
You can run tests directly from IntelliJ IDEA by running the test classes or test suite.
Alternatively, run tests from the command line with:
```bash
mvn test
```
4. **Java version:**
This project is compatible with the latest Java version supported by IntelliJ IDEA (e.g., Java 17 or newer).
5. **WebDriver setup:**
WebDriver binaries are managed automatically via WebDriverManager in the BaseTest class.
If needed, you can switch to manual driver setup by commenting/uncommenting the relevant lines in BaseTest.

## Project Structure

```bash
SaucedemoUIAutomation/
├── src/
│   ├── main/
│   │   ├── java/            # Main application code (if any, typically for utilities not directly tested)
│   │   └── resources/       # Main application resources
│   └── test/
│       ├── java/
│       │   └── saucedemo/
│       │       ├── asserts/       # Custom assertion classes extending TestNG's assertions for Page Objects (e.g., CartAssert.java)
│       │       ├── base/          # Base classes for pages (BasePage) and tests (BaseTest, WebDriver setup)
│       │       ├── components/    # Reusable UI components (e.g., CMenu for navigation, CItem for product cards)
│       │       ├── data/          # Data Transfer Objects (DTOs) and TestNG Data Providers
│       │       ├── pages/         # Page Object Model classes representing different web pages (e.g., LoginPage.java, HomePage.java)
│       │       ├── tests/         # TestNG test classes containing test methods (e.g., LoginTest.java, CartTest.java)
│       │       └── utilities/     # Helper utilities (e.g., ExcelReader, Route definitions, Screenshot capture)
│       └── resources/
│           ├── data/          # External test data files (e.g., Excel sheets for user credentials)
│           └── testng/        # TestNG XML suite configuration files (e.g., smoke-test.xml, regression-test.xml)
```
## How to Run Tests

### From IntelliJ IDEA

- Open the project in IntelliJ IDEA.
- Navigate to the `tests` package under `src/test/java/saucedemo/tests`.
- Right-click on a test class or package and select **Run** to execute the tests.
- You can also run the TestNG XML suite files located in `src/test/resources/testng` by right-clicking the XML file and selecting **Run**.

### From Command Line

Make sure Maven is installed and added to your system PATH.

- Navigate to the project root directory.
- Run the following command to execute all tests:

```bash
mvn test
```
To run specific TestNG suite files, use:

```bash
mvn test -DsuiteXmlFile=src/test/resources/testng/regression-test.xml
```

## License

This project is open-source and freely available for use, modification, and distribution without restrictions.

## Author
**Danica Bijeljanin**

Feel free to connect with me:
- **LinkedIn:** [linkedin.com/in/danicabijeljanin/](https://linkedin.com/in/danicabijeljanin/)  
- **GitHub:** [github.com/etoile-venus](https://github.com/etoile-venus)
