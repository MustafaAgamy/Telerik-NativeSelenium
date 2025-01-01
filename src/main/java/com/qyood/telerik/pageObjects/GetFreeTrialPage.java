package com.qyood.telerik.pageObjects;

import com.qyood.telerik.utils.Actions;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GetFreeTrialPage {
    private WebDriver driver;

    private final By getFreeTrialsTitle_h1 = By.xpath("//h1[contains(@class,'mb1')]");
    private final By tryNowDropDown_a = By.xpath("//a[@href='#']");
    private final By uiForReact_a = By.xpath("//a[@href='/try/kendo-react-ui' and @class='u-db']");

    public GetFreeTrialPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Select UI For React")
    public GetFreeTrialPage selectUiForReact() {
        Actions.click(tryNowDropDown_a, driver);
        Actions.click(uiForReact_a, driver);
        return this;
    }

    @Step("Get Free Trials Page Title")
    public String getFreeTrialsPageTitle() {
        return Actions.getText(getFreeTrialsTitle_h1, driver);
    }
}