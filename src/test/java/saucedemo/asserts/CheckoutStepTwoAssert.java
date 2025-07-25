package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import saucedemo.components.CItem;
import saucedemo.data.ItemDto;
import saucedemo.pages.*;

import java.util.List;

public class CheckoutStepTwoAssert {
    private WebDriver driver;
    private CheckoutStepTwoPage checkoutStepTwoPage;

    public CheckoutStepTwoAssert(WebDriver driver, CheckoutStepTwoPage checkoutStepTwoPage) {
        this.driver = driver;
        this.checkoutStepTwoPage = checkoutStepTwoPage;
    }

    public CheckoutStepTwoAssert userIsOnCheckoutStepTwoPage(){
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepTwoPage.getUrl(), "User is not on the checkout step two page.");
        return this;
    }

    public CheckoutStepTwoAssert allAddedItemsArePresent(int quantity) {
        Assert.assertEquals(checkoutStepTwoPage.getItems().size(), quantity);
        return this;
    }

    public CheckoutStepTwoAssert allAddedItemsHaveValidInformations(List<ItemDto> expectedItems) {
        List<CItem> actualItems = checkoutStepTwoPage.getItems();
        Assert.assertEquals(actualItems.size(), expectedItems.size());
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < actualItems.size(); i++) {
            softAssert.assertEquals(actualItems.get(i).getIdByTitle(), expectedItems.get(i).id());
            softAssert.assertEquals(actualItems.get(i).getTitleText(), expectedItems.get(i).title());
            softAssert.assertEquals(actualItems.get(i).getDescriptionText(), expectedItems.get(i).description());
            softAssert.assertEquals(actualItems.get(i).getPriceText(), expectedItems.get(i).price());
        }
        softAssert.assertAll("Items don't contain same information as on home page");
        return this;

    }

    public CheckoutStepTwoAssert itemTotalPriceIsValid() {
        Assert.assertEquals(checkoutStepTwoPage.getTotalItemValue(), checkoutStepTwoPage.getTotalItemsSum());
        return this;
    }

    public CheckoutStepTwoAssert totalPriceWithTaxIsValid() {
        double expectedTotal = checkoutStepTwoPage.getTotalItemsSum() + checkoutStepTwoPage.getTaxValue();
        Assert.assertEquals(checkoutStepTwoPage.getTotalValue(), expectedTotal);
        return this;
    }

    public CheckoutStepTwoAssert allItemsTitlesLeadToItemPages() {
        List<CItem> items = checkoutStepTwoPage.getItems();
        List<ItemDto> itemsDto = items.stream()
                .map(item -> item.stepTwoToItemDto()).toList();

        for (int i = 0; i < itemsDto.size(); i++) {
            items = checkoutStepTwoPage.getItems();
            items.get(i).getTitle().click();
            ItemPage itemPage = new ItemPage(driver);
            itemPage.waitForPageToLoad();

            SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(driver.getCurrentUrl(), itemPage.getUrl() + itemPage.getId());
            softAssert.assertEquals(itemPage.getId(), itemsDto.get(i).id());
            softAssert.assertEquals(itemPage.getTitleText(), itemsDto.get(i).title());
            softAssert.assertEquals(itemPage.getDescriptionText(), itemsDto.get(i).description());
            softAssert.assertEquals(itemPage.getPriceText(), itemsDto.get(i).price());
            softAssert.assertAll("Invalid data on the item page");

            checkoutStepTwoPage.navigateTo();
            checkoutStepTwoPage.assertThat().userIsOnCheckoutStepTwoPage();
        }
        return this;
    }
    public CheckoutStepTwoAssert cartIconNumberIsValid(int expectedNumber) {
        Assert.assertEquals(checkoutStepTwoPage.getMenu().getCartIcon().getText(), String.valueOf(expectedNumber));
        return this;
    }
}
