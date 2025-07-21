package saucedemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CItem {
    private final WebElement container;

    public CItem(WebElement container) {
        this.container = container;
    }

    private final By image = By.cssSelector("img[class='inventory_item_img']");
    private final By title = By.className("inventory_item_name");
    private final By description = By.className("inventory_item_desc");
    private final By price = By.className("inventory_item_price");
    private final By addRemoveButton = By.className("btn_inventory");
    private final By linkImage = By.cssSelector("a[id*='_img_link']");
    private final By linkTitle = By.cssSelector("a[id*='_title_link']");


    public WebElement getImage(){
        return container.findElement(image);
    }
    public WebElement getTitle(){
        return container.findElement(title);
    }
    public WebElement getDescription(){
        return container.findElement(description);
    }
    public WebElement getPrice(){
        return container.findElement(price);
    }
    public WebElement getAddRemoveButton(){
        return container.findElement(addRemoveButton);
    }
    public WebElement getLinkTitleElement(){
        return container.findElement(linkTitle);
    }
    public WebElement getLinkImageElement() {
        return container.findElement(linkImage);
    }

    public String getTitleText(){
        return getTitle().getText();
    }
    public String getPriceText() {
        return getPrice().getText();
    }
    public double getPriceValue(){
        String value = getPrice().getText().substring(1);
        return Double.parseDouble(value);
    }
    public String getDescriptionText(){
        return getDescription().getText();
    }
    public String getImageSrc(){
        return getImage().getAttribute("src");
    }
    public String getIdByTitle(){
        return getLinkTitleElement().getAttribute("id").split("_")[1];
    }
    public String getIdByImage(){
        return getLinkImageElement().getAttribute("id").split("_")[1];
    }
    public String getIdBy(String titleOrImage) {
        String id;
        switch(titleOrImage) {
            case "title" -> id = getIdByTitle();
            case "image" -> id = getIdByImage();
            default -> id = "";
        }
        return id;
    }
    public String getAddRemoveButtonText(){
        return getAddRemoveButton().getText();
    }
}
