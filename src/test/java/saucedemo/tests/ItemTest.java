package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import saucedemo.base.BaseTest;
import saucedemo.pages.ItemPage;
import saucedemo.pages.LoginPage;

public class ItemTest extends BaseTest {
    private ItemPage itemPage;

    @BeforeMethod
    public void itemPageMethodSetup(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
//        homePage = loginPage.login("standard_user", "secret_sauce");
//        homePage.getMenu().clickOnResetButtonAndRefresh();
//        homePage.waitForPageToLoad();
    }
}
