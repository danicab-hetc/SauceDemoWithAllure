package saucedemo.tests;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import saucedemo.base.BaseTest;
import saucedemo.pages.HomePage;
import saucedemo.pages.LoginPage;
import saucedemo.utilities.DataManager;

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
            description = "When user applies sort option, then items sort properly",
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
        description = "When user clicks on add to cart button then cart icon updates!",
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

    @Test(description = "All items contain all elements")
    public void testAllItemsHaveContent(){
        homePage.assertThat().allItemsHaveContent();
    }

}
