package saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.CheckoutStepTwoAssert;
import saucedemo.base.BasePage;
import saucedemo.components.CItem;
import saucedemo.utilities.Route;

import java.util.Arrays;
import java.util.List;

public class CheckoutStepTwoPage extends BasePage {

    public CheckoutStepTwoPage(WebDriver driver){
        super(driver);
        super.url += Route.CHECKOUT_STEP_TWO;
    }
    @Override
    public void waitForPageToLoad() {   getFinishButton();
    }
    public CheckoutStepTwoAssert assertThat(){
        return new CheckoutStepTwoAssert(driver, this);
    }

    private final By item = By.className("cart_item");
    private final By itemTotal = By.className("summary_subtotal_label");
    private final By tax = By.className("summary_tax_label");
    private final By total = By.className("summary_total_label");
    private final By cancelButton = By.id("cancel");
    private final By finishButton = By.id("finish");

    @Step("Get all items in the checkout summary")
    public List<CItem> getItems() {
        List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(item));
        return list.stream().map(item -> new CItem(item)).toList();
    }

    @Step("Get subtotal element")
    public WebElement getItemTotal(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal));
    }

    @Step("Get tax element")
    public WebElement getTax(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tax));
    }

    @Step("Get total element")
    public WebElement getTotal(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(total));
    }

    @Step("Get Cancel button")
    public WebElement getCancelButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }

    @Step("Get Finish button")
    public WebElement getFinishButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(finishButton));
    }

    @Step("Get tax value as double")
    public double getTaxValue() {
        String tax = getTax().getText().split("\\$")[1];
        return Double.parseDouble(tax.trim());
    }

    @Step("Get total value as double")
    public double getTotalValue() {
        String total = getTotal().getText().split("\\$")[1];
        return Double.parseDouble(total.trim());
    }

    @Step("Get subtotal of items as double")
    public double getTotalItemValue(){
        String totalItemValueString = getItemTotal().getText().split("\\$")[1];
        return Double.parseDouble(totalItemValueString.trim());
    }

    @Step("Calculate sum of all item prices")
    public double getTotalItemsSum() {
        return getItems().stream().mapToDouble(item -> item.getPriceValue()).sum();
    }

    @Step("Click Cancel button and navigate to Home Page")
    public HomePage clickOnCancelButton() {
        getCancelButton().click();

        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        return homePage;
    }

    @Step("Click Finish button and complete checkout")
    public CheckoutCompletePage clickOnFinishButton(){
        getFinishButton().click();
        CheckoutCompletePage checkoutCompletePage = new CheckoutCompletePage(driver);
        checkoutCompletePage.waitForPageToLoad();
        return checkoutCompletePage;
    }

}
