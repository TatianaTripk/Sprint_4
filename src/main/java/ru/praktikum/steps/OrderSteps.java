package ru.praktikum.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.praktikum.pages.OrderPage;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ru.praktikum.util.EnvConfig.EXPLICIT_TIMEOUT;

public class OrderSteps {
   private OrderPage orderPage;
    private WebDriver driver;
    private WebDriverWait wait;

       public OrderSteps(OrderPage orderPage, WebDriver driver) {
        this.orderPage = orderPage;
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
    }

    // Метод заполнения первой формы
    public void fillFirstForm(String firstName, String lastName, String address, String metroStation, String phone) {
        orderPage.fillInFirstNameField(firstName);
        orderPage.fillInLastNameField(lastName);
        orderPage.fillInAddressField(address);
        fillMetroStationField(metroStation);
        orderPage.fillInPhoneNumberField(phone);
    }

    public void fillMetroStationField(String metroStation) {
        WebElement metro = orderPage.clickMetroField();
        orderPage.fillInMetroStation(metroStation);
        orderPage.sendMetroStationData(metro);
    }

    // Метод заполнения второй формы
    public void fillSecondForm(String deliveryDate, String rentalPeriod, String scooterColour, String comment) {
        orderPage.fillInDeliveryDate(deliveryDate);
        // Выбираем нужный вариант срока аренды
        orderPage.getRentalPeriodByText(rentalPeriod);
        orderPage.selectRentalPeriod(rentalPeriod);
        // Выбор цвета самоката
        orderPage.pickScooterColour(scooterColour);
        // Пишем комментарий к заказу
        orderPage.fillInCommentField(comment);
        orderPage.clickOrderButton();
        orderPage.clickConfirmationButton();
        new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(orderPage.getOrderSuccessModal()));
    }
    public void assertValidation(String expectedError, String deliveryDate, String rentalPeriod, String scooterColour, String comment, boolean shouldShowError) {
        List<WebElement> errors = driver.findElements(orderPage.getErrorLocator());
        if (errors.isEmpty()) {
            WebElement nextButtonLocator = wait.until(ExpectedConditions.elementToBeClickable(orderPage.getNextButton()));
            orderPage.clickNextButton();
            fillSecondForm(deliveryDate, rentalPeriod, scooterColour, comment);
        } else {
            String actualErrorText = errors.get(0).getText().trim();
            System.out.println("Найденный текст: " + actualErrorText);
            assertEquals(orderPage.isErrorVisible(), shouldShowError);
            assertEquals(expectedError, actualErrorText);
            assertFalse(orderPage.isSecondFormVisible());
        }
    }

}
