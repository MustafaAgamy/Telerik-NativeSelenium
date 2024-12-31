package com.qyood.telerik;

import com.qyood.telerik.utils.JsonReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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

        Assert.assertTrue(demosPage.getDemosPageTitle()
                .contains(testData.getJson("demosPageTitle")),
                "Title of Current Page is: " + demosPage.getDemosPageTitle());

        demosPage.getFreeTrial();

        GetFreeTrialPage getFreeTrialPage = new GetFreeTrialPage(driver);

        Assert.assertTrue(getFreeTrialPage.getFreeTrialsPageTitle()
                        .contains(testData.getJson("freeTrialPageSubTitle")),
                "Title of Current Page is: " + getFreeTrialPage.getFreeTrialsPageTitle());

        getFreeTrialPage.selectUiForReact();

        LoginPage loginPage = new LoginPage(driver);

        Assert.assertTrue(loginPage.getLoginPageTitle()
                        .contains(testData.getJson("loginPageTitle")),
                "Title of Current Page is: " + loginPage.getLoginPageTitle());

        loginPage.login(testData.getJson("email"));

        RegisterAccountPage registerAccountPage = new RegisterAccountPage(driver);

        Assert.assertTrue(registerAccountPage.getRegisterPageTitle()
                        .contains(testData.getJson("signUpPageTitle")),
                "Title of Current Page is: " + registerAccountPage.getRegisterPageTitle());

        registerAccountPage.fillSignUpForm(
                testData.getJson("password"), testData.getJson("firstName"),
                testData.getJson("lastName"), testData.getJson("company"),
                testData.getJson("phone"), testData.getJson("country"));
    }

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        testData = new JsonReader("/src/test/resources/createAccountTests.json");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
