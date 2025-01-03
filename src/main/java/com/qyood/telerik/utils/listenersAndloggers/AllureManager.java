package com.qyood.telerik.utils.listenersAndloggers;


import com.qyood.telerik.utils.files.Files;
import com.qyood.telerik.utils.files.PropertyReader;

public class AllureManager {
    private static final PropertyReader propertyReader = new PropertyReader("./src/main/resources/Environment.properties");
    private static String allureResultsFolderPath = "allure-results/";
    private static final String allureReportPath = "allure-report";
    private static final Files internalFileSession = Files.getInstance(true);

    public static void setAllureEnvironment() {
        cleanAllureReportDirectory();
        cleanAllureResultsDirectory();
    }

    private static void cleanAllureReportDirectory() {
        // clean allure-report directory before execution
        if (Boolean.valueOf(propertyReader.getProperty("clearAllureReport")).equals(Boolean.TRUE)) {
            try {
                internalFileSession.deleteFolder(allureReportPath);
            } catch (Exception t) {
                Log4j2Manager.logWarnLogMessage("Failed to delete '" + allureReportPath + "' as it is currently open. Kindly restart your device to unlock the directory.");
            }
        }
    }

    private static void cleanAllureResultsDirectory() {
        if (Boolean.valueOf(propertyReader.getProperty("clearAllureResults")).equals(Boolean.TRUE)) {
            // clean allure-results directory before execution
            var allureResultsPath = allureResultsFolderPath.substring(0, allureResultsFolderPath.length() - 1);
            try {
                internalFileSession.deleteFolder(allureResultsPath);
            } catch (Exception t) {
                Log4j2Manager.logWarnLogMessage("Failed to delete '" + allureResultsPath + "' as it is currently open. Kindly restart your device to unlock the directory.");
            }
        }
    }

}
