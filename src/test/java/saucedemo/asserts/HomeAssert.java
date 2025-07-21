package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import saucedemo.components.CItem;
import saucedemo.pages.HomePage;

import java.util.List;

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
        for(CItem item: homePage.getProducts()){
            softAssert.assertFalse(item.getTitleText().isEmpty(), "Title is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getDescriptionText().isEmpty(), "Description is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getPriceText().isEmpty(), "Price is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getImageSrc().contains("sl-404.168b1cce"), "Image is not valid: " + item.getIdByTitle());
            softAssert.assertTrue(item.getAddRemoveButton().isDisplayed(), "AddRemove button is not displayed: " + item.getIdByTitle());
        }
        softAssert.assertAll("Not all items have content");
    }


    public void buttonChangeNameTo(String buttonName, List<CItem> items) {
        SoftAssert softAssert = new SoftAssert();
        for(CItem item: items) {
            softAssert.assertTrue(
                    item.getAddRemoveButton().getText().equalsIgnoreCase(buttonName),
                    "Button text is not " + buttonName + " for item: " + item.getTitleText());
        }
        softAssert.assertAll("Not all buttons for items have changed text to '" + buttonName + "'");
    }

    public void allItemsHaveValidPriceFormat() {
        SoftAssert softAssert = new SoftAssert();
        for(CItem item: homePage.getProducts()){
            String price = item.getPriceText();
            softAssert.assertTrue(price.startsWith("$"), "Price doesn't start with $ " + item.getIdByTitle());
            softAssert.assertEquals(price.split("\\.").length, 2, "Price has problem with dots " + item.getIdByTitle());
            softAssert.assertEquals(price.split("\\.")[1].length(), 2, "Price doesn't have 2 digits after the dot " + item.getIdByTitle());
        }
        softAssert.assertAll("Not all items have valid price format.");
    }
}
