package kinopoisk.ru;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MoviePageTests {
    private static WebDriver webDriver;
    private static final String SITEURL = "https://www.kinopoisk.ru/";
    private static final String FILM_OPTIONS_MENU_BUTTON_CSSS = ".styles_sticky__mDnbt > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1)";
    private static final String TOP_250_FILMS_CSSS = "a.styles_root__c9qje:nth-child(1)";
    private static final String TOP_1_FILM_CSSS = ".styles_contentSlot__h_lSN > main:nth-child(2) > div:nth-child(3) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > a:nth-child(1)";
    private static final String FILM_PAGES_UNIQUE_ELEMENT_CLASSNAME = "film-details-block";
    private static final String TOP_1_FILM_URL = "https://www.kinopoisk.ru/film/535341/";
    private static final String TRAILERS_PAGE_LINK_CSSS = ".styles_rootInDark__Yb_ii > a:nth-child(2)";
    private static final String TRAILERS_PAGE_UNIQUE_ELEMENT_ID = "comm0";
    private static final String TRAILERS_PAGE_URL = "https://www.kinopoisk.ru/film/535341/video/62802/";
    private static final String CREATORS_PAGE_LINK_CSSS = ".styles_actors__wn_C4 > h3:nth-child(1) > a:nth-child(1)";
    private static final String CREATORS_PAGE_ELEMENT_ID = "block_left";
    private static final String CREATORS_PAGE_URL = "https://www.kinopoisk.ru/film/535341/cast/";
    private static final String INFORMATION_MESSAGE_CLOSE_BUTTON_CSSS = ".styles_closeButtonIcon__umzgU";
    private static final String REVIEW_PAGE_LINK_CSSS = "ul.styles_itemsSpoiler__ROHvQ > li:nth-child(8) > a:nth-child(1)";
    private static final String REVIEW_PAGE_ELEMENT_CLASSNAME = "resp_type";
    private static final String REVIEW_PAGE_URL = "https://www.kinopoisk.ru/film/535341/reviews/";
    private static final String AWARDS_PAGE_LINK_CSSS = "ul.styles_itemsSpoiler__ROHvQ > li:nth-child(2) > a:nth-child(1)";
    private static final String AWARDS_PAGE_ELEMENT_CLASSNAME = "block_left";
    private static final String AWARDS_PAGE_URL = "https://www.kinopoisk.ru/film/535341/awards/";


    @BeforeEach
    public void setUpWebDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test // 14 Проверить, что переходит в карточку фильма
    public void filmPageLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(FILM_OPTIONS_MENU_BUTTON_CSSS)).click(); // Нажимаем на ссылку в меню и перходим на страницу фильмы
        webDriver.findElement(By.cssSelector(TOP_250_FILMS_CSSS)).click(); // Переходим по ссылке топ 250 фильмов
        webDriver.findElement(By.cssSelector(TOP_1_FILM_CSSS)).click(); // Выбираем первый фильм в списке

        assertDoesNotThrow(()->webDriver.findElement(By.className(FILM_PAGES_UNIQUE_ELEMENT_CLASSNAME))); // Ожидаем загрузку элемента страницы

        Assertions.assertEquals(webDriver.getCurrentUrl(), TOP_1_FILM_URL); // Проверяем что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @Test // 15 Проверить, что переходит на страницу трейлера в карточке фильма
    public void trailerPageIsWorking(){
        webDriver.navigate().to(TOP_1_FILM_URL); // Переходим в  карточку фильма

        webDriver.findElement(By.cssSelector(TRAILERS_PAGE_LINK_CSSS)).click(); // Переходим на страницу трейлеров

        assertDoesNotThrow(()->webDriver.findElement(By.id(TRAILERS_PAGE_UNIQUE_ELEMENT_ID))); // Ожидаем загрузку элемента страницы трейлеров

        Assertions.assertEquals(webDriver.getCurrentUrl(), TRAILERS_PAGE_URL); // Проверяем что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @Test // 16 Проверить, что переходит создателям из карточки фильма
    public void creatorsPageIsWorking() {
        webDriver.navigate().to(TOP_1_FILM_URL); // Переходим в  карточку фильма

        webDriver.findElement(By.cssSelector(CREATORS_PAGE_LINK_CSSS)).click(); // Переходим на страницу создателей

        assertDoesNotThrow(() -> webDriver.findElement(By.id(CREATORS_PAGE_ELEMENT_ID))); // Ожидаем загрузку элемента страницы трейлеров

        Assertions.assertEquals(webDriver.getCurrentUrl(), CREATORS_PAGE_URL); // Проверяем, что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @Test // 17 Проверить, что в карточке фильма можно перейти на страницу с рецензиями зрителей
    public void viewersReviewPageIsWorking(){
        webDriver.navigate().to(TOP_1_FILM_URL); // Переходим в  карточку фильма

        webDriver.findElement(By.cssSelector(INFORMATION_MESSAGE_CLOSE_BUTTON_CSSS)).click(); // Закрываем всплывающее окно, мешающее пролистать страницу вниз

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // Пролистываем страницу вниз

        webDriver.findElement(By.cssSelector(REVIEW_PAGE_LINK_CSSS)).click(); // Переходим на страницу с рецензиями

        assertDoesNotThrow(() -> webDriver.findElement(By.className(REVIEW_PAGE_ELEMENT_CLASSNAME))); // Ожидаем загрузки элемента страницы с рецензиями

        Assertions.assertEquals(webDriver.getCurrentUrl(), REVIEW_PAGE_URL); // Проверяем, что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @Test // 18 Проверить, что в карточке фильма можно перейти на страницу с наградами
    public void awardsPageIsWorking(){
        webDriver.navigate().to(TOP_1_FILM_URL); // Переходим в  карточку фильма

        webDriver.findElement(By.cssSelector(INFORMATION_MESSAGE_CLOSE_BUTTON_CSSS)).click(); // Закрываем всплывающее окно, мешающее пролистать страницу вниз

        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)"); // Пролистываем страницу вниз

        webDriver.findElement(By.cssSelector(AWARDS_PAGE_LINK_CSSS)).click(); // Переходим на страницу с наградами


        assertDoesNotThrow(() -> webDriver.findElement(By.className(AWARDS_PAGE_ELEMENT_CLASSNAME))); // Ожидаем загрузки элемента страницы с наградами

        Assertions.assertEquals(webDriver.getCurrentUrl(), AWARDS_PAGE_URL); // Проверяем, что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @AfterEach
    public void finalizeWebDriver(){
        webDriver.quit();
    }

}
