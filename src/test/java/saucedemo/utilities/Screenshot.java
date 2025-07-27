package saucedemo.utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Screenshot {
    public static void take(WebDriver driver, String className, String methodName) {
        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String dirPath = "screenshots/" + className;
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            FileUtils.copyFile(srcFile, new File(dirPath, "/" + methodName + ".jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
