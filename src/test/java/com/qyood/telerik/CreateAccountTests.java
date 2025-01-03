package com.qyood.telerik;

import com.qyood.telerik.utils.core.DriverFactory;
import com.qyood.telerik.utils.files.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.qyood.telerik.pageObjects.DemosPage;
import com.qyood.telerik.pageObjects.GetFreeTrialPage;
import com.qyood.telerik.pageObjects.LoginPage;
import com.qyood.telerik.pageObjects.RegisterAccountPage;

public class CreateAccountTests {
    private WebDriver driver;
    private JsonReader testData;

    @Test
    public void testCreateAccount() {
        DemosPage demosPage = new DemosPage(driver);
        demosPage.navigate();

        String demosPageTitle = demosPage.getDemosPageTitle();
        Assert.assertTrue(demosPageTitle
                .contains(testData.getJson("demosPageTitle")),
                "Title of Current Page is: " + demosPageTitle);

        demosPage.getFreeTrial();

        GetFreeTrialPage getFreeTrialPage = new GetFreeTrialPage(driver);

        String getFreeTrialPageTitle = getFreeTrialPage.getFreeTrialsPageTitle();
        Assert.assertTrue(getFreeTrialPageTitle
                        .contains(testData.getJson("freeTrialPageSubTitle")),
                "Title of Current Page is: " + getFreeTrialPageTitle);

        getFreeTrialPage.selectUiForReact();

        LoginPage loginPage = new LoginPage(driver);

        String loginPageTitle = loginPage.getLoginPageTitle();
        Assert.assertTrue(loginPageTitle
                        .contains(testData.getJson("loginPageTitle")),
                "Title of Current Page is: " + loginPageTitle);

        loginPage.login(testData.getJson("email"));

        RegisterAccountPage registerAccountPage = new RegisterAccountPage(driver);

        String registerAccountTitle = registerAccountPage.getRegisterPageTitle();
        Assert.assertTrue(registerAccountTitle
                        .contains(testData.getJson("signUpPageTitle")),
                "Title of Current Page is: " + registerAccountTitle);

        registerAccountPage.fillSignUpForm(
                testData.getJson("password"), testData.getJson("firstName"),
                testData.getJson("lastName"), testData.getJson("company"),
                testData.getJson("phone"), testData.getJson("country"));
    }

    @BeforeMethod
    public void setUp() {
        DriverFactory.setDriver(DriverFactory.BrowserType.CHROME);
        driver = DriverFactory.getDriver();

        testData = new JsonReader("./src/test/resources/createAccountTests.json");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
