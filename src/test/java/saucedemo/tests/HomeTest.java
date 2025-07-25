package saucedemo.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.components.CItem;
import saucedemo.data.DataProviders;
import saucedemo.data.ItemDto;
import saucedemo.pages.HomePage;
import saucedemo.pages.ItemPage;

import java.util.List;

public class HomeTest extends BaseTest {
    private HomePage homePage;

    @BeforeMethod
    public void homePageMethodSetup(){
        homePage = new HomePage(driver);
        homePage.navigateToWithCookie();
        homePage.resetAndRefresh();
    }

    //===================================================

    @Test(
        description = "When user applies any of the sort options, then items sort properly",
        dataProvider = "sortOptions",
        dataProviderClass = DataProviders.class
    )
    public void testSortBy(String sortName){
        homePage.sortProductBy(sortName)
                .assertThat()
                .itemsAreSortedBySortName(sortName);
    }

    //===================================================

    @Test(
        description = "When user clicks on item addRemoveButton, its name is changed to opposite",
        dataProvider = "itemQuantity",
        dataProviderClass = DataProviders.class
    )
    public void testClickingOnItemButton(int quantity){
        List<CItem> items = homePage.clickOnAddToCartButtons(quantity);
        homePage.assertThat().buttonChangeNameTo("Remove", items);

        items = homePage.clickOnRemoveFromCartButtons(quantity);
        homePage.assertThat().buttonChangeNameTo("Add to cart", items);
    }

    //===================================================

    @Test(
        description = "When user clicks on add to cart buttons then cart icon updates!",
        dataProvider = "itemQuantity",
        dataProviderClass = DataProviders.class
    )
    public void testAddToCartIcon(int quantity){
        homePage.clickOnAddToCartButtons(quantity);
        homePage.assertThat().cartIconNumberIsValid(quantity);
    }

    //===================================================

    @Test(
        description = "When user clicks on remove button then cart icon updates!",
        dataProvider = "itemQuantity",
        dataProviderClass = DataProviders.class
    )
    public void testRemoveFromCartIcon(int quantity){
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


    @Test(
            description = "When user clicks on item image/title then user is redirected to the item page and all data is valid",
            dataProvider = "itemClick",
            dataProviderClass = DataProviders.class
    )
    public void testRedirectToItemPages(String titleOrImage){
        for (int i = 0; i < homePage.getProducts().size(); i++){
            CItem citem = homePage.getProducts().get(i);
            ItemDto expected = citem.toItemDto(titleOrImage);

            ItemPage itemPage = homePage.goToItemPageByClicking(titleOrImage, citem);
            itemPage.assertThat()
                    .itemTitleIsSame(expected.title())
                    .itemDescriptionIsSame(expected.description())
                    .itemPriceIsSame(expected.price())
                    .itemImageSrcIsSame(expected.image())
                    .itemAddRemoveButtonIsSame(expected.buttonText())
                    .itemUrlIsSame(itemPage.getUrl() + expected.id());

            itemPage.clickOnBackToProductsButton()
                    .assertThat()
                    .userIsOnHomePage();
        }
    }

    //===================================================

    @Test(description = "All products have valid price format")
    public void testValidPriceFormat(){
        homePage.assertThat().allItemsHaveValidPriceFormat();
    }
}
