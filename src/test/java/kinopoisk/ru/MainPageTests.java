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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MainPageTests {
    private static WebDriver webDriver;
    private static final String SITEURL = "https://www.kinopoisk.ru/";
    private static final String SEARCH_FIELD_CLASSNAME = "kinopoisk-header-search-form-input__input";
    private static final String SEARCH_BUTTON_CLASSNAME = "search-form-submit-button__icon";
    private static final String SEARCH_RESULTS_CLASSNAME = "search_results";
    private static final String ONLINE_CINEMA_BUTTON_CSSS = ".styles_sticky__mDnbt > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)";
    private static final String CURRENT_PROMO_URL = "https://hd.kinopoisk.ru/film/7905191c28ba489e8d60b9095bf008ff?from_block=trailer_promo";
    private static final String ONLINE_CINEMA_UNIQUE_ELEMENT_CSSS = "section.content-section__item:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2)";
    private static final String PROMO_WATCH_ONLINE_BUTTON_CSSS = ".kinopoisk-watch-online-button";
    private static final String PROMO_PAGE_PREDICTED_ELEMENT_CSSS = ".ContentWrapper_root__Fi869";
    private static final String TICKET_SELECTION_PAGE_MENU_BUTTON_CSSS = ".styles_sticky__mDnbt > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(7) > a:nth-child(1)";
    private static final String TICKET_SELECTION_PAGE_MOVIE_LIST_CLASSNAME = "filmsListNew";
    private static final String SERIAL_OPTIONS_MENU_BUTTON_CSSS = ".styles_sticky__mDnbt > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(4) > a:nth-child(1)";
    private static final String ALL_SERIALS_BUTTON_CSSS = "a.styles_root__c9qje:nth-child(1)";
    private static final String ALL_SERIAL_TITLE_CSSS = ".styles_title__jB8AZ";

    @BeforeEach
    public void setUpWebDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test // Проверка что поиск работает
    public void searchFieldIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.className(SEARCH_FIELD_CLASSNAME)).sendKeys("Стажер"); // В поле поиска отправляем текст "Стажер"
        webDriver.findElement(By.className(SEARCH_BUTTON_CLASSNAME)).click(); // Нажимаем поиск

        boolean isElementFound = !webDriver.findElements(By.className(SEARCH_RESULTS_CLASSNAME)).isEmpty();

        // Проводим проверку, что элемент, который появляется только на странице с результатами поиска есть на текущей странице

        if (isElementFound){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // Проверка что работает переход в онлайн кинотеатр
    public void onlineCinemaLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(ONLINE_CINEMA_BUTTON_CSSS)).click(); // Переходим по ссылке на онлайн-кинотеатр

        boolean isElementFound = !webDriver.findElements(By.cssSelector(ONLINE_CINEMA_UNIQUE_ELEMENT_CSSS)).isEmpty();

        // Проводим проверку, что элемент, который появляется только на странице онлайн кинотеатра есть на текущей странице

        if (isElementFound){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // Проверка что промо ведёт на нужную страницу
    public void promoLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(PROMO_WATCH_ONLINE_BUTTON_CSSS)).click(); // Переходим по ссылке на промо

        assertDoesNotThrow(()->webDriver.findElement(By.cssSelector(PROMO_PAGE_PREDICTED_ELEMENT_CSSS))); // Ожидаем загрузки элемента на странице промо

        Assertions.assertEquals(webDriver.getCurrentUrl(), CURRENT_PROMO_URL); // Проверяем что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @Test // Проверить, что работает переход к оформлению билетов
    public void ticketPurchaseIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(TICKET_SELECTION_PAGE_MENU_BUTTON_CSSS)).click(); // Переходим по ссылке на выбор фильа для покупки билетов

        boolean isElementPresent = !webDriver.findElements(By.className(TICKET_SELECTION_PAGE_MOVIE_LIST_CLASSNAME)).isEmpty();

        // Переходим по ссылке на выбор фильа для покупки билетов

        if (isElementPresent){
            System.out.println("Test successful");
        }
        else {
            Assertions.fail();
            System.out.println("Test failed");
        }
    }

    @Test // Проверить, что работает переход к сериалам
    public void serialsPageLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(SERIAL_OPTIONS_MENU_BUTTON_CSSS)).click(); // Переходим по ссылке на страницу подборок сералов
        webDriver.findElement(By.cssSelector(ALL_SERIALS_BUTTON_CSSS)).click(); // Переходим по ссылке "Все сериалы"

        Assertions.assertTrue(webDriver.findElement(By.cssSelector(ALL_SERIAL_TITLE_CSSS)).getText().contains("Сериалы онлайн")); // Пролверяем что заглавие страницы "Все сериалы"(Присутствует на нужной нам странице)
    }

    @AfterEach
    public void finalizeWebDriver(){
        webDriver.quit();
    }

}
