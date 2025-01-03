package com.qyood.telerik.utils.elements;

import com.qyood.telerik.utils.listenersAndloggers.Log4j2Manager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;

public class Actions {

    public static void type(final By locator, final WebDriver driver, String... keys) {
        try {
            Log4j2Manager.logAction("Sending > " + Arrays.toString(keys) + " < to element -> ", locator);
            Finder.elementVisibility(locator, driver).sendKeys(keys);
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementVisibility(locator, driver));
            Log4j2Manager.logInfoLogMessage("Clicking on Element");
        } catch (Exception e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementPresence(locator, driver));
        }
    }

    public static void click(final By locator, final WebDriver driver) {
        try {
            Log4j2Manager.logAction("Clicking on element -> ", locator);
            Finder.elementVisibility(locator, driver).click();
        } catch (ElementClickInterceptedException e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementVisibility(locator, driver));
        } catch (Exception e) {
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
            javascriptExecutor.executeScript("arguments[0].click();",
                    Finder.elementPresence(locator, driver));
        } catch (Throwable throwable) {
            Log4j2Manager.logErrorLogMessage(throwable);
        }
    }

    public static String getText(final By locator, final  WebDriver driver) {
        try {
            Log4j2Manager.logAction("Getting Text from element -> ", locator);
            return Finder.elementVisibility(locator, driver).getText();
        } catch (Exception e) {
            return Finder.elementPresence(locator, driver).getText();
        }
    }
}
