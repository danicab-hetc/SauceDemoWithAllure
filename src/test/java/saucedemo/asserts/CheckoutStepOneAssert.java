package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import saucedemo.pages.CheckoutStepOnePage;

public class CheckoutStepOneAssert {
    private WebDriver driver;
    private CheckoutStepOnePage checkoutStepOnePage;

    public CheckoutStepOneAssert(WebDriver driver, CheckoutStepOnePage checkoutStepOnePage) {
        this.driver = driver;
        this.checkoutStepOnePage = checkoutStepOnePage;
    }

    public CheckoutStepOneAssert userIsOnCheckoutStepOnePage(){
        Assert.assertEquals(driver.getCurrentUrl(), checkoutStepOnePage.getUrl(), "User is not on the checkout step one page.");
        return this;
    }

    public CheckoutStepOneAssert userStaysOnStepOneBecauseOfInvalidForm(String errorMsg) {
        Assert.assertTrue(checkoutStepOnePage.getErrorMessage().isDisplayed());
        Assert.assertEquals(checkoutStepOnePage.getErrorMessageButtonText(), errorMsg);
        return this;
    }

    public CheckoutStepOneAssert cartIconNumberIsValid(int expectedNumber) {
        Assert.assertEquals(checkoutStepOnePage.getMenu().getCartIcon().getText(), String.valueOf(expectedNumber));
        return this;
    }
}
