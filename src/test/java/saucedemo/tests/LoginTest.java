package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.data.DataProviders;
import saucedemo.data.UserLoginDto;
import saucedemo.pages.LoginPage;
import saucedemo.utilities.DataManager;

public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod
    public void loginPageMethodSetup() {
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
    }

    //===================================================

    @Test(
            description = "Verify that when user inputs valid credentials is logged in and redirected to the home page.",
            dataProvider = "validUsers",
            dataProviderClass = DataProviders.class
    )
    public void testSuccessfulUserLogin(UserLoginDto user) {
        loginPage.loginWithValidCreds(user.getUsername(), user.getPassword())
                    .assertThat()
                    .userIsOnHomePage()
                    .logOutButtonIsPresent();
    }

    //===================================================

    @Test(
            description = "Verify that when user inputs invalid credentials is not logged in and error message appears.",
            dataProvider = "invalidUsers",
            dataProviderClass = DataProviders.class
    )
    public void testUnsuccessfulUserLogin(UserLoginDto user) {
        loginPage.loginWithInvalidCreds(user.getUsername(), user.getPassword())
                    .assertThat()
                    .userIsOnLoginPage()
                    .loginButtonIsVisible()
                    .errorMessageAppears(user.getMessage());
    }

    //===================================================

    // User can not directly navigate to any page except login page without logging in previously
}
