package saucedemo.pages;

import io.qameta.allure.Step;
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

    @Step("Get First Name input field")
    public WebElement getFirstNameInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
    }

    @Step("Get Last Name input field")
    public WebElement getLastNameInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
    }

    @Step("Get Postal Code input field")
    public WebElement getPostalCodeInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(postalCodeInput));
    }

    @Step("Get Cancel button")
    public WebElement getCancelButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }

    @Step("Get Continue button")
    public WebElement getContinueButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(continueButton));
    }

    @Step("Get error message text")
    public WebElement getErrorMessage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));
    }

    @Step("Enter First Name: {firstName}")
    public void enterFirstName(String firstName){
        WebElement input = getFirstNameInput();
        input.clear();
        input.sendKeys(firstName);
    }

    @Step("Enter Last Name: {lastName}")
    public void enterLastName(String lastName){
        WebElement input = getLastNameInput();
        input.clear();
        input.sendKeys(lastName);
    }

    @Step("Enter Postal Code: {postalCode}")
    public void enterPostalCode(String postalCode){
        WebElement input = getPostalCodeInput();
        input.clear();
        input.sendKeys(postalCode);
    }

    @Step("Get error message text")
    public String getErrorMessageButtonText(){
        return getErrorMessage().getText().trim();
    }

    @Step("Click on Cancel button and return to Cart Page")
    public CartPage clickOnCancelButton(){
        getCancelButton().click();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageToLoad();
        return cartPage;
    }

    @Step("Click on Continue button and go to Checkout Step Two")
    public CheckoutStepTwoPage clickOnContinueButton(){
        getContinueButton().click();
        return new CheckoutStepTwoPage(driver);
    }

    @Step("Fill in checkout form with First Name: {firstname}, Last Name: {lastname}, Postal Code: {postalcode} and continue")
    public CheckoutStepTwoPage fillInTheFormAndContinue(String firstname, String lastname, String postalcode) {
        enterFirstName(firstname);
        enterLastName(lastname);
        enterPostalCode(postalcode);
        return clickOnContinueButton();
    }
}
