package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.components.CMenu;
import saucedemo.pages.HomePage;
import saucedemo.pages.ItemPage;
import saucedemo.pages.LoginPage;

import java.util.List;

public class ItemTest extends BaseTest {
    private ItemPage itemPage;

    @BeforeMethod
    public void itemPageMethodSetup(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        HomePage homePage = loginPage.login("standard_user", "secret_sauce");
        homePage.getMenu().clickOnResetButtonAndRefresh();
        homePage.waitForPageToLoad();

        itemPage = homePage.clickOnItemTitle(homePage.getProducts().getFirst());
        itemPage.waitForPageToLoad();
    }

    //===================================================

    @Test(
        description = "When user clicks on add button then cart icon is updated and same item button on home page is updated as well as cart icon"
    )
    public void testAddToCart(){
        itemPage.clickOnAddButton();
        itemPage.assertThat()
                .buttonNameChangesTo("Remove")
                .cartIconNumberIsValid(1);

        HomePage homePage = itemPage.clickOnBackToProductsButton();
        homePage.waitForPageToLoad();

        CItem item = homePage.getProducts().getFirst();
        homePage.assertThat()
                .userIsOnHomePage()
                .buttonChangeNameTo("Remove", item)
                .cartIconNumberIsValid(1);
    }

    @Test(
            description = "When user clicks on remove button then cart icon is updated and same item button on home page is updated as well as cart icon"
    )
    public void testRemoveFromCart(){
        itemPage.clickOnAddButton();

        itemPage.clickOnRemoveButton();
        itemPage.assertThat()
                .buttonNameChangesTo("Add to cart")
                .cartIconNumberIsValid(0);

        HomePage homePage = itemPage.clickOnBackToProductsButton();
        homePage.waitForPageToLoad();

        CItem item = homePage.getProducts().getFirst();
        homePage.assertThat()
                .userIsOnHomePage()
                .buttonChangeNameTo("Add to cart", item)
                .cartIconNumberIsValid(0);
    }
}
