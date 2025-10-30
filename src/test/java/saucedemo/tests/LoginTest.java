package saucedemo.tests;

import jdk.jfr.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.data.DataProviders;
import saucedemo.data.UserLoginDto;
import saucedemo.pages.*;
@Description("Login tests")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void loginPageMethodSetup() {
        loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        driver.manage().deleteAllCookies();
    }

    //===================================================

    @Test(
            description = "Verify that when user inputs valid credentials is logged in and redirected to the home page.",
            dataProvider = "validUsers",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    @Description("Verify that when user inputs valid credentials is logged in and redirected to the home page.")
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
    @Description("Verify that when user inputs invalid credentials is not logged in and error message appears.")
    public void testUnsuccessfulUserLogin(UserLoginDto user) {
        loginPage.loginWithInvalidCreds(user.getUsername(), user.getPassword())
                .assertThat()
                .userIsOnLoginPage()
                .loginButtonIsVisible()
                .errorMessageAppears(user.getMessage());
    }

    //===================================================

    // User can log out when logged in -> MenuTest (better)

    //===================================================

    @Test(
            description =
                    "When user tries to navigate to any of the pages except log in page" +
                    "without previously logging in or adding cookie, then" +
                    "they are redirected to the log in page and proper error message appears",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    @Description(
            "When user tries to navigate to any of the pages except log in page" +
            "without previously logging in or adding cookie, then" +
            "they are redirected to the log in page and proper error message appears"
    )
    public void testUnsuccessfulNavigationWithoutPreviouslyLoggingOrAddingCookie(String url) {
        driver.navigate().to(url);
        loginPage.assertThat()
                .userIsOnLoginPage()
                .properErrorMessageAppearsForPageWhereUserWantedToNavigate(url);

    }

    //===================================================

    @Test(
            description =
                            "When user tries to navigate to the any of the pages" +
                            "with previously adding a cookie, then" +
                            "they are redirected to the wanted page",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class
    )
    @Description(
                "When user tries to navigate to the any of the pages" +
                "with previously adding a cookie, then" +
                "they are redirected to the wanted page"
    )
    public void testSuccessfulNavigationWithPreviouslyAddingACookieOnUrl(String url) {
        loginPage.setUserCookie();
        driver.navigate().to(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
    }

    @Test( description = "Login Fail Example with Allure Screenshots", groups = { "smoke" } )
    @Description("Login Fail Example with Allure Screenshots")
    public void testSuccessfulUserLoginFail() {
        loginPage.loginWithValidCreds("wrong_username", "wrong_password")
                .assertThat()
                .userIsOnHomePage()
                .logOutButtonIsPresent();
    }


}
