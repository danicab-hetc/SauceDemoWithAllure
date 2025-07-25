package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.CheckoutStepOneAssert;
import saucedemo.base.BasePage;
import saucedemo.utilities.Route;

public class CheckoutStepOnePage extends BasePage {


    public CheckoutStepOnePage(WebDriver driver){
        super(driver);
        super.url += Route.CHECKOUT_STEP_ONE;
    }
    @Override
    public void waitForPageToLoad() {
        getContinueButton();
    }

    public CheckoutStepOneAssert assertThat(){
        return new CheckoutStepOneAssert(driver, this);
    }

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By cancelButton = By.id("cancel");
    private final By continueButton = By.id("continue");
    private final By errorMessage = By.className("error-message-container");

    public WebElement getFirstNameInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }
    public WebElement getLastNameInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
    }
    public WebElement getPostalCodeInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(postalCodeInput));
    }
    public WebElement getCancelButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }
    public WebElement getContinueButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(continueButton));
    }
    public WebElement getErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
    }

    public void enterFirstName(String firstName){
        WebElement input = getFirstNameInput();
        input.clear();
        input.sendKeys(firstName);
    }
    public void enterLastName(String lastName){
        WebElement input = getLastNameInput();
        input.clear();
        input.sendKeys(lastName);
    }
    public void enterPostalCode(String postalCode){
        WebElement input = getPostalCodeInput();
        input.clear();
        input.sendKeys(postalCode);
    }
    public String getErrorMessageButtonText(){
        return getErrorMessage().getText().trim();
    }

    public CartPage clickOnCancelButton(){
        getCancelButton().click();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageToLoad();
        return cartPage;
    }
    public CheckoutStepTwoPage clickOnContinueButton(){
        getContinueButton().click();
        return new CheckoutStepTwoPage(driver);
    }

    public CheckoutStepTwoPage fillInTheFormAndContinue(String firstname, String lastname, String postalcode) {
        enterFirstName(firstname);
        enterLastName(lastname);
        enterPostalCode(postalcode);
        return clickOnContinueButton();
    }
}
