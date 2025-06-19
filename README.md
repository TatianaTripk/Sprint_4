# Sprint_4
Для запуска тестов в браузере Mozilla Firefox использовать такую команду в терминале:
mvn clean test -Dbrowser=firefox

Для запуска тестов в браузере Google Chrome использовать такую команду в терминале:
mvn clean test

Я ипользую Windows 7 и старые версии браузеров Chrome и Firefox, поэтому мне пришлось добавить эти строки в класс DriverFactory:
System.setProperty("webdriver.gecko.driver", "C:\\Program files\\webdrivers\\geckodriver.exe");
System.setProperty("webdriver.chrome.driver", "C:\\Program files\\webdrivers\\chromedriver.exe");
options.setBrowserVersion("109");
