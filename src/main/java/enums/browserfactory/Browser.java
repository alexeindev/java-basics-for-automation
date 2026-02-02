package enums.browserfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;

public enum Browser {
    CHROME, FIREFOX, SAFARI;


    public WebDriver createDriver() {
        WebDriver driver = switch (this) {
            case CHROME -> new ChromeDriver(getChromeOptions());
            case FIREFOX -> new FirefoxDriver(getFirefoxOptions());
            case SAFARI -> new SafariDriver(getSafariOptions());
        };
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static Browser fromString(String browserName) {
        //Should be static to be used without an instance yet
        return switch (browserName.toLowerCase()) {
            case "chrome" -> Browser.CHROME;
            case "firefox" -> Browser.FIREFOX;
            case "safari" -> Browser.SAFARI;
            default -> throw new IllegalArgumentException("Unsupported browser: '" + browserName +
                    "'. Supported browsers: chrome, firefox, edge");
        };
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--start-maximized");
        return options;
    }

    private FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webdriver.enabled", false);
        options.addArguments("--width=1920");
        return options;
    }

    private SafariOptions getSafariOptions() {
        SafariOptions options = new SafariOptions();
        options.setAutomaticInspection(false);
        options.setUseTechnologyPreview(false);
        return options;
    }

}
