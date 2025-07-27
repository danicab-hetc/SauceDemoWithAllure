package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import saucedemo.pages.CheckoutCompletePage;

public class CheckoutCompleteAssert {
    private WebDriver driver;
    private CheckoutCompletePage checkoutCompletePage;

    public CheckoutCompleteAssert(WebDriver driver, CheckoutCompletePage checkoutCompletePage){
        this.driver = driver;
        this.checkoutCompletePage = checkoutCompletePage;
    }

    public CheckoutCompleteAssert userIsOnCheckoutCompletePage(){
        Assert.assertEquals(driver.getCurrentUrl(), checkoutCompletePage.getUrl(), "User is not on the checkout complete page!");
        return this;
    }
    public CheckoutCompleteAssert cartIconNumberIs0() {
        Assert.assertEquals(checkoutCompletePage.getMenu().getCartIcon().getText(), "", "Cart is not empty.");
        return this;
    }

    public CheckoutCompleteAssert cartIconNumberIs(int quantity) {
        Assert.assertEquals(checkoutCompletePage.getMenu().getCartIcon().getText(), String.valueOf(quantity));
        return this;
    }


    public CheckoutCompleteAssert messageCheckoutCompleteAppears() {

        Assert.assertTrue(checkoutCompletePage.titleText().contains("Complete"),"Message does not contains complete!");
        return this;
    }

}
