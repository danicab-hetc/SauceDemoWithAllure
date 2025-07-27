package saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import saucedemo.asserts.LoginAssert;
import saucedemo.base.BasePage;
import saucedemo.components.CMenu;
import saucedemo.utilities.Route;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
        super.url += Route.LOGIN;
    }
    
    @Override
    public void waitForPageToLoad() {
        getLoginButton();
    }

    public LoginAssert assertThat(){
        return new LoginAssert(driver, this);
    }

    private final By usernameInput = By.id("user-name");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorField = By.cssSelector(".error-message-container.error");

    public WebElement getUsernameInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
    }
    public WebElement getPasswordInput(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput));
    }
    public WebElement getLoginButton(){
        return wait.until(ExpectedConditions.elementToBeClickable(loginButton));
    }
    public WebElement getErrorField(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorField));
    }

    public void enterUsername(String username){
        WebElement input = getUsernameInput();
        input.clear();
        input.sendKeys(username);
    }
    public void enterPassword(String password){
        WebElement input = getPasswordInput();
        input.clear();
        input.sendKeys(password);
    }
    public void clickOnLoginButton(){
        getLoginButton().click();
    }

    public HomePage login(String username, String password){
        enterUsername(username);
        enterPassword(password);
        clickOnLoginButton();
        return new HomePage(driver);
    }

    public HomePage loginWithValidCreds(String username, String password){
        login(username, password);

        HomePage homePage = new HomePage(driver);
        homePage.waitForPageToLoad();
        return homePage;
    }

    public LoginPage loginWithInvalidCreds(String username, String password){
        login(username, password);
        this.waitForPageToLoad();
        return this;
    }

    public String getErrorMessage(){
        return getErrorField().findElement(By.cssSelector("h3[data-test='error']")).getText().trim();
    }
    public void closeErrorField(){
        getErrorField().findElement(By.cssSelector("button[class='error-button']")).click();
    }


}
