import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import PageObject.MainPage;
import PageObject.OrderPage1;
import PageObject.OrderPage2;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private MainPage mainPage;
    private OrderPage1 orderPage1;
    private OrderPage2 orderPage2;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phoneNumber;
    private String deliveryDate;
    private String rentalPeriod;
    private String color;

    public OrderTests(String firstName, String lastName, String address, String metroStation, String phoneNumber,
                      String deliveryDate, String rentalPeriod, String color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                {"Георгий", "Филантропов", "Тверская улица, 17", "Тверская", "89103365478", "02.06.2025", "сутки", "black"},
                {"Агрипина", "Гваделупова", "Страстной бульвар, 9", "Пушкинская", "89206641258", "07.07.2025", "четверо суток", "grey"}
        };
    }

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        orderPage1 = new OrderPage1(driver);
        orderPage2 = new OrderPage2(driver);
    }

    @Test
    public void testOrderFromHeaderButton() {
        mainPage.clickOrderButtonHeader();
        fillOrderAndConfirm();
    }

    @Test
    public void testOrderFromBottomButton() {
        // Прокрутка и клик по нижней кнопке
        mainPage.clickOrderButtonBottom();
        fillOrderAndConfirm();
    }

    private void fillOrderAndConfirm() {
        // Заполнение первой страницы
        orderPage1.fillForm(firstName, lastName, address, metroStation, phoneNumber);
        orderPage1.clickNextButton();

        // Явное ожидание заголовка второй страницы
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Про аренду')]")
        ));

        // Заполнение второй страницы
        orderPage2.fillForm(deliveryDate, rentalPeriod, color);
        orderPage2.clickOrderButton();
        orderPage2.clickConfirmOrderButton();

        // Проверка сообщения
        String confirmationMessage = orderPage2.getOrderConfirmationMessage();
        assertTrue("Сообщение об успешном заказе не отображается",
                confirmationMessage.contains("Заказ оформлен"));

        // Дополнительная проверка для отладки
        System.out.println("Order confirmation message: " + confirmationMessage);
    }

    @After
    public void tearDown() {
        // Задержка для визуальной проверки перед закрытием
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.quit();
    }
}
