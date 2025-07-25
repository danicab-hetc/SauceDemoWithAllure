package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.CartAssert;
import saucedemo.base.BasePage;
import saucedemo.components.CItem;
import saucedemo.utilities.Route;

import java.util.List;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) {
        super(driver);
        super.url += Route.CART;
    }

    @Override
    public void waitForPageToLoad() {
        getCheckoutButton();
    }

    public CartAssert assertThat() {
        return new CartAssert(driver, this);
    }

    private final By continueShoppingButton = By.className("back");
    private final By checkoutButton = By.id("checkout");
    private final By cartItem = By.className("cart_item");

    public WebElement getContinueShoppingButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
    }

    public WebElement getCheckoutButton() {
        return wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
    }

    public List<CItem> getItems() {
        List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItem));
        return list.stream().map(item -> new CItem(item)).toList();
    }

    public HomePage clickOnContinueShoppingButton() {
        getContinueShoppingButton().click();
        return new HomePage(driver);
    }

    public CheckoutStepOnePage clickOnCheckoutButton() {
        getCheckoutButton().click();

        CheckoutStepOnePage checkoutOnePage = new CheckoutStepOnePage(driver);
        checkoutOnePage.waitForPageToLoad();
        return checkoutOnePage;
    }

    public boolean isEmpty() {
        return driver.findElements(cartItem).isEmpty();
    }

    public CartPage removeAllItems() {
        for(CItem item: getItems()){
            item.clickOnAddRemoveButton();
        }
        return this;
    }
}
