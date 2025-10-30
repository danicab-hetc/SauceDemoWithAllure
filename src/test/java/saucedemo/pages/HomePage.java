package saucedemo.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import saucedemo.asserts.HomeAssert;
import saucedemo.base.BasePage;
import saucedemo.components.CItem;
import saucedemo.data.ItemDto;
import saucedemo.utilities.Route;

import java.util.ArrayList;
import java.util.List;

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

    @Step("Get product sort dropdown")
    public WebElement getProductSort(){
        return wait.until(ExpectedConditions.elementToBeClickable(productSort));
    }

    @Step("Get all products on the home page")
    public List<CItem> getProducts() {
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(product));
        return items.stream().map(item -> new CItem(item)).toList();
    }

    @Step("Sort products by: {name}")
    public HomePage sortProductBy(String name){
        Select select = new Select(getProductSort());
        select.selectByVisibleText(name);
        return this;
    }

    @Step("Check if products are sorted A to Z")
    public boolean isSortedAZ() {
        List<CItem> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i - 1).getTitleText().compareTo(items.get(i).getTitleText()) > 0) {
                return false;
            }
        }
        return true;
    }

    @Step("Check if products are sorted Z to A")
    public boolean isSortedZA() {
        List<CItem> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            if (items.get(i - 1).getTitleText().compareTo(items.get(i).getTitleText()) < 0) {
                return false;
            }
        }
        return true;
    }

    @Step("Check if products are sorted by price low to high")
    public boolean isSortedPriceLowToHigh() {
        List<CItem> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            double prevPrice = items.get(i - 1).getPriceValue();
            double currPrice = items.get(i).getPriceValue();
            if (prevPrice > currPrice) {
                return false;
            }
        }
        return true;
    }

    @Step("Check if products are sorted by price high to low")
    public boolean isSortedPriceHighToLow() {
        List<CItem> items = getProducts();
        for (int i = 1; i < items.size(); i++) {
            double prevPrice = items.get(i - 1).getPriceValue();
            double currPrice = items.get(i).getPriceValue();
            if (prevPrice < currPrice) {
                return false;
            }
        }
        return true;
    }

    @Step("Add first {quantity} products to cart")
    public List<CItem> clickOnAddToCartButtons(int quantity) {
        List<CItem> items = getProducts();
        List<CItem> result = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            items.get(i).getAddRemoveButton().click();
            result.add(items.get(i));
        }
        return result;
    }

    @Step("Remove first {quantity} products from cart")
    public List<CItem> clickOnRemoveFromCartButtons(int quantity) {
        return clickOnAddToCartButtons(quantity);
    }

    @Step("Click on item title to go to Item Page")
    public ItemPage clickOnItemTitle(CItem item) {
        item.getLinkTitleElement().click();

        ItemPage itemPage = new ItemPage(driver);
        itemPage.waitForPageToLoad();
        return itemPage;
    }

    @Step("Click on item image to go to Item Page")
    public ItemPage clickOnItemImage(CItem item) {
        item.getLinkImageElement().click();

        ItemPage itemPage = new ItemPage(driver);
        itemPage.waitForPageToLoad();
        return itemPage;
    }

    @Step("Go to item page by clicking {linkClick}")
    public ItemPage goToItemPageByClicking(String linkClick, CItem item) {
        switch(linkClick) {
            case "title" -> clickOnItemTitle(item);
            case "image" -> clickOnItemImage(item);
            default -> {
                return null;
            }
        }
        return new ItemPage(driver);
    }

    @Step("Add first {number} items to cart and return item details")
    public List<ItemDto> addItemsToCart(int number) {
        List<CItem> items = getProducts();
        List<ItemDto> result = new ArrayList<>();
        for(int i = 0; i < number; i++) {
            CItem item = items.get(i);
            item.getAddRemoveButton().click();
            result.add(new ItemDto(
                    item.getIdByTitle(),
                    item.getImageSrc(),
                    item.getTitleText(),
                    item.getDescriptionText(),
                    item.getPriceText(),
                    item.getAddRemoveButtonText()
            ));
        }
        return result;
    }

    @Step("Reset and refresh home page")
    public HomePage resetAndRefresh() {
        this.getMenu().clickOnResetButtonAndRefresh();
        this.waitForPageToLoad();
        return this;
    }
}
