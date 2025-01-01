package com.qyood.telerik.pageObjects;

import com.qyood.telerik.utils.Actions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DemosPage {
    private WebDriver driver;

    private final By demosPageTitle_h1 = By.xpath("//h1[contains(@class,'mb0')]");
    private final By getFreeTrialButton_a = By.xpath("//a[@href='/download' and contains(@class, 'Button')]");

    public DemosPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate To Demos Page")
    public DemosPage navigate() {
        driver.navigate().to("https://www.telerik.com/support/demos");
        return this;
    }
    @Step("Navigate To Get Free Trials Page")
    public DemosPage getFreeTrial() {
        Actions.click(getFreeTrialButton_a, driver);
        return this;
    }

    @Step("Get Demos Page Title")
    public String getDemosPageTitle() {
        return Actions.getText(demosPageTitle_h1, driver);
    }

}
