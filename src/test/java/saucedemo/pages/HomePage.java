package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import saucedemo.asserts.HomeAssert;
import saucedemo.base.BasePage;
import saucedemo.components.Item;
import saucedemo.utilities.Route;

import java.util.List;
import java.util.stream.Stream;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver){
        super(driver);
        super.url += Route.HOME;
    }
    @Override
    public void waitForPageToLoad() {
        getProducts();
    }

    public HomeAssert assertThat(){
        return new HomeAssert(driver, this);
    }

    private final By productSort = By.className("product_sort_container");
    private final By product = By.className("inventory_item");


    public WebElement getProductSort(){
        return wait.until(ExpectedConditions.elementToBeClickable(productSort));
    }
    public List<Item> getProducts(){
        List<WebElement> items =  wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product));
        return items.stream().map(item->new Item(item)).toList();
    }
    public void sortProductBy(String name){
        Select select = new Select(getProductSort());
        select.selectByVisibleText(name);
    }

    public boolean isSortedAZ() {
        List<Item> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i - 1).getTitleText().compareTo(items.get(i).getTitleText()) > 0) {
                return false;
            }
        }
        return true;
    }
    public boolean isSortedZA() {
        List<Item> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i - 1).getTitleText().compareTo(items.get(i).getTitleText()) < 0) {
                return false;
            }
        }
        return true;
    }
    public boolean isSortedPriceLowToHigh() {
        List<Item> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            double prevPrice = items.get(i - 1).getPriceValue();
            double currPrice = items.get(i).getPriceValue();
            if (prevPrice > currPrice) {
                return false;
            }
        }
        return true;
    }
    public boolean isSortedPriceHighToLow() {
        List<Item> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            double prevPrice = items.get(i - 1).getPriceValue();
            double currPrice = items.get(i).getPriceValue();
            if (prevPrice < currPrice) {
                return false;
            }
        }
        return true;
    }

    public void clickOnAddToCartButtons(int quantity) {
        List<Item> items = getProducts();
        for (int i = 0; i < quantity; i++) {
            String buttonName = items.get(i).getAddRemoveButton().getText();
            if (buttonName.equalsIgnoreCase("Add to cart"))
                items.get(i).getAddRemoveButton().click();
        }
    }

    public void clickOnRemoveFromCartButtons(int quantity) {
        List<Item> items = getProducts();
        for (int i = 0; i < quantity; i++) {
            String buttonName = items.get(i).getAddRemoveButton().getText();
            if (buttonName.equalsIgnoreCase("Remove"))
                items.get(i).getAddRemoveButton().click();
        }
    }
}
