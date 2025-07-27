package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BasePage;
import saucedemo.base.BaseTest;
import saucedemo.components.CMenu;
import saucedemo.data.DataProviders;
import saucedemo.pages.*;
import saucedemo.utilities.Route;

public class MenuTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private CMenu menu;

    @BeforeMethod
    public void menuMethodSetup() {
        loginPage = new LoginPage(driver);
        loginPage.navigateToWithCookie();
        menu = loginPage.getMenu();
        homePage = new HomePage(driver);
    }

    //===================================================

    @Test(
            description = "When user opens menu and clicks on logout button, then they are redirected to log in page",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class
    )
    public void testSuccessfulLoggingOut(String url){
        driver.navigate().to(url);
        menu.clickOnLogoutButton()
                .assertThat()
                .userIsOnLoginPage()
                .loginButtonIsVisible();
    }

    //===================================================

    @Test(
            description = "",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class
    )
    public void testSuccessfulAllItems(String url){
        homePage.navigateTo();
        menu.clickOnResetButtonAndRefresh();
        homePage.addItemsToCart(6);

        driver.navigate().to(url);
        menu.clickOnAllItemsButton()
                .assertThat()
                .userIsOnHomePage()
                .cartIconNumberIsValid(6)
                .allItemsHaveButtonsWithText("Remove");
    }






}
