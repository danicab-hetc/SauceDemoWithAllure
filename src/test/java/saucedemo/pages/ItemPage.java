package saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.ItemAssert;
import saucedemo.base.BasePage;
import saucedemo.utilities.Route;

public class ItemPage extends BasePage {

    public ItemPage(WebDriver driver) {
        super(driver);
        super.url += Route.ITEM;
    }

    @Override
    public void waitForPageToLoad() {
        getPrice();
    }

    public ItemAssert assertThat() {
        return new ItemAssert(driver, this);
    }

    private final By backToProducts = By.id("back-to-products");
    private final By image = By.className("inventory_details_img");
    private final By title = By.className("inventory_details_name");
    private final By description = By.className("inventory_details_desc");;
    private final By price = By.className("inventory_details_price");;
    private final By addButton = By.id("add-to-cart");
    private final By removeButton = By.id("remove");

    @Step("Get 'Back to Products' button")
    public WebElement getBackToProducts(){
        return wait.until(ExpectedConditions.elementToBeClickable(backToProducts));
    }

    @Step("Get item image")
    public WebElement getImage(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(image));
    }

    @Step("Get item title")
    public WebElement getTitle(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(title));
    }

    @Step("Get item description")
    public WebElement getDescription(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(description));
    }

    @Step("Get item price element")
    public WebElement getPrice(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(price));
    }

    @Step("Get 'Add to Cart' button")
    public WebElement getAddButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(addButton));
    }

    @Step("Get 'Remove from Cart' button")
    public WebElement getRemoveButton() {return wait.until(ExpectedConditions.elementToBeClickable(removeButton));}

    @Step("Get item title text")
    public String getTitleText(){
        return getTitle().getText().trim();
    }

    @Step("Get item description text")
    public String getDescriptionText(){
        return getDescription().getText().trim();
    }

    @Step("Get item price text")
    public String getPriceText(){
        return getPrice().getText();
    }

    @Step("Get item price value as double")
    public double getPriceValue(){
        return Double.parseDouble(getPriceText().substring(1));
    }

    @Step("Get item image source URL")
    public String getImageSrc(){
        return getImage().getAttribute("src");
    }

    @Step("Get 'Add to Cart' button text")
    public String getAddButtonName(){
        return getAddButton().getText().trim();
    }

    @Step("Get 'Remove from Cart' button text")
    public String getRemoveButtonName(){
        return getRemoveButton().getText().trim();
    }

    @Step("Click on 'Add to Cart' button")
    public ItemPage clickOnAddButton(){
        getAddButton().click();
        return this;
    }

    @Step("Click on 'Remove from Cart' button")
    public ItemPage clickOnRemoveButton(){
        getRemoveButton().click();
        return this;
    }

    @Step("Get item ID from URL")
    public String getId(){
        return driver.getCurrentUrl().split("=")[1];
    }

    @Step("Click on 'Back to Products' button and go to Home Page")
    public HomePage clickOnBackToProductsButton(){
        getBackToProducts().click();
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        return homePage;
    }

}
