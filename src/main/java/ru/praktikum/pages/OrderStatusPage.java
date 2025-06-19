package ru.praktikum.pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderStatusPage {
    public OrderStatusPage(WebDriver driver) {
        this.driver = driver;
    }

    private WebDriver driver;
    public final By errorPicture = By.cssSelector("img[alt='Not found']");

    public void checkError() {
        Assert.assertTrue(driver.findElement(errorPicture).isDisplayed());
    }

}
