package saucedemo.pages;

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


    public List<CItem> getItems() {
        List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(item));
        return list.stream().map(item -> new CItem(item)).toList();
    }
    public WebElement getItemTotal(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotal));
    }
    public WebElement getTax(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tax));
    }
    public WebElement getTotal(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(total));
    }
    public WebElement getCancelButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
    }
    public WebElement getFinishButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(finishButton));
    }

    public double getTaxValue() {
        String tax = getTax().getText().split("\\$")[1];
        return Double.parseDouble(tax.trim());
    }
    public double getTotalValue() {
        String total = getTotal().getText().split("\\$")[1];
        return Double.parseDouble(total.trim());
    }
    public double getTotalItemValue(){
        String totalItemValueString = getItemTotal().getText().split("\\$")[1];
        return Double.parseDouble(totalItemValueString.trim());
    }
    public double getTotalItemsSum() {
        return getItems().stream().mapToDouble(item -> item.getPriceValue()).sum();
    }


    public HomePage clickOnCancelButton() {
        getCancelButton().click();
        return new HomePage(driver);
    }
}
