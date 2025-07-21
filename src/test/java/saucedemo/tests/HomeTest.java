package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.pages.HomePage;
import saucedemo.pages.ItemPage;
import saucedemo.pages.LoginPage;
import saucedemo.utilities.DataManager;

import java.util.List;

public class HomeTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void homePageMethodSetup(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        homePage = loginPage.login("standard_user", "secret_sauce");
        homePage.getMenu().clickOnResetButtonAndRefresh();
        homePage.waitForPageToLoad();
    }

    //===================================================

    @DataProvider(name = "sortOptions")
    public Object[][] getOptions(){
        return DataManager.getSortOptions();
    }
    @Test(
        description = "When user applies any of the sort options, then items sort properly",
        dataProvider = "sortOptions"
    )
    public void testSortBy(String sortName){
        homePage.sortProductBy(sortName);
        homePage.assertThat().itemsAreSortedBySortName(sortName);
    }

    //===================================================

    @DataProvider(name = "itemQuantity")
    public Object[][] getItemQuantity(){
        return DataManager.getQuantity();
    }
    @Test(
        description = "When user clicks on item addRemoveButton, its name is changed to opposite",
        dataProvider = "itemQuantity"
    )
    public void testClickingOnItemButton(String quantityType){
        int quantity = (quantityType.equals("one")) ? 1 : homePage.getProducts().size();

        List<CItem> items = homePage.clickOnAddToCartButtons(quantity);
        homePage.assertThat().buttonChangeNameTo("Remove", items);

        items = homePage.clickOnAddToCartButtons(quantity);
        homePage.assertThat().buttonChangeNameTo("Add to cart", items);
    }

    //===================================================

    @Test(
        description = "When user clicks on add to cart buttons then cart icon updates!",
        dataProvider = "itemQuantity"
    )
    public void testAddToCartIcon(String quantityType){
        int quantity = (quantityType.equals("one")) ? 1 : homePage.getProducts().size();

        homePage.clickOnAddToCartButtons(quantity);
        homePage.assertThat().cartIconNumberIsValid(quantity);
    }

    //===================================================

    @Test(
        description = "When user clicks on remove button then cart icon updates!",
        dataProvider = "itemQuantity"
    )
    public void testRemoveFromCartIcon(String quantityType){
        int quantity = (quantityType.equals("one")) ? 1 : homePage.getProducts().size();

        homePage.clickOnAddToCartButtons(quantity);
        homePage.clickOnRemoveFromCartButtons(quantity);
        homePage.assertThat().cartIconNumberIsValid(0);

    }

    //===================================================

    @Test(description = "All items contain data")
    public void testAllItemsHaveContent(){
        homePage.assertThat().allItemsHaveContent();
    }

    //===================================================

    @DataProvider(name = "itemClick")
    public Object[][] getItemClick() {
        return DataManager.getItemClick();
    }
    @Test(
            description = "When user clicks on item image/title then user is redirected to the item page and all data is valid",
            dataProvider = "itemClick"
    )
    public void testRedirectToItemPages(String titleOrImage){
        for (int i = 0; i < homePage.getProducts().size(); i++){
            CItem item = homePage.getProducts().get(i) ;
            String expectedTitle = item.getTitleText();
            String expectedDescription = item.getDescriptionText();
            double expectedPriceValue = item.getPriceValue();
            String expectedImageSrc = item.getImageSrc();
            String expectedAddRemoveButtonText = item.getAddRemoveButton().getText().trim();
            String expectedId = item.getIdBy(titleOrImage);

            ItemPage itemPage = homePage.clickOnItem(titleOrImage, item);
            itemPage.waitForPageToLoad();
            itemPage.assertThat()
                    .itemTitleIsSame(expectedTitle)
                    .itemDescriptionIsSame(expectedDescription)
                    .itemPriceIsSame(expectedPriceValue)
                    .itemImageSrcIsSame(expectedImageSrc)
                    .itemAddRemoveButtonIsSame(expectedAddRemoveButtonText)
                    .itemUrlIsSame(itemPage.getUrl() + expectedId);

            itemPage.clickOnBackToProductsButton();
            homePage.waitForPageToLoad();
        }

    }

    @Test(description = "All products have valid price format")
    public void testValidPriceFormat(){
        homePage.assertThat().allItemsHaveValidPriceFormat();
    }
}
