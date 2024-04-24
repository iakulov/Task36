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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class OuterPagesTests {
    private static WebDriver webDriver;
    private static final String SITEURL = "https://www.kinopoisk.ru/";
    private static final String TELEGRAM_ICON_CSSS = "a.styles_icon__cHky_:nth-child(3) > img:nth-child(1)";
    private static final String TELEGRAM_PAGE_UNIQUE_ELEMENT_ID = "tgme_frame_cont";
    private static final String RUSTORE_DOWNLOAD_LINK_CSSS = "a.styles_store__JFbwQ:nth-child(4)";
    private static final String RUSTORE_PAGE_UNIQUE_ELEMENT_CLASSNAME = "app_main__E5qpy";
    private static final String KINOPOISK_ON_TV_LINK_BUTTON_CSSS = ".styles_title__MhhxD > a:nth-child(1)";
    private static final String COMMON_TV_BUTTON_CSSS = "a.devices-tiles__item:nth-child(1)";
    private static final String NO_MODULES_BUTTON_CSSS = ".device__button";
    private static final String STATION_SELECTION_PAGE_UNIQUE_ELEMENT_ID = "stationSelectionForm";
    private static final String TV_PROGRAMM_FOOTER_LINK_CSSS = "li.styles_bottomSectionMenuItem__RV9c1:nth-child(1) > a:nth-child(1)";
    private static final String TV_PROGRAMM_PAGE_UNIQUE_ELEMENT = ".filters__title";
    private static final String TV_PROGRAMM_PAGE_URL = "https://tv.yandex.ru/";

    @BeforeEach
    public void setUpWebDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test // 9 Проверить, что ссылка на канал в телеграм работает
    public void telegramLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(TELEGRAM_ICON_CSSS)).click(); // Нажимаем на иконку телеграм

        ArrayList tabs = new ArrayList (webDriver.getWindowHandles());

        // Переключаем драйвер на вторую вкладку

        webDriver.switchTo().window((String) tabs.get(1));

        boolean isElementPresent = !webDriver.findElements(By.id(TELEGRAM_PAGE_UNIQUE_ELEMENT_ID)).isEmpty();

        // Проверяем что на странице находится элемент уникальный для страницы телеграма(Доказывает, что мы на нужной странице)

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // 10 Проверить, что ссылка на установку в rustore работает
    public void appstoreDownloadLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(RUSTORE_DOWNLOAD_LINK_CSSS)).click(); // Нажимаем на кнопку скачивания приложения в rustore

        ArrayList tabs = new ArrayList (webDriver.getWindowHandles());

        // Переключаем драйвер на вторую вкладку

        webDriver.switchTo().window((String) tabs.get(1));

//        assertDoesNotThrow(()->webDriver.findElement(By.cssSelector("section.l-content-width:nth-child(2)")));
        boolean isElementPresent = !webDriver.findElements(By.className(RUSTORE_PAGE_UNIQUE_ELEMENT_CLASSNAME)).isEmpty();

        // Проверяем что на странице находится элемент уникальный для страницы rustore(Доказывает, что мы на нужной странице)

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // 11 Проверить, что работает переход к покупке яндекс модуля
    public void modulesLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(KINOPOISK_ON_TV_LINK_BUTTON_CSSS)).click(); // Переходим по ссылке "Смотрите Кинопоиск на телевизоре"

        ArrayList tabs = new ArrayList (webDriver.getWindowHandles());

        // Переключаем драйвер на вторую вкладку

        webDriver.switchTo().window((String) tabs.get(1));

        webDriver.findElement(By.cssSelector(COMMON_TV_BUTTON_CSSS)).click(); // Нажимаем на кнопку "Обычный телевизор"
        webDriver.findElement(By.cssSelector(NO_MODULES_BUTTON_CSSS)).click(); // Нажимаем на кнопку "У меня нет модуля"

        tabs = new ArrayList (webDriver.getWindowHandles());

        // Переключаем драйвер на третью вкладку

        webDriver.switchTo().window((String) tabs.get(2));

        boolean isElementPresent = !webDriver.findElements(By.id(STATION_SELECTION_PAGE_UNIQUE_ELEMENT_ID)).isEmpty();

        // Проверяем что на странице находится элемент уникальный для страницы ЯНДЕКС ПЛЮСА(Доказывает, что мы на нужной странице)

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // 12 Проверить, что переходит в телепрограмму
    public void tvProgramLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(TV_PROGRAMM_FOOTER_LINK_CSSS)).click(); // Переходим по ссылке на телепрограмму

        ArrayList tabs = new ArrayList (webDriver.getWindowHandles());

        // Переключаем драйвер на вторую вкладку

        webDriver.switchTo().window((String) tabs.get(1));

        assertDoesNotThrow(()->webDriver.findElement(By.cssSelector(TV_PROGRAMM_PAGE_UNIQUE_ELEMENT))); // Ожидаем загрузки элемента на странице

        Assertions.assertEquals(webDriver.getCurrentUrl(), TV_PROGRAMM_PAGE_URL); // Проверяем что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @AfterEach
    public void finalizeWebDriver(){
        webDriver.quit();
    }

}
