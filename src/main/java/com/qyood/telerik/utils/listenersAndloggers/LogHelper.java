package com.qyood.telerik.utils.listenersAndloggers;

import com.qyood.telerik.utils.core.DriverFactory;
import com.qyood.telerik.utils.files.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;

public class LogHelper {
    private static final PropertyReader propertyReader = new PropertyReader("./src/main/resources/LoggingManager.properties");
    private static final boolean USE_EXCEPTION_LOGGING = Boolean.parseBoolean(propertyReader.getProperty("useLoggingManagerForExceptions"));

    public void logAction(String message, By by, String... messageCont) {
        String msgCont = messageCont.length > 0 ? " " + messageCont[0] : "";
        StringBuilder logMessage = new StringBuilder();
        try {
            logMessage.append(message)
                    .append(by != null && !locatorAccessibleName(by).isBlank() ?
                            locatorAccessibleName(by) + " >> " + locator(by) : by != null ? locator(by) : "").append(msgCont);

            if (!logMessage.isEmpty()) {
                Log4j2Manager.logInfoLogMessage(String.valueOf(logMessage));
            }
        } catch (Exception e2) {
            // Do Nothing
        } catch (Throwable throwable) {
            if(USE_EXCEPTION_LOGGING){
                Log4j2Manager.logErrorLogMessage(throwable);
            }else {
                throw throwable;
            }
        }
    }

    private String locatorAccessibleName(By by){
        WebElement webElement = DriverFactory.getDriver().findElement(by);
        return webElement.getAccessibleName();
    }

    private String locator(By by) {
        if (by instanceof RelativeLocator.RelativeBy relativeBy) {
            return "RelativeBy : " + relativeBy.getRemoteParameters().value().toString();
        } else {
            return by.toString();
        }
    }

}
