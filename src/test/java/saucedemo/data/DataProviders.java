package saucedemo.data;

import org.testng.annotations.DataProvider;
import saucedemo.utilities.ExcelReader;
import saucedemo.utilities.Route;

public class DataProviders {
    @DataProvider(name = "validUsers")
    public Object[][] getValidUsers() {
        return ExcelReader.toDataProvider(
                "validUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }
    @DataProvider(name = "invalidUsers")
    public Object[][] getInvalidUsers() {
        return ExcelReader.toDataProvider(
                "invalidUsers.xlsx", "Sheet1", UserLoginDto.class
        );
    }

    @DataProvider(name = "sortOptions")
    public Object[][] getOptions(){
        return new Object[][] {
                {"Name (A to Z)"},
                {"Name (Z to A)"},
                {"Price (low to high)"},
                {"Price (high to low)"}
        };
    }

    @DataProvider(name = "itemQuantity")
    public Object[][] getItemQuantity(){
        return new Object[][]{
//                {1},
//                {3},
                {6}
        };
    }

    @DataProvider(name = "itemClick")
    public Object[][] getItemClick() {
        return new Object[][] {
                {"title"},
                {"image"}
        };
    }

    @DataProvider(name = "invalidForm")
    public Object[][] getInvalidForm() {
        return new Object[][] {
                {new CheckoutFormDto("","",""),"Error: First Name is required"},
                {new CheckoutFormDto("Danica", "", ""), "Error: Last Name is required"},
                {new CheckoutFormDto("Danica", "Bijeljanin", ""), "Error: Postal Code is required"}
        };
    }

    @DataProvider(name = "PagesUrls")
    public Object[][] getPagesUrls(){
        return new Object[][]{
                {Route.BASE_URL + Route.HOME},
                {Route.BASE_URL + Route.CART},
                {Route.BASE_URL + Route.CHECKOUT_STEP_ONE},
                {Route.BASE_URL + Route.CHECKOUT_STEP_TWO},
                {Route.BASE_URL + Route.CHECKOUT_COMPLETE},
                {Route.BASE_URL + Route.ITEM}
        };
    }
}
