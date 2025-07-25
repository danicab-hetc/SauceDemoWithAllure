package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import saucedemo.components.CItem;
import saucedemo.data.ItemDto;
import saucedemo.pages.CartPage;

import java.util.List;

public class CartAssert {
    private WebDriver driver;
    private CartPage cartPage;

    public CartAssert(WebDriver driver, CartPage cartPage) {
        this.driver = driver;
        this.cartPage = cartPage;
    }

    public CartAssert userIsOnCartPage(){
        Assert.assertEquals(driver.getCurrentUrl(), cartPage.getUrl(), "User is not on the cart page");
        return this;
    }

    public CartAssert itemsNumberIsValid(int size) {
        Assert.assertEquals(cartPage.getItems().size(), size, "Added size is not the same as the size in the cart page.");
        return this;
    }

    public void allAddedItemsContentIsValid(List<ItemDto> itemsHome) {
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < itemsHome.size(); i++) {
            ItemDto itemHome = itemsHome.get(i);
            CItem itemCart = cartPage.getItems().get(i);
            Assert.assertEquals(itemCart.getIdByTitle(), itemHome.id());
            Assert.assertEquals(itemCart.getTitleText(), itemHome.title());
            Assert.assertEquals(itemCart.getDescriptionText(), itemHome.description());
            Assert.assertEquals(itemCart.getPriceText(), itemHome.price());
            Assert.assertEquals(itemCart.getQuantityValue(), 1);
            Assert.assertEquals(itemCart.getAddRemoveButtonText(), "Remove");
        }
        softAssert.assertAll("All items do not match from homepage added items");
    }

    public CartAssert itemsListIsEmpty() {
        Assert.assertTrue(cartPage.isEmpty());
        return this;
    }

    public CartAssert cartIconNumberIsValid(int quantity) {
        String number = cartPage.getMenu().getCartIcon().getText();
        int actualQuantity = number.isEmpty() ? 0 : Integer.parseInt(number);
        Assert.assertEquals(actualQuantity, quantity, "Cart icon number is not valid");
        return this;
    }

    public CartAssert addedItemsRemainSavedInCart(List<ItemDto> addedItems) {
        List<CItem> itemsInCart = cartPage.getItems();
        SoftAssert softAssert = new SoftAssert();
        for (int i = 0; i < itemsInCart.size(); i++) {
            softAssert.assertEquals(itemsInCart.get(i).getIdByTitle(), addedItems.get(i).id());
            softAssert.assertEquals(itemsInCart.get(i).getTitleText(), addedItems.get(i).title());
            softAssert.assertEquals(itemsInCart.get(i).getDescriptionText(), addedItems.get(i).description());
            softAssert.assertEquals(itemsInCart.get(i).getPriceText() , addedItems.get(i).price());
        }
        softAssert.assertAll("Not all added items remain saved in cart!");
        return this;
    }
}
