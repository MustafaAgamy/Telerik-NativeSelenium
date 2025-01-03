package com.qyood.telerik.utils.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Finder {

    protected static WebElement elementPresence(final By locator, final WebDriver driver) {
        return wait(driver).until(driver1 -> driver.findElement(locator));
    }

    protected static WebElement elementVisibility(final By locator, final WebDriver driver) {
        return wait(driver).until(driver1 -> {
           WebElement webElement = driver.findElement(locator);
           return webElement.isDisplayed() ? webElement : null;
        });
    }

    private static WebDriverWait wait(final WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(90));
    }
}
