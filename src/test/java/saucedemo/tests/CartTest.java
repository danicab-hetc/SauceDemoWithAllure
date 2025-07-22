package saucedemo.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.dto.ItemDto;
import saucedemo.pages.CartPage;
import saucedemo.pages.HomePage;
import saucedemo.pages.LoginPage;

import java.util.List;

public class CartTest extends BaseTest {
    private CartPage cartPage;
    private HomePage homePage;

    @BeforeMethod
    public void cartPageMethodSetup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        homePage = loginPage.login("standard_user", "secret_sauce");
        homePage.getMenu().clickOnResetButtonAndRefresh();
        homePage.waitForPageToLoad();
    }

    //===================================================

    @Test(
            description = "When user adds items from home page, then cart page contains only added items",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testSuccessfullyAddingItemsToCartFromHomePage(String quantityItem) {
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        homePage.assertThat().cartIconNumberIsValid(quantity);

        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        cartPage.assertThat()
                .userIsOnCartPage()
                .itemsNumberIsValid(quantity)
                .cartIconNumberIsValid(quantity)
                .allAddedItemsContentIsValid(addedItems);
    }

    //===================================================

    @Test(description = "When user goes to cart, without previously adding any item, cart is empty.")
    public void testEmptyCart() {
        homePage.assertThat().cartIconNumberIsValid(0);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        cartPage.assertThat()
                .userIsOnCartPage()
                .itemsListIsEmpty()
                .cartIconNumberIsValid(0);
    }

    //===================================================

    @Test(
            description = "Clicking on remove buttons remove items from cart",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testRemovingItems(String quantityItem) {
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();

        cartPage.removeAllItems();
        cartPage.assertThat()
                .itemsListIsEmpty()
                .cartIconNumberIsValid(0);

        homePage = cartPage.clickOnContinueShoppingButton();
        homePage.assertThat()
                .userIsOnHomePage()
                .cartIconNumberIsValid(0)
                .buttonChangeNameTo("Add to cart", homePage.getProducts());
    }
}
