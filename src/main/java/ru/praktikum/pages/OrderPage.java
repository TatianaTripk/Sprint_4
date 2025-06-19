package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static ru.praktikum.util.EnvConfig.*;

public class OrderPage {
    // Локаторы
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
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[contains(@class, 'Button_Middle') and text()='Заказать']");
    private final By confirmYesButton = By.xpath("//button[text()='Да']");
    public final By orderSuccessModal = By.xpath("//div[contains(@class, 'Order_ModalHeader') and text()='Заказ оформлен']");
    // список всех ошибок
    private final By errorLocator = By.xpath("//div[contains(@class,'Input_ErrorMessage') and contains(@class,'Input_Visible')] | //div[contains(@class,'Order_MetroError')]");
    // Заголовок страницы заказа
    private final By orderHeader = By.cssSelector(".Order_Header__BZXOb");

    private WebDriver driver;
    private WebDriverWait wait;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_TIMEOUT));
    }

    // Методы
    public void fillInPhoneNumberField(String phone) {
        driver.findElement(this.phoneNumberField).sendKeys(phone);
    }

    public void sendMetroStationData(WebElement metro) {
        metro.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
    }

    public void fillInMetroStation(String metroStation) {
        driver.findElement(this.metroStationField).sendKeys(metroStation);
    }

    public WebElement clickMetroField() {
        WebElement metro = driver.findElement(this.metroStationField);
        metro.click();
        return metro;
    }

    public void fillInAddressField(String address) {
        driver.findElement(this.addressField).sendKeys(address);
    }

    public void fillInLastNameField(String lastName) {
        driver.findElement(this.lastNameField).sendKeys(lastName);
    }

    public void fillInFirstNameField(String firstName) {
        driver.findElement(this.firstNameField).sendKeys(firstName);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
    }


    public void clickConfirmationButton() {
        wait.until(ExpectedConditions.elementToBeClickable(confirmYesButton)).click();
    }

    public void clickOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(orderButton)).click();
    }

    public void fillInCommentField(String comment) {
        driver.findElement(commentField).sendKeys(comment);
    }

    public void pickScooterColour(String scooterColour) {
        if (scooterColour.equals(BLACK_COLOUR) || scooterColour.equals(GREY_COLOUR)) {
            driver.findElement(getColourCheckboxById(scooterColour)).click();
        }
    }

    public void pickRentalPeriod(String rentalPeriod) {
        {
            driver.findElement(getRentalPeriodByText(rentalPeriod)).click();
        }
    }

    public void selectRentalPeriod(String rentalPeriod) {
        driver.findElement(rentalPeriodDropdown).click();
        pickRentalPeriod(rentalPeriod);
    }

    public void fillInDeliveryDate(String deliveryDate) {
        wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField)).sendKeys(deliveryDate, Keys.ENTER);
    }

    public boolean isOrderSuccessModalDisplayed() {
        return driver.findElement(orderSuccessModal).isDisplayed();
    }

    public boolean isSecondFormVisible() {
        List<WebElement> elements = driver.findElements(deliveryDateField);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public boolean isErrorVisible() {
        List<WebElement> elements = driver.findElements(errorLocator);
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                return true;
            }
        }
        return false;
    }

    public void clickOrderHeader() {
        driver.findElement(orderHeader).click();
    }

    private By getColourCheckboxById(String scooterColour) {
        return By.id(scooterColour);
    }

    public By getRentalPeriodByText(String rentalPeriod) {
        String xpath = String.format("//div[contains(@class, 'Dropdown-option') and text()='%s']", rentalPeriod);
        return By.xpath(xpath);
    }

    public By getOrderSuccessModal() {
        return orderSuccessModal;
    }

    public By getErrorLocator() {
        return errorLocator;
    }

    public By getNextButton() {
        return nextButton;
    }
}


