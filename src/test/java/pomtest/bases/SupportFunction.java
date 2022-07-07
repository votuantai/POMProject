package pomtest.bases;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SupportFunction {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public SupportFunction(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void setText(By by, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).clear();
        driver.findElement(by).sendKeys(value);
    }

    public void clickElement(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    public boolean verifyMassage(By by, String expectedMsg) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        String msg = driver.findElement(by).getText();
        return msg.contains(expectedMsg);
    }
}
