package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import saucedemo.components.CItem;
import saucedemo.data.ItemDto;
import saucedemo.pages.HomePage;

import java.util.ArrayList;
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

    public HomeAssert itemsAreSortedBySortName(String sortName) {
        boolean sorted = false;
        switch (sortName) {
            case "Name (A to Z)" -> sorted = homePage.isSortedAZ();
            case "Name (Z to A)" -> sorted = homePage.isSortedZA();
            case "Price (low to high)" -> sorted = homePage.isSortedPriceLowToHigh();
            case "Price (high to low)" -> sorted = homePage.isSortedPriceHighToLow();
            default -> Assert.fail("Unknown sort option: " + sortName);
        }
        Assert.assertTrue(sorted, "Products are not sorted correctly by " + sortName);
        return this;
    }

    public HomeAssert cartIconNumberIsValid(int quantity) {
        String number = homePage.getMenu().getCartIcon().getText();
        int actualQuantity = number.isEmpty() ? 0 : Integer.parseInt(number);
        Assert.assertEquals(actualQuantity, quantity, "Cart icon number is not valid");
        return this;
    }

    public HomeAssert allItemsHaveContent() {
        SoftAssert softAssert = new SoftAssert();
        for(CItem item: homePage.getProducts()){
            softAssert.assertFalse(item.getTitleText().isEmpty(), "Title is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getDescriptionText().isEmpty(), "Description is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getPriceText().isEmpty(), "Price is empty: " + item.getIdByTitle());
            softAssert.assertFalse(item.getImageSrc().contains("sl-404.168b1cce"), "Image is not valid: " + item.getIdByTitle());
            softAssert.assertTrue(item.getAddRemoveButton().isDisplayed(), "AddRemove button is not displayed: " + item.getIdByTitle());
        }
        softAssert.assertAll("Not all items have content");
        return this;
    }


    public HomeAssert buttonChangeNameTo(String buttonName, List<CItem> items) {
        SoftAssert softAssert = new SoftAssert();
        for(CItem item: items) {
            softAssert.assertTrue(
                    item.getAddRemoveButton().getText().equalsIgnoreCase(buttonName),
                    "Button text is not " + buttonName + " for item: " + item.getTitleText());
        }
        softAssert.assertAll("Not all buttons for items have changed text to '" + buttonName + "'");
        return this;
    }

    public HomeAssert buttonChangeNameTo(String buttonName, CItem item) {
        Assert.assertTrue(
                item.getAddRemoveButtonText().equalsIgnoreCase(buttonName),
                "Button text is not " + buttonName + " for item: " + item.getTitleText()
        );
        return this;
    }

    public HomeAssert allItemsHaveValidPriceFormat() {
        SoftAssert softAssert = new SoftAssert();
        for(CItem item: homePage.getProducts()){
            String price = item.getPriceText();
            softAssert.assertTrue(price.startsWith("$"), "Price doesn't start with $ " + item.getIdByTitle());
            softAssert.assertEquals(price.split("\\.").length, 2, "Price has problem with dots " + item.getIdByTitle());
            softAssert.assertEquals(price.split("\\.")[1].length(), 2, "Price doesn't have 2 digits after the dot " + item.getIdByTitle());
        }
        softAssert.assertAll("Not all items have valid price format.");
        return this;
    }

    public HomeAssert addedItemsHaveRemoveButtons(List<ItemDto> addedItems) {
        List<CItem> itemsWithRemoveButton = new ArrayList<>();

        for (int i = 0; i < homePage.getProducts().size(); i++) {
            if(homePage.getProducts().get(i).getAddRemoveButtonText().equalsIgnoreCase("remove")){
                itemsWithRemoveButton.add(homePage.getProducts().get(i));
            }
        }
        Assert.assertEquals(itemsWithRemoveButton.size(), addedItems.size());

        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < itemsWithRemoveButton.size(); i++) {
            softAssert.assertEquals(itemsWithRemoveButton.get(i).getIdByTitle(), addedItems.get(i).id());
            softAssert.assertEquals(itemsWithRemoveButton.get(i).getTitleText(), addedItems.get(i).title());
            softAssert.assertEquals(itemsWithRemoveButton.get(i).getDescriptionText(), addedItems.get(i).description());
            softAssert.assertEquals(itemsWithRemoveButton.get(i).getPriceText(), addedItems.get(i).price());
        }
        softAssert.assertAll("Not all items on home page have remove button as should have");
        return this;
    }

    public HomeAssert allItemsHaveButtonsWithText(String text) {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < homePage.getProducts().size(); i++) {
            softAssert.assertEquals(homePage.getProducts().get(i).getAddRemoveButtonText(), text);
        }
        softAssert.assertAll("Not all items have " + text + " button");
        return this;
    }
}
