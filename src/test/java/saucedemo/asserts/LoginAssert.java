package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import saucedemo.pages.LoginPage;

public class LoginAssert {
    WebDriver driver;
    LoginPage loginPage;

    public LoginAssert(WebDriver driver, LoginPage loginPage) {
        this.driver = driver;
        this.loginPage = loginPage;
    }

    public LoginAssert userIsOnLoginPage(){
        Assert.assertEquals(driver.getCurrentUrl(), loginPage.getUrl(), "User is not on the login page.");
        return this;
    }
    public LoginAssert loginButtonIsVisible(){
        Assert.assertTrue(loginPage.getLoginButton().isDisplayed(), "Login button is not displayed");
        return this;
    }
    public LoginAssert errorMessageAppears(String errorMsg){
        Assert.assertEquals(loginPage.getErrorMessage(), errorMsg.trim(), "Error message didn't appear");
        return this;
    }
}
