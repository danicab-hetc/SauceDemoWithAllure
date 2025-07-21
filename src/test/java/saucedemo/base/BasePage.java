package saucedemo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import saucedemo.components.Menu;
import saucedemo.utilities.Route;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;  // Simulates mouse actions
    protected String url;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        this.url = Route.BASE_URL;
    }

    public String getUrl() {
        return this.url;
    }

    public void navigateTo() {
        driver.navigate().to(url);
        waitForPageToLoad();
    }

    public Menu getMenu() {
        return new Menu(driver, wait, actions);
    }

    public abstract void waitForPageToLoad();


}
