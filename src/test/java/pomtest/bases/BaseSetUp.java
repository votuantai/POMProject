package pomtest.bases;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pomtest.com.common.ultilities.Log;

import java.time.Duration;
import java.util.Arrays;



public class BaseSetUp {
    private WebDriver driver;

    private final String driverPath = "src/test/resources/drivers/";
    public WebDriver getDriver() {
        return driver;
    }

    @BeforeClass
    private void setDriver(String browserType, String appURL) {
        switch (browserType) {
            case "chrome" -> driver = initChromeDriver(appURL);
            case "firefox" -> driver = initFireFoxDriver(appURL);
            default -> {
                Log.error("Browser: " + browserType);
                driver = initFireFoxDriver(appURL);
            }
        }
    }

    private WebDriver initChromeDriver(String appURL) {
        Log.info("Launching Chrome browser... ");
        System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    private WebDriver initFireFoxDriver(String appURL) {
        Log.info("Launching FireFox browser... ");
        System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.navigate().to(appURL);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        return driver;
    }

    @Parameters({"browserType", "appURL"})
    @BeforeClass
    public void initializeTestBaseSetUp(String browserType, String appURL) {
        try {
            setDriver(browserType, appURL);
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
