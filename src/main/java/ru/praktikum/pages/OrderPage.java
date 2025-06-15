package ru.praktikum.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderPage {
    // Локаторы для первой формы
    private final By firstNameField = By.xpath("//input[@placeholder='* Имя']");
    private final By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroStationField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneNumberField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");
    // Локаторы для второй формы
    private final By deliveryDateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodDropdown = By.className("Dropdown-placeholder");
    // private final By rentalPeriodOption = By.xpath("//div[contains(@class,'Dropdown-option') and text()='");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']");
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    private final By orderSuccessModal = By.xpath("//div[contains(@class, 'Order_ModalHeader') and text()='Заказ оформлен']");
    private final By aboutRent = By.cssSelector(".Order_Header__BZXOb");
    // список всех ошибок
    By errorLocator = By.xpath("//div[contains(@class,'Input_ErrorMessage') and contains(@class,'Input_Visible')] | //div[contains(@class,'Order_MetroError')]");
    // Заголовок страницы заказа
    private final By orderHeader = By.cssSelector(".Order_Header__BZXOb");

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Метод заполнения первой формы
    public void fillFirstForm(String firstName, String lastName, String address, String metroStation, String phone) {
        driver.findElement(this.firstNameField).sendKeys(firstName);
        driver.findElement(this.lastNameField).sendKeys(lastName);
        driver.findElement(this.addressField).sendKeys(address);
        WebElement metro = driver.findElement(this.metroStationField);
        metro.click();
        metro.sendKeys(metroStation);
        metro.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        driver.findElement(this.phoneNumberField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }


    // Метод заполнения второй формы
    public void fillSecondForm(String deliveryDate, String rentalPeriod, String scooterColour, String comment) {
        wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField)).sendKeys(deliveryDate, Keys.ENTER);
        // Выбираем нужный вариант срока аренды
        selectRentalPeriod(rentalPeriod);
        // Выбор цвета самоката
        if (scooterColour.equals("black") || scooterColour.equals("grey")) {
            driver.findElement(getColourCheckboxById(scooterColour)).click();
        }
        // Пишем комментарий к заказу
        driver.findElement(commentField).sendKeys(comment);
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
        new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
    }

    // Метод выбора срока аренды (принимает строку и кликает по нужному варианту)
    private void selectRentalPeriod(String rentalPeriod) {
        //Открываем выпадающий список
        driver.findElement(rentalPeriodDropdown).click();
        //Ищем элемент с нужным текстом
        By optionLocator = By.xpath("//div[contains(@class, 'Dropdown-option') and text() ='" + rentalPeriod + "']");
        //Кликаем по найденному элементу
        driver.findElement(optionLocator).click();

    }

    private By getColourCheckboxById(String scooterColour) {
        return By.id(scooterColour);
    }

    public boolean isOrderSuccessModalDisplayed() {
        return driver.findElement(orderSuccessModal).isDisplayed();
    }

//    public String getValidationErrorText() {
//        List<WebElement> errors = driver.findElements(errorLocator);
//        if (!errors.isEmpty()) {
//            return errors.get(0).getText().trim();
//        }
//        return "";
//    }

    public void assertValidation(String expectedError, String deliveryDate, String rentalPeriod, String scooterColour, String comment) {
        //  WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        List<WebElement> errors = driver.findElements(errorLocator);
        if (errors.isEmpty()) {
            WebElement nextButtonLocator = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
            clickNextButton();
            fillSecondForm(deliveryDate, rentalPeriod, scooterColour, comment);

        } else {

            String actualErrorText = errors.get(0).getText().trim();
            System.out.println("Найденный текст: " + actualErrorText);
            assertEquals(expectedError, actualErrorText);
            Assert.assertFalse(isSecondFormVisible());
        }
    }

    public boolean isSecondFormVisible() {
        List<WebElement> elements = driver.findElements(deliveryDateField);
        return !elements.isEmpty() && elements.get(0).isDisplayed();

    }
    public void clickOrderHeader() {
        driver.findElement(orderHeader).click();
    }
}


