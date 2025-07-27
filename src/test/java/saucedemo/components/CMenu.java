package saucedemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import saucedemo.asserts.CMenuAssert;
import saucedemo.pages.CartPage;
import saucedemo.pages.HomePage;
import saucedemo.pages.LoginPage;

public class CMenu {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    public CMenu(WebDriver driver, WebDriverWait wait, Actions actions) {
        this.driver = driver;
        this.wait = wait;
        this.actions = actions;
    }

    private final By menuButton = By.id("react-burger-menu-btn");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By allItemsButton = By.id("inventory_sidebar_link");
    private final By aboutButton = By.id("about_sidebar_link");
    private final By logoutButton = By.id("logout_sidebar_link");
    private final By resetAppStateButton = By.id("reset_sidebar_link");
    private final By closeButton = By.id("react-burger-cross-btn");

    public WebElement getMenuButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(menuButton));
    }
    public WebElement getCartIcon (){
        return wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
    }
    public WebElement getAllItemsButton (){
        return wait.until(ExpectedConditions.elementToBeClickable(allItemsButton));
    }
    public WebElement getAboutButton (){
        return wait.until(ExpectedConditions.elementToBeClickable(aboutButton));
    }
    public WebElement getLogoutButton (){
        return wait.until(ExpectedConditions.elementToBeClickable(logoutButton));
    }
    public WebElement getResetAppStateButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(resetAppStateButton));
    }
    public WebElement getCloseButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(closeButton));
    }

    public CMenu openMenu(){
        getMenuButton().click();
        return this;
    }
    public void closeMenu(){
        getCloseButton().click();
    }
    public boolean logoutButtonIsVisible() {
        try{
            openMenu();
            return getLogoutButton().isDisplayed();
        } catch (Exception e) {
        } finally {
            closeMenu();
        }
        return false;
    }
    public void clickOnResetButtonAndRefresh() {
        openMenu();
        getResetAppStateButton().click();
        driver.navigate().refresh();
    }
    public LoginPage clickOnLogoutButton(){
        openMenu();
        getLogoutButton().click();
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageToLoad();
        return loginPage;
    }
    public CartPage openCartPage() {
        getCartIcon().click();

        CartPage cartPage = new CartPage(driver);
        cartPage.waitForPageToLoad();
        return cartPage;
    }
    public CMenuAssert assertThat(){
        return new CMenuAssert(driver);
    }

    public HomePage clickOnAllItemsButton() {
        openMenu().getAllItemsButton().click();
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        return homePage;
    }
}
