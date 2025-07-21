package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import saucedemo.components.Item;
import saucedemo.pages.HomePage;

public class HomeAssert {
    private WebDriver driver;
    private HomePage homePage;

    public HomeAssert(WebDriver driver, HomePage homePage) {
        this.driver = driver;
        this.homePage = homePage;
    }

    public HomeAssert userIsOnHomePage(){
        Assert.assertEquals(driver.getCurrentUrl(), homePage.getUrl(), "User is not on the home page");
        return this;
    }
    public HomeAssert logOutButtonIsPresent(){
        Assert.assertTrue(homePage.getMenu().logoutButtonIsVisible(), "Logout button is not present!");
        return this;
    }

    public void itemsAreSortedBySortName(String sortName) {
        boolean sorted = false;
        switch (sortName) {
            case "Name (A to Z)" -> sorted = homePage.isSortedAZ();
            case "Name (Z to A)" -> sorted = homePage.isSortedZA();
            case "Price (low to high)" -> sorted = homePage.isSortedPriceLowToHigh();
            case "Price (high to low)" -> sorted = homePage.isSortedPriceHighToLow();
            default -> Assert.fail("Unknown sort option: " + sortName);
        }
        Assert.assertTrue(sorted, "Products are not sorted correctly by " + sortName);
    }

    public HomeAssert cartIconNumberIsValid(int quantity) {
        String number = homePage.getMenu().getCartIcon().getText();
        int actualQuantity = number.isEmpty() ? 0 : Integer.parseInt(number);
        Assert.assertEquals(actualQuantity, quantity, "Cart icon number is not valid");
        return this;
    }

    public void allItemsHaveContent() {
        SoftAssert softAssert = new SoftAssert();
        for(Item item: homePage.getProducts()){
            softAssert.assertFalse(item.getTitleText().isEmpty(), "Title is empty: " + item.getId());
            softAssert.assertFalse(item.getDescriptionText().isEmpty(), "Description is empty: " + item.getId());
            softAssert.assertFalse(item.getPriceText().isEmpty(), "Price is empty: " + item.getId());
            softAssert.assertFalse(item.getImageSrc().contains("sl-404.168b1cce"), "Image is not valid: " + item.getId());
            softAssert.assertTrue(item.getAddRemoveButton().isDisplayed(), "AddRemove button is not displayed: " + item.getId());
        }
        softAssert.assertAll("Not all items have content");
    }
}
