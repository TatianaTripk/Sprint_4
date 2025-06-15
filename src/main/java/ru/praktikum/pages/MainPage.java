package ru.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.praktikum.util.EnvConfig;

import java.util.List;

public class MainPage {
    private final By cookieButton = By.id("rcc-confirm-button");
    // Локатор для всех вопросов
    private final By questionsLocator = By.className("accordion__heading");
    // Локатор для всех ответов
    private final By answersLocator = By.className("accordion__panel");
    // Первая кнопка "Заказать"
    private final By upperOrderButton = By.cssSelector(".Button_Button__ra12g");
    // Вторая кнопка "Заказать"
    private final By lowerOrderButton = By.cssSelector(".Button_Button__ra12g.Button_Middle__1CSJM");
    // Логотип Самоката
    private final By samokatLogo = By.xpath("//img[@src='/assets/scooter.svg' and @alt='Scooter']");
    // Логотип Яндекса
    private final By yandexLogo = By.xpath("//img[@src='/assets/ya.svg' and @alt='Yandex']");
    private final By orderStatusButton = By.cssSelector(".Header_Link__1TAG7");
    private final By orderNumberField = By.cssSelector((".Input_Input__1iN_Z.Header_Input__xIoUq"));
    private final By goButton = By.cssSelector(".Button_Button__ra12g.Header_Button__28dPO");
    private WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // метод открытия главной страницы
    public void openMainPage() {
        driver.get(EnvConfig.BASE_URL);
    }

    // метод нажатия на кнопку "Заказать"
    public OrderPage clickOrderButton(By orderButtonLocator) {
        WebElement button = driver.findElement(orderButtonLocator);
        button.click();
        return new OrderPage(driver);
    }

    // Метод для закрытия всплывающего окна с информацией о cookies
    public void closeCookiePopup() {
        driver.findElement(cookieButton).click();
    }

    // Получить локатор кнопки "Заказать" по флагу
    public By getOrderButtonLocatorByFlag(boolean isTopButton) {
        return isTopButton ? upperOrderButton : lowerOrderButton;
    }

    // Возвращает список всех вопросов
    public List<WebElement> getQuestions() {
        return driver.findElements(questionsLocator);
    }

    // Возвращает список всех ответов
    public List<WebElement> getAnswers() {
        return driver.findElements(answersLocator);
    }

    // Метод кликает по вопросу с нужным индексом
    public void clickQuestion(int index) {
        getQuestions().get(index).click();
    }

    // Скроллит до нужного вопроса
    public void scrollToQuestion(int index) {
        WebElement element = getQuestions().get(index);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    // Получает текст ответа по индексу
    public String getAnswerText(int index) {
        return getAnswers().get(index).getText();
    }

    // Возвращает WebElement ответа (для ожидания)
    public WebElement getAnswer(int index) {
        return getAnswers().get(index);
    }

    // Кликает на логотип Смоката
    public void clickOnSamokatLogo() {
        driver.findElement(samokatLogo).click();
    }

    // Кликает на логотип Яндекса
    public void clickOnYandexLogo() {
        driver.findElement(yandexLogo).click();
    }

    // Кликает на верхнюю кнопку "Заказать"
    public OrderPage clickUpperOrderButton() {
        driver.findElement(upperOrderButton).click();
        return new OrderPage(driver);
    }

    // Метод, кликающий на кнопку "Статус заказа"
    public void clickOrderStatusButton() {
        driver.findElement(orderStatusButton).click();
    }

    // Метод, который вводит номер заказа в поле
    public void enterOrderNumber() {
        driver.findElement(orderNumberField).sendKeys("12345");
    }

    public OrderStatusPage clickGoButton() {
        driver.findElement(goButton).click();
        return new OrderStatusPage(driver);
    }

}
