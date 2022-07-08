package pomtest.testcases;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pomtest.bases.BaseSetUp;
//import pomtest.bases.BaseSetUpSauceLabs;
import pomtest.com.common.ultilities.ExcelData;
import pomtest.pages.LogInPage;

public class LogInTest extends BaseSetUp {

    private WebDriver driver;
    public  LogInPage logInPage;
    public ExcelData excelData;

    @BeforeClass
    public void setUp() { driver = getDriver(); }

    @Test(priority = 1)
    public void userTruePassNull() {
        logInPage = new LogInPage(driver);
        logInPage.login("admin01", "");
    }

    @Test(priority = 2)
    public void userNullPassNull() {
        logInPage.login("", "");
    }

    @Test(priority = 3)
    public void userTruePassLessThan6() {
        logInPage.login("1", "1234567");
    }

    @Test(priority = 4)
    public void userTruePassMoreThan6ButFalse() {
        logInPage.login("admin01", "1234567");
    }

    @Test(priority = 5)
    public void logInSuccessful() {
        excelData = new ExcelData();
        excelData.setExcelFilePath("src/main/resources/testdata.xlsx", "Sheet1");
        logInPage.login(excelData.getCellData("Username", 1), excelData.getCellData("Password", 1));
    }

}
