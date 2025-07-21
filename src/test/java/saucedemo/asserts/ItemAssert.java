package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import saucedemo.pages.HomePage;
import saucedemo.pages.ItemPage;

public class ItemAssert {
    private WebDriver driver;
    private ItemPage itemPage;

    public ItemAssert(WebDriver driver, ItemPage itemPage) {
        this.driver = driver;
        this.itemPage = itemPage;
    }

    public ItemAssert itemTitleIsSame(String expectedTitle) {
        Assert.assertEquals(itemPage.getTitleText(), expectedTitle, "Titles are not the same for: " + itemPage.getId());
        return this;
    }

    public ItemAssert itemDescriptionIsSame(String descriptionText) {
        Assert.assertEquals(itemPage.getDescriptionText(), descriptionText, "Descriptions are not the same for: " + itemPage.getId());
        return this;
    }


    public ItemAssert itemPriceIsSame(double priceValue) {
        Assert.assertEquals(itemPage.getPriceValue(), priceValue, "Prices are not the same for: " + itemPage.getId());
        return this;
    }

    public ItemAssert itemImageSrcIsSame(String imageSrc) {
        Assert.assertEquals(itemPage.getImageSrc(), imageSrc, "Image sources are not the same for: " + itemPage.getId());
        return this;
    }

    public ItemAssert itemAddRemoveButtonIsSame(String buttonName) {
        Assert.assertEquals(itemPage.getAddButtonName(), buttonName, "Button names are not the same for: " + itemPage.getId());
        return this;
    }

    public ItemAssert itemUrlIsSame(String expectedUrl) {
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "URLs are not the same for: " + itemPage.getId());
        return this;
    }

    public ItemAssert buttonNameChangesTo(String name) {
        switch(name) {
            case "Remove" -> Assert.assertTrue(itemPage.getRemoveButtonName().equalsIgnoreCase("Remove"), "Button name has not changed to 'Remove'");
            case "Add to cart" -> Assert.assertTrue(itemPage.getAddButtonName().equalsIgnoreCase("Add to cart"), "Button name has not changed to 'Add to cart'");
            default -> Assert.fail("Unknown button name has been passed as an argument");
        }
        return this;
    }

    public ItemAssert cartIconNumberIsValid(int quantity) {
        String number = itemPage.getMenu().getCartIcon().getText();
        int actualQuantity = number.isEmpty() ? 0 : Integer.parseInt(number);
        Assert.assertEquals(actualQuantity, quantity, "Cart icon number is not valid");
        return this;
    }
}
