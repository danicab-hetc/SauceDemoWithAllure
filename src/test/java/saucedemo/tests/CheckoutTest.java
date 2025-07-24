package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.dto.CheckoutFormDto;
import saucedemo.dto.ItemDto;
import saucedemo.pages.*;
import saucedemo.utilities.DataManager;

import java.util.List;

public class CheckoutTest extends BaseTest {
    private CheckoutStepTwoPage checkoutStepTwoPage;
    private CheckoutStepOnePage checkoutStepOnePage;
    private CartPage cartPage;
    private HomePage homePage;

    @BeforeMethod
    public void CheckoutPageMethodSetup() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        homePage = loginPage.login("standard_user", "secret_sauce");
        homePage.getMenu().clickOnResetButtonAndRefresh();
        homePage.waitForPageToLoad();
    }

    //===================================================

    @Test(
            description = "When user goes to the Checkout step one page then number of items displayed on the cart icon is valid",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testValidNumberOfAddedItemsDisplayedOnTheCartIcon(String quantityItem) {
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();

        checkoutStepOnePage.assertThat().numberOfAddedItemsDisplayedOnTheCartIconIsValid(addedItems.size());
    }

    //===================================================

    @Test(
            description = "When user inputs valid information into every form field and clicks on continue button then is redirected to checkout step two page ",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testSuccessfullyRedirectingToCheckoutTwoPage(String quantityItem) {
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();

        CheckoutFormDto formInfo = new CheckoutFormDto("Danica", "Bijeljanin", "10001");
        CheckoutStepTwoPage checkoutStepTwoPage;
        checkoutStepTwoPage = checkoutStepOnePage.fillInTheFormAndContinue(
                formInfo.firstName(),
                formInfo.lastName(),
                formInfo.postalCode()
        );
        checkoutStepTwoPage.assertThat().userIsOnCheckoutStepTwoPage();
    }

    //===================================================

    @DataProvider(name = "invalidForm")
    public Object[][] getInvalidForm() {
        return DataManager.getInvalidForm();
    }
    @Test(
            description = "When user doesn't fill all forms and clicks on continue button then is not redirected to checkout step two page and proper error message appears ",
            dataProvider = "invalidForm"
    )
    public void testUnsuccessfulRedirectionWhenFormIsNotFilledProperly(CheckoutFormDto data, String errorMsg) {
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();

        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();

        checkoutStepOnePage.fillInTheFormAndContinue(
                data.firstName(),
                data.lastName(),
                data.postalCode()
        );
        checkoutStepOnePage.assertThat()
                .userStaysOnStepOneBecauseOfInvalidForm(errorMsg)
                .userIsOnCheckoutStepOnePage();
    }

    //===================================================

    @Test(
            description = "When user clicks on cancel button on the checkout step one page then is redirected to cart page and all saved data are valid. ",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testCancelCheckoutStepOnePage(String quantityItem){
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();

        cartPage = checkoutStepOnePage.clickOnCancelButton();

        cartPage.assertThat()
                .userIsOnCartPage()
                .cartIconNumberIsValid(quantity)
                .itemsNumberIsValid(addedItems.size())
                .allAddedItemsContentIsValid(addedItems);
    }

    //=================================================== checkout step TWO

    @Test(
            description = "When user adds items in cart and goes to checkout step two page, then all added items are present on checkout step two page.",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testValidItemsContentOnCheckoutStepTwoPage(String quantityItem){
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();
        checkoutStepTwoPage = checkoutStepOnePage.fillInTheFormAndContinue("Danica", "Bijeljanin", "11000");

        checkoutStepTwoPage.waitForPageToLoad();

        checkoutStepTwoPage.assertThat()
                        .userIsOnCheckoutStepTwoPage()
                        .allAddedItemsArePresent(quantity)
                        .allAddedItemsHaveValidInformations(addedItems);

    }

    //===================================================

    @Test(
            description = "When user adds items in cart and goes to checkout step two page, then total price is valid.",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
    )
    public void testValidTotalPrice(String quantityItem){
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();

        homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();
        checkoutStepTwoPage = checkoutStepOnePage.fillInTheFormAndContinue("Danica", "Bijeljanin", "11000");

        checkoutStepTwoPage.waitForPageToLoad();

        checkoutStepTwoPage.assertThat()
                .userIsOnCheckoutStepTwoPage()
                .itemTotalPriceIsValid()
                .totalPriceWithTaxIsValid();
    }

    //===================================================

    @Test(
            description = "When user is on checkout step two page and clicks cancel button, then is redirected to home page and added items remain added on home page as well as saved on cart page.",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
            )
    public void testSuccessfulReturningToHomePageFromCheckoutStepTwoPage(String quantityItem){
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();
        checkoutStepTwoPage = checkoutStepOnePage.fillInTheFormAndContinue("Danica", "Bijeljanin", "11000");
        checkoutStepTwoPage.waitForPageToLoad();

        homePage = checkoutStepTwoPage.clickOnCancelButton();
        homePage.waitForPageToLoad();
        homePage.assertThat().userIsOnHomePage()
                .cartIconNumberIsValid(quantity)
                .validItemsButtonsHaveTextRemove(addedItems);

        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        cartPage.assertThat().userIsOnCartPage()
                .cartIconNumberIsValid(quantity)
                .allAddedItemsContentIsValid(addedItems);
    }

    @Test(
            description = "When user clicks on any item title, then they are redirected to the item page.",
            dataProvider = "itemQuantity",
            dataProviderClass = HomeTest.class
        )
    public void testValidLinksToItemPages(String quantityItem){
        int quantity = (quantityItem.equals("one")) ? 1 : homePage.getProducts().size();
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        cartPage = homePage.getMenu().clickOnCartIcon();
        cartPage.waitForPageToLoad();
        checkoutStepOnePage = cartPage.clickOnCheckoutButton();
        checkoutStepOnePage.waitForPageToLoad();
        checkoutStepTwoPage = checkoutStepOnePage.fillInTheFormAndContinue("Danica", "Bijeljanin", "11000");
        checkoutStepTwoPage.waitForPageToLoad();

        checkoutStepTwoPage.assertThat()
                .userIsOnCheckoutStepTwoPage()
                .allItemsTitlesLeadToItemPages();
    }



}
