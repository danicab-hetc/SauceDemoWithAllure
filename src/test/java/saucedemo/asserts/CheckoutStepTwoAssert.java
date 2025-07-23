package saucedemo.asserts;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import saucedemo.pages.CheckoutStepOnePage;
import saucedemo.pages.CheckoutStepTwoPage;

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
}
