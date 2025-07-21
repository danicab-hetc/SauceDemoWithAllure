package saucedemo.components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Item {
    private WebElement conatainer;

    public Item(WebElement conatainer) {
        this.conatainer = conatainer;
    }

    private final By image = By.cssSelector("img[class='inventory_item_img']");
    private final By title = By.className("inventory_item_name");
    private final By description = By.className("inventory_item_desc");
    private final By price = By.className("inventory_item_price");
    private final By addRemoveButton = By.className("btn_inventory");
    private final By link = By.cssSelector("a[href='#']");

    public WebElement getImage(){
        return conatainer.findElement(image);
    }
    public WebElement getTitle(){
        return conatainer.findElement(title);
    }
    public WebElement getDescription(){
        return conatainer.findElement(description);
    }
    public WebElement getPrice(){
        return conatainer.findElement(price);
    }
    public WebElement getAddRemoveButton(){
        return conatainer.findElement(addRemoveButton);
    }
    public WebElement getLinkElement(){
        return conatainer.findElement(link);
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
    public String getId(){
        return getLinkElement().getAttribute("id").split("_")[1];
    }


}
