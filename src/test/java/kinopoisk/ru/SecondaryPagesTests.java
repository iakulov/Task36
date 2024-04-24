package kinopoisk.ru;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SecondaryPagesTests {
    private static WebDriver webDriver;
    private static final String SITEURL = "https://www.kinopoisk.ru/";
    private static final String LOGO_CLASSNAME = "kinopoisk-header-logo__img";
    private static final String FILM_OPTIONS_MENU_BUTTON_CSSS = ".styles_sticky__mDnbt > nav:nth-child(1) > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1)";
    private static final String FIRST_PAGE_UNIQUE_PROMO_ID = "trailer-promo-block";
    private static final String HAMBURGER_MENU_FIRSTPAGE_BUTTON_CSSS = ".styles_navigationMenu__c_jLJ > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1)";
    private static final String MORE_MATERIALS_BUTTON_CSSS = ".style_buttonSize48__7RF4w";
    private static final String ARTICLESPAGE_BUTTON_CSSS = "a.media-main-page-navigation-menu__item:nth-child(3)";
    private static final String ARTICLESPAGE_URL = "https://www.kinopoisk.ru/media/article/";
    private static final String FIRST_ARTICLE_CSSS = "div.posts-grid__main-section-column:nth-child(1)";

    @BeforeEach
    public void setUpWebDriver() {
        WebDriverManager.firefoxdriver().setup();
        webDriver = new FirefoxDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test  // 1 Лого - ссылка на главную
    public void logoLinkIsWorking() {
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(FILM_OPTIONS_MENU_BUTTON_CSSS)).click(); // Нажимаем на ссылку в меню и перходим на страницу фильмы
        webDriver.findElement(By.className(LOGO_CLASSNAME)).click(); // Нажимаем на лого и переходим на основную страницу

        assertDoesNotThrow(()->webDriver.findElement(By.id(FIRST_PAGE_UNIQUE_PROMO_ID)));

        Assertions.assertEquals(webDriver.getCurrentUrl(), SITEURL); // Проверка, что мы вернулись на главную страницу (сравниваем url страницы с ожидаемым)
    }

    @Test // 8 Проверить, что бургер меню открывается
    public void hamburgerMenuIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(FILM_OPTIONS_MENU_BUTTON_CSSS)).click(); // Нажимаем на ссылку в меню и перходим на страницу фильмы

        Actions action = new Actions(webDriver);
        WebElement bm = webDriver.findElement(By.className(LOGO_CLASSNAME));
        action.moveToElement(bm).build().perform(); // Наводим курсор на логотип сайта

        webDriver.findElement(By.cssSelector(HAMBURGER_MENU_FIRSTPAGE_BUTTON_CSSS)).click(); // Нажимаем на первый элемент в бургер меню и переходим по ссылке(доказывает, что бургер меню открывается)

    }

    @Test // 13 Проверить, что переходит в статьи через больше материалов
    public void articleLinkIsWorking(){
        webDriver.navigate().to(SITEURL); // Переходим на сайт
        webDriver.findElement(By.cssSelector(MORE_MATERIALS_BUTTON_CSSS)).click(); // Нажимаем на кнопку "больше материалов" и переходим на страницу медиа
        webDriver.findElement(By.cssSelector(ARTICLESPAGE_BUTTON_CSSS)).click(); // Нажимаем на кнопку "Статьи"

        assertDoesNotThrow(()->webDriver.findElement(By.cssSelector(FIRST_ARTICLE_CSSS))); // Ожидаение, когда загрузится первый элемент статей

        Assertions.assertEquals(webDriver.getCurrentUrl(), ARTICLESPAGE_URL); // Проверяем, что мы на нужной странице (сравниваем url страницы с ожидаемым)
    }

    @AfterEach
    public void finalizeWebDriver(){
        webDriver.quit();
    }

}
