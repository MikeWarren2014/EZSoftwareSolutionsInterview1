package com.mikewarren.ezsoftwaresolutionsinterview.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

public final class WebDriverUtils {
    private static WebDriver driver;

    public static WebDriver GetWebDriver() {
        if (this.driver != null)
            return this.driver;

        // TODO: for some reason, we face the warning where we can't find exact match for CDP version 134 (the ChromeDriver.exe is version 134)
        System.setProperty("webdriver.chrome.driver",
                new File('src/main/resources/chromedriver.exe').getAbsolutePath());

        driver = new ChromeDriver();
        // Maximize the browser window
        driver.manage().window().maximize();

        return driver;
    }

    public static void CloseWebDriver() {
        if (this.driver != null) {
            this.driver.quit();
            this.driver = null;
        }
    }
}
