package pomtest.bases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

import pomtest.com.common.ultilities.Log;

public class BaseSetUpSauceLabs {
    public WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public static final String USERNAME = "oauth-jenbiminh-1ed86";
    public static final String ACCESS_KEY = "3c6ad2c2-69c6-48f5-8e88-1b9054f51c24";
    public static final String URL = "https://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

    @BeforeClass
    private void setDriver(String browserType, String platform, String appURL) throws MalformedURLException {
        switch (browserType) {
            case "chrome" -> driver = initChromeDriver(platform, appURL);
            case "firefox" -> driver = initFireFoxDriver(platform, appURL);
            default -> {
                Log.error("Browser: " + browserType);
                driver = initChromeDriver(platform, appURL);
            }
        }
    }

    private WebDriver initFireFoxDriver(String platform, String appURL) throws MalformedURLException {
        Log.info("Launching FireFox browser... ");
        FirefoxOptions options = new FirefoxOptions();
        options.setPlatformName(platform);
        options.setBrowserVersion("latest");
        driver = new RemoteWebDriver(new URL(URL), options);
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    private WebDriver initChromeDriver(String platform, String appURL) throws MalformedURLException {
        Log.info("Launching Chrome browser... ");
        ChromeOptions options = new ChromeOptions();
        options.setPlatformName(platform);
        options.setBrowserVersion("latest");
        driver = new RemoteWebDriver(new URL(URL), options);
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    @Parameters({"browserType", "platform", "appURL"})
    @BeforeClass
    public void initializeTestBaseSetUp(String browserType, String platform, String appURL)  {
            try {
                setDriver(browserType, platform, appURL);
            } catch (Exception e) {
                Log.error("Error..." + Arrays.toString(e.getStackTrace()));
            }
    }

    @AfterClass
    public void tearDown() throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }
}
