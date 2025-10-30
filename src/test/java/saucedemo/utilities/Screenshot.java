package saucedemo.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Screenshot {
    public static File take(WebDriver driver, String className, String methodName) {
        File screenshotFile = null;
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dirPath = "screenshots/" + className;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            screenshotFile = new File(dirPath, methodName + ".jpg");
            FileUtils.copyFile(srcFile, screenshotFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenshotFile; // vrati fajl za Allure attachment
    }
}


