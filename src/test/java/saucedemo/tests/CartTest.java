package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.data.DataProviders;
import saucedemo.data.ItemDto;
import saucedemo.pages.CartPage;
import saucedemo.pages.HomePage;

import java.util.List;

public class CartTest extends BaseTest {
    private CartPage cartPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void cartPageMethodSetup() {
        homePage = new HomePage(driver);
        homePage.navigateToWithCookie();
        homePage.resetAndRefresh();
        cartPage = new CartPage(driver);
    }

    //===================================================

    @Test(
            description = "When user goes to cart, without previously adding any item, cart is empty.",
            groups = { "smoke" }
    )
    public void testEmptyCart() {
        homePage.assertThat().cartIconNumberIsValid(0);
        homePage.getMenu().openCartPage()
                .assertThat()
                .userIsOnCartPage()
                .itemsListIsEmpty()
                .cartIconNumberIsValid(0);
    }

    //===================================================

    @Test(
            description = "When user adds items from home page, then cart page contains only added items",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    public void testSuccessfullyAddingItemsToCartFromHomePage(int quantity) {
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        homePage.assertThat().cartIconNumberIsValid(quantity);

        homePage.getMenu().openCartPage()
                .assertThat()
                .userIsOnCartPage()
                .itemsNumberIsValid(quantity)
                .cartIconNumberIsValid(quantity)
                .allAddedItemsContentIsValid(addedItems);
    }

    //===================================================

    @Test(
            description = "Clicking on remove buttons, removes items from cart",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    public void testRemovingItems(int quantity) {
        homePage.addItemsToCart(quantity);

        homePage.getMenu().openCartPage()
                .removeAllItems()
                .assertThat()
                .itemsListIsEmpty()
                .cartIconNumberIsValid(0);

        cartPage.clickOnContinueShoppingButton()
                .assertThat()
                .userIsOnHomePage()
                .cartIconNumberIsValid(0)
                .buttonChangeNameTo("Add to cart", homePage.getProducts());
    }

}
