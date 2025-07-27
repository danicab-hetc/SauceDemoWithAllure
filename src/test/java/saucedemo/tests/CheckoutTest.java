package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.data.CheckoutFormDto;
import saucedemo.data.DataProviders;
import saucedemo.data.ItemDto;
import saucedemo.pages.*;

import java.util.List;

public class CheckoutTest extends BaseTest {
    private CheckoutCompletePage checkoutCompletePage;
    private CheckoutStepTwoPage checkoutTwoPage;
    private CheckoutStepOnePage checkoutOnePage;
    private CartPage cartPage;
    private HomePage homePage;

    @BeforeMethod
    public void CheckoutPageMethodSetup() {
        homePage = new HomePage(driver);
        homePage.navigateToWithCookie();
        homePage.resetAndRefresh();

        cartPage = new CartPage(driver);
        checkoutOnePage = new CheckoutStepOnePage(driver);
        checkoutTwoPage = new CheckoutStepTwoPage(driver);
        checkoutCompletePage = new CheckoutCompletePage(driver);
    }

    //===================================================

    @Test(
            description = "When user goes to the Checkout step one page then number of items displayed on the cart icon is valid",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testValidNumberOfAddedItemsDisplayedOnTheCartIcon(int quantity) {
        homePage.addItemsToCart(quantity);
        homePage.getMenu().openCartPage()
                .clickOnCheckoutButton()
                .assertThat()
                .userIsOnCheckoutStepOnePage()
                .cartIconNumberIsValid(quantity);
    }

    //===================================================

    @Test(
            description = "When user inputs information into every form field and clicks on continue button then they are redirected to checkout step two page "
    )
    public void testSuccessfullyRedirectingToCheckoutTwoPage() {
        checkoutOnePage.navigateTo();
        checkoutOnePage
                .fillInTheFormAndContinue("Name", "Lastname", "10000")
                .assertThat()
                .userIsOnCheckoutStepTwoPage();
    }

    //===================================================

    @Test(
            description = "When user doesn't fill all forms and clicks on continue button then is not redirected to checkout step two page and proper error message appears ",
            dataProvider = "invalidForm",
            dataProviderClass = DataProviders.class
    )
    public void testUnsuccessfulRedirectionWhenFormIsNotFilledProperly(CheckoutFormDto data, String errorMsg) {
        checkoutOnePage.navigateTo();
        checkoutOnePage.fillInTheFormAndContinue(
                data.firstName(),
                data.lastName(),
                data.postalCode()
        );
        checkoutOnePage.assertThat()
                .userStaysOnStepOneBecauseOfInvalidForm(errorMsg)
                .userIsOnCheckoutStepOnePage();
    }

    //===================================================

    @Test(
            description = "When user clicks on cancel button on the checkout step one page then they are redirected to cart page and all saved data is valid. ",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testCancelCheckoutStepOnePage(int quantity) {
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        checkoutOnePage.navigateTo();
        checkoutOnePage.clickOnCancelButton()
                .assertThat()
                .userIsOnCartPage()
                .cartIconNumberIsValid(quantity)
                .itemsNumberIsValid(quantity)
                .allAddedItemsContentIsValid(addedItems);
    }

    //=================================================== checkout step TWO

    @Test(
            description = "When user adds items in cart and goes to checkout step two page, then all added items are present on checkout step two page.",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testValidItemsContentOnCheckoutStepTwoPage(int quantity) {
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        checkoutTwoPage.navigateTo();
        checkoutTwoPage.assertThat()
                .userIsOnCheckoutStepTwoPage()
                .cartIconNumberIsValid(quantity)
                .allAddedItemsArePresent(quantity)
                .allAddedItemsHaveValidInformations(addedItems);
    }

    //===================================================

    @Test(
            description = "When user adds items in cart and goes to checkout step two page, then total price is valid.",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testValidTotalPrice(int quantity) {
        homePage.addItemsToCart(quantity);
        checkoutTwoPage.navigateTo();
        checkoutTwoPage.assertThat()
                .userIsOnCheckoutStepTwoPage()
                .itemTotalPriceIsValid()
                .totalPriceWithTaxIsValid();
    }

    //===================================================

    @Test(
            description = "When user is on checkout step two page and clicks on cancel button, then they are redirected to home page and added items remain added on home page as well as saved on cart page.",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testSuccessfulReturningToHomePageFromCheckoutStepTwoPage(int quantity) {
        List<ItemDto> addedItems = homePage.addItemsToCart(quantity);
        checkoutTwoPage.navigateTo();

        checkoutTwoPage.clickOnCancelButton()
                .assertThat()
                .userIsOnHomePage()
                .cartIconNumberIsValid(quantity)
                .addedItemsHaveRemoveButtons(addedItems);

        homePage.getMenu().openCartPage()
                .assertThat()
                .userIsOnCartPage()
                .cartIconNumberIsValid(quantity)
                .allAddedItemsContentIsValid(addedItems);
    }

    @Test(
            description = "When user clicks on any item title, then they are redirected to the item page.",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testValidLinksToItemPages(int quantity) {
        homePage.addItemsToCart(quantity);
        checkoutTwoPage.navigateTo();
        checkoutTwoPage.assertThat()
                .userIsOnCheckoutStepTwoPage()
                .allItemsTitlesLeadToItemPages();
    }
    //---------------------- CheckoutCompletePage tests
    @Test(
            description =
                    "When user adds items into the cart and goes to checkout complete page " +
                    "by clicking finish button on checkout step two page," +
                    " then message checkout complete appears and cart icon is empty!",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testSuccessfulCompleteMessageAppearing(int quantity) {
        homePage.addItemsToCart(quantity);
        checkoutTwoPage.navigateTo();
        checkoutTwoPage
                    .clickOnFinishButton()
                    .assertThat()
                    .userIsOnCheckoutCompletePage()
                    .messageCheckoutCompleteAppears()
                    .cartIconNumberIs0();
    }

    @Test(
            description = "When user adds items into the cart and directly navigate to checkout complete page, then message checkout complete appears and cart icon is not empty!",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testUnsuccessfulOrderCompletingAfterDirectNavigationToCheckoutCompletePage(int quantity) {
        homePage.addItemsToCart(quantity);

        checkoutCompletePage.navigateTo();
        checkoutCompletePage
                .assertThat()
                .userIsOnCheckoutCompletePage()
                .messageCheckoutCompleteAppears()
                .cartIconNumberIs(quantity);

    }


    @Test(
            description =
                    "When user adds items into the cart, goes to checkout complete page and open cart page," +
                    " then cart page is empty!",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void testEmptyCartAfterGoingToCheckoutCompletePage(int quantity) {
        homePage.addItemsToCart(quantity);

        checkoutTwoPage.navigateTo();
        checkoutTwoPage
                .clickOnFinishButton()
                .assertThat()
                .userIsOnCheckoutCompletePage();

        cartPage.navigateTo();
        cartPage.assertThat()
                .userIsOnCartPage()
                .cartIconNumberIsValid(0)
                .itemsListIsEmpty();
    }
    @Test(
            description =
                    "When user adds items to the cart, goes to checkout complete page and clicks on back home button," +
                            "then all items on home page have add to cart buttons and cart icon is empty.",
            dataProvider = "itemQuantity",
            dataProviderClass = DataProviders.class
    )
    public void test(int quantity) {
        homePage.addItemsToCart(quantity);

        checkoutTwoPage.navigateTo();
        checkoutTwoPage.clickOnFinishButton();

        checkoutCompletePage.assertThat().userIsOnCheckoutCompletePage();
        checkoutCompletePage
                .clickOnBackHomeButton()
                .assertThat()
                .userIsOnHomePage()
                .cartIconNumberIsValid(0)
                .allItemsHaveButtonsWithText("Add to cart");
        //.buttonChangeNameTo("Add to cart", )
    }
}
