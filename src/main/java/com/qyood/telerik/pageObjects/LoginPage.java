package com.qyood.telerik.pageObjects;

import com.qyood.telerik.utils.elements.Actions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;

    private final By loginPageTitle_h4 = By.tagName("h4");
    private final By emailField_input = By.id("email");
    private final By next_button = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Login")
    public LoginPage login(String email) {
        Actions.type(emailField_input, driver, email);
        Actions.click(next_button, driver);
        return this;
    }

    @Step("Get Login Page Title")
    public String getLoginPageTitle() {
        return Actions.getText(loginPageTitle_h4, driver);
    }

}
