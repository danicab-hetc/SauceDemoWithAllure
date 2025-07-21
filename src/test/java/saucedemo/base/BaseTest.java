package saucedemo.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void baseClassSetup(){
//        WebDriverManager.edgedriver().setup();
        System.setProperty("webdriver.edge.driver","drivers/msedgedriver.exe");
        driver = new EdgeDriver();
        driver.manage().window().maximize();
    }
    @AfterClass
    public void baseClassTeardown(){
//        if(driver!=null)
//            driver.quit();

    }
}
