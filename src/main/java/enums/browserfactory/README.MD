

# 🌐 Browser Factory with Enum Pattern

Build a type-safe browser factory using enums to manage WebDriver instances with browser-specific capabilities and configurations.

## 🎯 Learning Objectives

- Implement factory pattern with enums for WebDriver instantiation
- Design type-safe browser configuration systems with custom capabilities
- Build cross-browser test infrastructure supporting local and remote execution
- Apply enum methods to encapsulate browser-specific initialization logic

## 💡 Why This Matters

Browser factories are the foundation of any Selenium framework. Without a centralized factory, browser initialization code becomes duplicated across tests, configuration management becomes inconsistent, and adding new browsers requires changes throughout the codebase. Industry frameworks use enum-based factories to provide compile-time safety, eliminate string-based browser selection errors, and maintain a single source of truth for browser configuration.

## 📋 Technical Requirements

1. Create a `Browser` enum with the following constants: `CHROME`, `FIREFOX`, `EDGE`

2. Each enum constant must implement:
    - A `createDriver()` method that returns a configured `WebDriver` instance
    - A `getOptions()` method that returns browser-specific options (`ChromeOptions`, `FirefoxOptions`, `EdgeOptions`)
    - Common browser configurations: maximize window, set implicit wait to 10 seconds

3. Browser-specific configurations in `getOptions()`:
    - **Chrome**: Add arguments `--disable-blink-features=AutomationControlled`, `--start-maximized`
    - **Firefox**: Set preference `dom.webdriver.enabled` to `false`, add argument `--width=1920`
    - **Edge**: Add arguments `--inprivate`, `--start-maximized`

4. Create a `BrowserFactory` class with:
    - Static method `createInstance(Browser browser)` returning `WebDriver`
    - Support for reading browser choice from properties file as fallback
    - Property file location: `src/test/resources/config/browser.properties`
    - Property key: `browser.type` (values: chrome, firefox, edge - case insensitive)

5. Implement a `fromString(String browserName)` method in the `Browser` enum:
    - Converts string to corresponding enum constant
    - Case-insensitive matching
    - Throws `IllegalArgumentException` with clear message for unsupported browsers

6. Add WebDriverManager integration:
    - Automatically manage browser drivers using WebDriverManager
    - Call appropriate setup method before driver instantiation
    - Example: `WebDriverManager.chromedriver().setup()`

## ✅ Acceptance Criteria

1. All three browsers (`CHROME`, `FIREFOX`, `EDGE`) are implemented as enum constants with working `createDriver()` methods

2. Each browser successfully launches with its specific configurations applied:
    - Chrome opens maximized without automation flags
    - Firefox disables webdriver detection and sets custom width
    - Edge opens in InPrivate mode maximized

3. `BrowserFactory.createInstance()` correctly instantiates drivers based on enum input

4. String-to-enum conversion works for all valid inputs (case-insensitive):
    - "chrome", "CHROME", "Chrome" → `Browser.CHROME`
    - Invalid input throws descriptive exception listing valid options

5. Properties file is correctly loaded and browser type is read:
    - Missing property key uses sensible default (CHROME)
    - Invalid property value throws exception with helpful message

6. WebDriverManager successfully downloads and configures drivers automatically without manual driver management

7. Test class demonstrates:
    - Direct enum usage: `Browser.CHROME.createDriver()`
    - Factory usage: `BrowserFactory.createInstance(Browser.FIREFOX)`
    - String-based creation: `Browser.fromString("edge").createDriver()`
    - Property-based creation from config file

8. All drivers properly quit after test execution without resource leaks

## 📁 Project Structure

```
browser-factory-enum/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── framework/
│   │               ├── enums/
│   │               │   └── Browser.java
│   │               ├── factory/
│   │               │   └── BrowserFactory.java
│   │               └── config/
│   │                   └── ConfigReader.java
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── tests/
│       │           └── BrowserFactoryTest.java
│       └── resources/
│           └── config/
│               └── browser.properties
└── pom.xml
```

## 📦 Dependencies

```xml
<dependencies>
    <!-- Selenium WebDriver -->
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>4.27.0</version>
    </dependency>

    <!-- WebDriverManager -->
    <dependency>
        <groupId>io.github.bonigarcia</groupId>
        <artifactId>webdrivermanager</artifactId>
        <version>5.10.0</version>
    </dependency>

    <!-- TestNG -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 🔗 References

- https://github.com/eliasnogueira/selenium-java-lean-test-architecture
- https://github.com/anhtester/AutomationFrameworkSelenium
- https://github.com/Tahanima/selenium-java-test-automation-architecture
- https://bonigarcia.dev/webdrivermanager/