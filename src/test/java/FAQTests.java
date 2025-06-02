import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import PageObject.MainPage;

import static org.junit.Assert.assertEquals;

public class FAQTests {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
    }

    @Test
    public void checkQuestion1Answer() {
        mainPage.clickQuestion1();
        String expectedAnswer = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";
        String actualAnswer = mainPage.getAnswer1Text();
        assertEquals("Текст ответа не совпадает", expectedAnswer, actualAnswer);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
