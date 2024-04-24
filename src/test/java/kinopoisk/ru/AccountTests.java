package kinopoisk.ru;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class AccountTests {
    private static WebDriver webDriver;
    private static final String SITEURL = "https://www.kinopoisk.ru/";
    private static final String KINOPOISK_LOGIN_BUTTON_CSSS = ".styles_loginButton__LWZQp";
    private static final String LOGIN_FIELD_ID = "passp-field-login";
    private static final String SWITCH_TO_PHONE_BUTTON_CSSS = ".Button2_view_clear";
    private static final String PHONE_FIELD_ID = "passp-field-phone";
    private static final String YANDEX_LOGIN_BUTTON_ID = "passp:sign-in";
    private static final String ALARM_LOGIN_MESSAGE_ID = "field:input-login:hint";
    private static final String ALARM_PHONE_MESSAGE_ID = "field:input-phone:hint";
    private static final String YANDEX_PLUS_SUBSCRIPTION_BUTTON_CSSS = ".styles_contentWrapper__Mjl_p > div:nth-child(4) > button:nth-child(1)";
    private static final String LOGIN_PAGE_TITLE_CLASSNAME = "passp-add-account-page-title";

    @BeforeEach
    public void setUpWebDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test // Проверка что не дает войти по некорректному логину
    public void failedLoginInput(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(KINOPOISK_LOGIN_BUTTON_CSSS)).click(); // Нажимаем на кнопку "Войти"
        webDriver.findElement(By.id(LOGIN_FIELD_ID)).sendKeys("Тест"); // Отправляем в поле логин текст "Тест"
        webDriver.findElement(By.id(YANDEX_LOGIN_BUTTON_ID)).click(); // Нажимаем на кнопку "Войти"

        boolean isElementPresent = !webDriver.findElements(By.id(ALARM_LOGIN_MESSAGE_ID)).isEmpty();

        // Проводим проверку, что появилось сообщение об ошибке

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }

    }

    @Test // Проверка что не дает войти по некорректному телефону
    public void failedPhoneInput(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(KINOPOISK_LOGIN_BUTTON_CSSS)).click(); // Нажимаем на кнопку "Войти"
        webDriver.findElement(By.cssSelector(SWITCH_TO_PHONE_BUTTON_CSSS)).click(); // Нажимаем на кнопку "Телефон"
        webDriver.findElement(By.id(PHONE_FIELD_ID)).sendKeys("1111111111"); // Отправляем в поле логин текст "1111111111"
        webDriver.findElement(By.id(YANDEX_LOGIN_BUTTON_ID)).click(); // Нажимаем на кнопку "Войти"

        boolean isElementPresent = !webDriver.findElements(By.id(ALARM_PHONE_MESSAGE_ID)).isEmpty();

        // Проводим проверку, что появилось сообщение об ошибке

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // Проверить, что при клике на подписку яндекс плюс открывается форма ввода данных аккаунта
    public void subscriptionButtonIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(YANDEX_PLUS_SUBSCRIPTION_BUTTON_CSSS)).click(); // Нажимаем на кнорпку "Попробовать бесплатно" яндекс плюса

        boolean isElementPresent = !webDriver.findElements(By.className(LOGIN_PAGE_TITLE_CLASSNAME)).isEmpty();

        // Проводим проверку, что элемент, который появляется только на странице с входом в аккаунт (Нужная нам страница)

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @AfterEach
    public void finalizeWebDriver(){
        webDriver.quit();
    }

}
