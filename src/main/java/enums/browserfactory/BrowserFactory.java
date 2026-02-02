package enums.browserfactory;

import org.openqa.selenium.WebDriver;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class BrowserFactory {

    private BrowserFactory() {
        // Private constructor to prevent installation
        throw new UnsupportedOperationException(
                "BrowserFactory is a utility class and cannot be instantiated"
        );
    }

    public static WebDriver createInstance(Browser browser){
        return browser.createDriver();
    }

    public static WebDriver createInstanceFromConfig(){
        Browser browser = getBrowserNameFromProperties();
        return browser.createDriver();
    }

    private static Browser getBrowserNameFromProperties() {
        try {
            ResourceBundle browserName = ResourceBundle.getBundle("config");
            return Browser.fromString(browserName.getString("browser.name"));
        } catch (MissingResourceException e) {
            throw new IllegalStateException(
                    "Property 'browser.name' not found in config.properties. " +
                            "Please add: browser.name=chrome", e
            );
        }
    }

}
