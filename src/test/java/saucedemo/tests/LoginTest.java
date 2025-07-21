package saucedemo.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.dto.UserLoginDto;
import saucedemo.pages.HomePage;
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

    @DataProvider(name = "validUsers")
    public Object[][] getValidUsers() {
        return DataManager.excelToDataProvider(
            "validUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }
    @Test(
            description = "Verify that when user inputs valid credentials is logged in and redirected to the home page.",
            dataProvider = "validUsers"
    )
    public void testSuccessfulUserLogin(UserLoginDto user) {
        HomePage homePage = loginPage.login(user.getUsername(), user.getPassword());
        homePage.assertThat()
                .userIsOnHomePage()
                .logOutButtonIsPresent();
    }

    //===================================================

    @DataProvider(name = "invalidUsers")
    public Object[][] getInvalidUsers() {
        return DataManager.excelToDataProvider(
                "invalidUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }
    @Test(
            description = "Verify that when user inputs invalid credentials is not logged in and error message appears.",
            dataProvider = "invalidUsers"
    )
    public void testUnsuccessfulUserLogin(UserLoginDto user) {
        loginPage.login(user.getUsername(), user.getPassword());
        loginPage.assertThat()
                .userIsOnLoginPage()
                .loginButtonIsVisible()
                .errorMessageAppears(user.getMessage());
    }

    //===================================================

    // User can not directly navigate to any page except login page without logging in previously
}
