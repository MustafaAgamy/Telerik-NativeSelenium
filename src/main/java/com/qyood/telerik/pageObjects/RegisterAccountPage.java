package com.qyood.telerik.pageObjects;

import com.qyood.telerik.utils.elements.Actions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterAccountPage {
    private WebDriver driver;

    private final By signUpPageTitle_h4 = By.xpath("//h4[contains(@class,'mt20')]");
    private final By passwordField_input = By.id("password");
    private final By firstNameField_input = By.id("fist-name");
    private final By lastNameField_input = By.id("last-name");
    private final By companyField_input = By.id("company");
    private final By phoneField_input = By.id("phone");
    private final By countryField_input = By.xpath("//kendo-combobox[@id='country']//input");
    private final By createAccount_button = By.xpath("//button[@type='submit']");

    public RegisterAccountPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Fill Register Form Details")
    public RegisterAccountPage fillSignUpForm(String password, String firstName, String lastName,
                                              String company, String phone, String country) {
        Actions.type(passwordField_input, driver, password);
        Actions.type(firstNameField_input, driver, firstName);
        Actions.type(lastNameField_input, driver, lastName);
        Actions.type(companyField_input, driver, company);
        Actions.type(phoneField_input, driver, phone);
        Actions.type(countryField_input, driver, country);
        Actions.click(createAccount_button, driver);
        return this;
    }

    @Step("Get Register Page Title")
    public String getRegisterPageTitle() {
        return Actions.getText(signUpPageTitle_h4, driver);
    }

 }
