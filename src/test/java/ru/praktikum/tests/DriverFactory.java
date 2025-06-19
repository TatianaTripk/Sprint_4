package ru.praktikum.tests;

import org.junit.rules.ExternalResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.praktikum.util.EnvConfig;

import java.time.Duration;

public class DriverFactory extends ExternalResource {

    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void initDriver() {
        if ("firefox".equals(System.getProperty("browser"))) {
            startFirefox();
        } else {
            startChrome();
        }
    }

    private void startFirefox() {
        System.setProperty("webdriver.gecko.driver", "C:\\Program files\\webdrivers\\geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.IMPLICIT_TIMEOUT));
        driver.manage().window().maximize();
    }

    private void startChrome() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program files\\webdrivers\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion("109");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(EnvConfig.EXPLICIT_TIMEOUT));
        driver.manage().window().maximize();

    }

    @Override
    protected void before() throws Throwable {
        initDriver();
    }

    @Override
    protected void after() {
        driver.quit();
    }
}
