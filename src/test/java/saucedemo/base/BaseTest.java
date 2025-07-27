package saucedemo.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import saucedemo.utilities.Screenshot;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass(alwaysRun = true)
    public void baseClassSetup(){
//        WebDriverManager.edgedriver().setup();
        System.setProperty("webdriver.edge.driver","drivers/msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String className = result.getTestClass().getRealClass().getSimpleName();
            String methodName = result.getMethod().getMethodName();

            Object[] params = result.getParameters();
            if (params.length > 0) {
                methodName += "_" + params[0].toString();
            }

            Screenshot.take(driver, className, methodName);
        }
    }

    @AfterClass(alwaysRun = true)
    public void baseClassTeardown(){
        if(driver!=null)
            driver.quit();
    }
}
