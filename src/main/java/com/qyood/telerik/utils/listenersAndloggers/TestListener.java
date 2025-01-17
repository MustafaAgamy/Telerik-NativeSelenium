package com.qyood.telerik.utils.listenersAndloggers;

import com.qyood.telerik.utils.files.PropertyReader;
import org.apache.commons.lang3.SystemUtils;
import org.testng.*;

import java.io.IOException;

public class TestListener implements ISuiteListener, IInvokedMethodListener, IExecutionListener {
    PropertyReader propertyReader;

    @Override
    public void onExecutionStart() {
        AllureManager.setAllureEnvironment();
    }

    @Override
    public void onExecutionFinish() {
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        IInvokedMethodListener.super.beforeInvocation(method, testResult);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        IInvokedMethodListener.super.afterInvocation(method, testResult);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        IInvokedMethodListener.super.beforeInvocation(method, testResult, context);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        IInvokedMethodListener.super.afterInvocation(method, testResult, context);
    }

    @Override
    public void onStart(ISuite suite) {
        ISuiteListener.super.onStart(suite);
    }

    @Override
    public void onFinish(ISuite suite) {
        propertyReader = new PropertyReader("./src/main/resources/Environment.properties");
        if(Boolean.valueOf(propertyReader.getProperty("openAllureReport")).equals(Boolean.TRUE)) {
            if (SystemUtils.IS_OS_WINDOWS) {
                String command = "cmd /c start \"\" cmd.exe /K \"cd /d" + System.getProperty("user.dir") + "&& allure generate --single-file --clean\"";
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
