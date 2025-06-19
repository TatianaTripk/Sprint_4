package ru.praktikum.tests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.praktikum.pages.MainPage;
import ru.praktikum.pages.OrderPage;
import ru.praktikum.steps.OrderSteps;

import static org.junit.Assert.assertTrue;
import static ru.praktikum.util.EnvConfig.BLACK_COLOUR;
import static ru.praktikum.util.EnvConfig.GREY_COLOUR;

@RunWith(Parameterized.class)
public class ScooterOrderTest {
    @Rule
    public DriverFactory driverFactory = new DriverFactory();

    private final boolean isTopButton;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String scooterColour;
    private final String comment;


    public ScooterOrderTest(boolean isTopButton, String firstName, String lastName, String address, String metroStation, String phoneNumber, String deliveryDate, String rentalPeriod, String scooterColour, String comment) {
        this.isTopButton = isTopButton;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColour = scooterColour;
        this.comment = comment;
    }

    // Тестовые данные
    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}")
    public static Object[][] getCredentials() {
        return new Object[][]{
                {true, "Анна", "Аннина", "ул. Петрова, 45", "Кропоткинская", "+788877766559", "22.06.2025", "сутки", BLACK_COLOUR, ""},
                {false, "Игорь", "Петров", "ул. Пушкина, 28", "Курская", "+74445556677", "15.09.2025", "четверо суток", GREY_COLOUR, "Позвоните за 30 минут до доставки"},
        };
    }

    @Test
    public void positiveScooterOrderTest() {
        WebDriver driver = driverFactory.getDriver();
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.closeCookiePopup();
        By orderButtonLocator = mainPage.getOrderButtonLocatorByFlag(isTopButton);
        OrderPage orderPage = mainPage.clickOrderButton(orderButtonLocator);
        OrderSteps orderSteps = new OrderSteps(orderPage, driver);
        orderSteps.fillFirstForm(firstName, lastName, address, metroStation, phoneNumber);
        orderPage.clickNextButton();
        orderSteps.fillSecondForm(deliveryDate, rentalPeriod, scooterColour, comment);
        // Проверяем, что заказ оформлен
        assertTrue(orderPage.isOrderSuccessModalDisplayed());
    }
}
