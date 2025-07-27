package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.CheckoutCompleteAssert;
import saucedemo.asserts.CheckoutStepTwoAssert;
import saucedemo.base.BasePage;
import saucedemo.utilities.Route;

public class CheckoutCompletePage extends BasePage {
    public CheckoutCompletePage(WebDriver driver){
        super(driver);
        super.url += Route.CHECKOUT_COMPLETE;
    }
    @Override
    public void waitForPageToLoad() {
        getBackHomeButton();
    }
    public CheckoutCompleteAssert assertThat(){
        return new CheckoutCompleteAssert(driver, this);
    }

    private  final By title = By.className("title");
    private final By backHomeButton = By.id("back-to-products");

    public WebElement getTitle(){
       return wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }
    public WebElement getBackHomeButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(backHomeButton));
    }

    public String titleText(){
        return getTitle().getText();
    }
    public HomePage clickOnBackHomeButton(){
        getBackHomeButton().click();
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        return homePage;
    }
}
