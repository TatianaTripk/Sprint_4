package ru.praktikum.tests;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.pages.MainPage;
import ru.praktikum.pages.OrderStatusPage;
import ru.praktikum.util.EnvConfig;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class ClickLogoTests {
    @Rule
    public DriverFactory driverFactory = new DriverFactory();
    @Test
    public void testScooterLogo() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickOnSamokatLogo();
        new WebDriverWait(driver, Duration.ofSeconds(EnvConfig.EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Home_Header__iJKdX")));
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://qa-scooter.praktikum-services.ru/", currentUrl);
    }

    @Test
    public void testYandexLogo() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        String currHandle = driver.getWindowHandle();
        mainPage.clickOnYandexLogo();

        //fetch handles of all windows, there will be two, [0]- default, [1] - new window
        Object[] windowHandles = driver.getWindowHandles().toArray();
        driver.switchTo().window((String) windowHandles[1]);
        //assert on title of new window
        String currentUrl = driver.getCurrentUrl();
        assertEquals("https://ya.ru", currentUrl);
        //closing current window
        driver.close();
        //Switch back to the old tab or window
        driver.switchTo().window((String) windowHandles[0]);
    }

    @Test
    public void checkOrderNumber() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickOrderStatusButton();
        OrderStatusPage orderStatusPage = mainPage.clickGoButton();
        orderStatusPage.checkError();
    }
}
