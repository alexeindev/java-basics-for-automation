package enums;

import enums.browserfactory.Browser;
import enums.browserfactory.BrowserFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BrowserFactoryTest {

    private WebDriver driver;

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ========== BROWSER ENUM TESTS ==========

    @Test
    public void testBrowserEnumCreatesDrivers() {
        driver = Browser.CHROME.createDriver();
        assertNotNull(driver, "Chrome driver should not be null");
        driver.quit();

        driver = Browser.FIREFOX.createDriver();
        assertNotNull(driver, "Firefox driver should not be null");
        driver.quit();
    }

    @Test
    public void testFromStringValidBrowsers() {
        assertEquals(Browser.fromString("chrome"), Browser.CHROME);
        assertEquals(Browser.fromString("CHROME"), Browser.CHROME);
        assertEquals(Browser.fromString("firefox"), Browser.FIREFOX);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFromStringInvalidBrowser() {
        Browser.fromString("edge");
    }

    // ========== BROWSER FACTORY TESTS ==========

    @Test
    public void testCreateInstanceWithParameter() {
        driver = BrowserFactory.createInstance(Browser.CHROME);

        assertNotNull(driver, "Driver should not be null");
        driver.get("https://www.google.com");
        assertTrue(driver.getTitle().contains("Google"));
    }

    @Test
    public void testCreateInstanceFromConfig() {
        // config.properties: browser.name=chrome
        driver = BrowserFactory.createInstanceFromConfig();

        assertNotNull(driver, "Driver from config should not be null");
        driver.get("https://www.google.com");
        assertTrue(driver.getTitle().contains("Google"));
    }

    @Test
    public void testMultipleBrowsersIndependently() {
        WebDriver chrome = BrowserFactory.createInstance(Browser.CHROME);
        WebDriver firefox = BrowserFactory.createInstance(Browser.FIREFOX);

        assertNotNull(chrome);
        assertNotNull(firefox);
        assertNotSame(chrome, firefox, "Should create independent instances");

        chrome.quit();
        firefox.quit();
        driver = null;
    }

    // ========== INTEGRATION TEST ==========

    @Test
    public void testCompleteWorkflow() {
        // String → Enum → Driver
        String browserName = "chrome";
        Browser browser = Browser.fromString(browserName);
        driver = BrowserFactory.createInstance(browser);

        driver.get("https://www.selenium.dev");
        assertTrue(driver.getTitle().contains("Selenium"));
    }
}
