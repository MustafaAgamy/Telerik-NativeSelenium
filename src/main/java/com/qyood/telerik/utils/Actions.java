package com.qyood.telerik.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Actions {

    public static void type(final By locator, final WebDriver driver, String... keys) {
        try {
            Finder.elementVisibility(locator, driver).sendKeys(keys);
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementVisibility(locator, driver));
        }
    }

    public static void click(final By locator, final WebDriver driver) {
        try {
            Finder.elementVisibility(locator, driver).click();
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementVisibility(locator, driver));
        }
    }

    public static String getText(final By locator, final  WebDriver driver) {
        return Finder.elementVisibility(locator, driver).getText();
    }
}
