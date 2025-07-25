package saucedemo.data;

import org.testng.annotations.DataProvider;
import saucedemo.utilities.DataManager;

public class DataProviders {
    @DataProvider(name = "validUsers")
    public Object[][] getValidUsers() {
        return DataManager.excelToDataProvider(
                "validUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }
    @DataProvider(name = "invalidUsers")
    public Object[][] getInvalidUsers() {
        return DataManager.excelToDataProvider(
                "invalidUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }

    @DataProvider(name = "sortOptions")
    public Object[][] getOptions(){
        return DataManager.getSortOptions();
    }
    @DataProvider(name = "itemQuantity")
    public Object[][] getItemQuantity(){
        return DataManager.getQuantity();
    }
    @DataProvider(name = "itemClick")
    public Object[][] getItemClick() {
        return DataManager.getItemClick();
    }
    @DataProvider(name = "invalidForm")
    public Object[][] getInvalidForm() {
        return DataManager.getInvalidForm();
    }
}
