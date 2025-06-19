package ru.praktikum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.pages.MainPage;
import ru.praktikum.pages.OrderStatusPage;
import ru.praktikum.util.EnvConfig;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static ru.praktikum.util.EnvConfig.BASE_URL;
import static ru.praktikum.util.EnvConfig.YA_URL;

public class ClickLogoTests {
    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    @Test
    public void scooterLogoTest() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickOnSamokatLogo();
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Home_Header__iJKdX")));
        String currentUrl = driver.getCurrentUrl();
        assertEquals(BASE_URL, currentUrl);
    }

    @Test
    public void yandexLogoTest() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickOnYandexLogo();

        //fetch handles of all windows, there will be two, [0]- default, [1] - new window
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        //assert on title of new window
        String currentUrl = driver.getCurrentUrl();
        assertEquals(YA_URL, currentUrl);
        //closing current window
        driver.close();
        //Switch back to the old tab or window
        driver.switchTo().window((String) windowHandles[0]);
    }

    @Test
    public void orderNumberTest() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickOrderStatusButton();
        mainPage.enterOrderNumber("12345");
        OrderStatusPage orderStatusPage = mainPage.clickGoButton();
        orderStatusPage.checkError();
    }
}
