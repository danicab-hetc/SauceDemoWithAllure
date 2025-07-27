package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.data.DataProviders;
import saucedemo.data.ItemDto;
import saucedemo.pages.HomePage;
import saucedemo.pages.ItemPage;

import java.util.ArrayList;
import java.util.List;

public class ItemTest extends BaseTest {
    private ItemPage itemPage;
    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void itemPageMethodSetup(){
        homePage = new HomePage(driver);
        homePage.navigateToWithCookie();
        homePage.resetAndRefresh();
        itemPage = new ItemPage(driver);
    }

    //===================================================

    @Test(
        description = "When user clicks on add button then button name changes to remove, cart icon is updated and same item button on home page is updated as well as cart icon",
        dataProvider = "itemClick",
        dataProviderClass = DataProviders.class
    )
    public void testAddToCart(String titleOrImage){
        homePage.goToItemPageByClicking(titleOrImage, homePage.getProducts().getFirst())
                .clickOnAddButton()
                .assertThat()
                .buttonNameChangesTo("Remove")
                .cartIconNumberIsValid(1);

        itemPage.clickOnBackToProductsButton()
                .assertThat()
                .userIsOnHomePage()
                .buttonChangeNameTo("Remove", homePage.getProducts().getFirst())
                .cartIconNumberIsValid(1);
    }

    //===================================================

    @Test(
        description = "When user clicks on remove button then button changes name to add, cart icon is updated and same item button on home page is updated as well as cart icon",
        dataProvider = "itemClick",
        dataProviderClass = DataProviders.class
    )
    public void testRemoveFromCart(String titleOrImage){
        homePage.goToItemPageByClicking(titleOrImage, homePage.getProducts().getFirst())
                .clickOnAddButton()
                .clickOnRemoveButton()
                .assertThat()
                .buttonNameChangesTo("Add to cart")
                .cartIconNumberIsValid(0);

        itemPage.clickOnBackToProductsButton()
                .assertThat()
                .userIsOnHomePage()
                .buttonChangeNameTo("Add to cart", homePage.getProducts().getFirst())
                .cartIconNumberIsValid(0);
    }

    //===================================================

    @Test(
            description = "When users adds item from item page then that item is added to the cart page",
            dataProvider = "itemClick",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    public void testSuccessfulAddingToCart(String titleOrImage) {
        ItemDto item = homePage.getProducts().getFirst().toItemDto(titleOrImage);
        homePage.goToItemPageByClicking(titleOrImage, homePage.getProducts().getFirst())
                .clickOnAddButton()
                .getMenu().openCartPage()
                .assertThat()
                .userIsOnCartPage()
                .itemsNumberIsValid(1)
                .cartIconNumberIsValid(1)
                .allAddedItemsContentIsValid(List.of(item));
    }

    //===================================================

    @Test(
            description = "When users removes item from item page then that item is removed from the cart page",
            dataProvider = "itemClick",
            dataProviderClass = DataProviders.class,
            groups = { "smoke" }
    )
    public void testSuccessfulRemovingFromCart(String titleOrImage) {
        ItemDto item = homePage.getProducts().getFirst().toItemDto(titleOrImage);
        homePage.goToItemPageByClicking(titleOrImage, homePage.getProducts().getFirst())
                .clickOnAddButton()
                .clickOnRemoveButton()
                .getMenu().openCartPage()
                .assertThat()
                .userIsOnCartPage()
                .cartIconNumberIsValid(0)
                .itemsListIsEmpty();
    }

}

