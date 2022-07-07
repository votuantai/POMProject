package pomtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pomtest.bases.SupportFunction;
import java.time.Duration;

import static java.lang.Thread.sleep;
import static org.testng.Assert.*;


public class LogInPage {

    private final WebDriver driver;
    private final SupportFunction spFunction;
    private final By usernameInput = By.xpath("//input[@id='iusername']");
    private final By passwordInput = By.xpath("//input[@id='ipassword']");
    private final By loginBtn = By.xpath("//span[@class='ladda-label']");
    private final By msgToast = By.xpath("//div[@class='toast-message']");
    private final By isDashBoard = By.xpath("//body[contains(@class,'modern-layout')]");
    public LogInPage(WebDriver driver) {
        this.driver = driver;
        spFunction = new SupportFunction(driver);
    }

    public void login(String username, String password) {
        String expectedUser = "admin01";
        String expectedPass = "123456";
        spFunction.setText(usernameInput, username);
        spFunction.setText(passwordInput, password);
        if(username.equals(expectedUser) == false || password.equals(expectedPass) == false) {
            testToast(username, password);
        } else {
            try {
                sleep(1000);
                spFunction.clickElement(loginBtn);
                Assert.assertTrue(driver.findElement(isDashBoard).isDisplayed());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void testToast(String username, String password) {
        spFunction.clickElement(loginBtn);
        if(username.isEmpty()) {
            Assert.assertTrue(spFunction.verifyMassage(msgToast, "The username field is required."));
        } else if (password.isEmpty()) {
            Assert.assertTrue(spFunction.verifyMassage(msgToast, "The password field is required."));
        } else if (password.length() < 6) {
            Assert.assertTrue(spFunction.verifyMassage(msgToast, "Your password is too short, minimum 6 characters required."));
        } else {
            Assert.assertTrue(spFunction.verifyMassage(msgToast, "Invalid Login Credentials."));
        }
    }

    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = driver -> driver != null && ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
                .equals("complete");
        try {
            sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(expectation);
        } catch (Throwable error) {
            fail("Timeout waiting for Page Load Request to complete.");
        }
    }
}
    