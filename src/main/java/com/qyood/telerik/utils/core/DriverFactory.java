package com.qyood.telerik.utils.core;

import com.qyood.telerik.utils.listenersAndloggers.Log4j2Manager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // Enum for supported browser types
    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE
    }

    // Initialize the WebDriver
    public static void setDriver(BrowserType browserType) {
        if (driverThreadLocal.get() != null) {
            throw new IllegalStateException("WebDriver is already initialized.");
        }

        switch (browserType) {
            case CHROME:
                driverThreadLocal.set(new ChromeDriver());
                break;
            case FIREFOX:
                driverThreadLocal.set(new FirefoxDriver());
                break;
            case EDGE:
                driverThreadLocal.set(new EdgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        driverThreadLocal.get().manage().window().maximize();
        Log4j2Manager.logInfoLogMessage(browserType.name().toLowerCase() + " driver, starting \"maximized\"");
    }

    public static WebDriver getDriver() {
        if (driverThreadLocal.get() == null) {
            throw new IllegalStateException("WebDriver is not initialized. Call setDriver() first.");
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (driverThreadLocal.get() != null) {
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
