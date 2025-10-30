package saucedemo.tests;

import jdk.jfr.Description;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BasePage;
import saucedemo.base.BaseTest;
import saucedemo.components.CMenu;
import saucedemo.data.DataProviders;
import saucedemo.pages.*;
import saucedemo.utilities.Route;

import java.time.Duration;
@Description("Menu tests")
public class MenuTest extends BaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private CMenu menu;

    @BeforeMethod(alwaysRun = true)
    public void menuMethodSetup() {
        loginPage = new LoginPage(driver);
        loginPage.navigateToWithCookie();
        menu = new CMenu(driver, new WebDriverWait(driver, Duration.ofSeconds(10)), new Actions(driver));
        homePage = new HomePage(driver);
    }

    //===================================================

    @Test(
            description = "When user opens menu and clicks on logout button, then they are redirected to log in page",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class,
            groups = { "smoke"}
    )
    @Description("When user opens menu and clicks on logout button, then they are redirected to log in page")
    public void testSuccessfulLoggingOut(String url){
        driver.navigate().to(url);
        menu.clickOnLogoutButton()
                .assertThat()
                .userIsOnLoginPage()
                .loginButtonIsVisible();
    }

    //===================================================

    @Test(
            description = "When user clicks on All Items, then they are redirected to the home page",
            dataProvider = "PagesUrls",
            dataProviderClass = DataProviders.class
    )
    @Description("When user clicks on All Items, then they are redirected to the home page")
    public void testSuccessfulAllItemsLinkNavigateToHomePage(String url){
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
