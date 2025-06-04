import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import PageObject.MainPage;
import PageObject.OrderFirstPage;
import PageObject.OrderSecondPage;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderTests {
    private WebDriver driver;
    private MainPage mainPage;
    private OrderFirstPage orderFirstPage;
    private OrderSecondPage orderSecondPage;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String color;

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
        driver.get("https://qa-scooter.praktikum-services.ru/");
        mainPage = new MainPage(driver);
        orderFirstPage = new OrderFirstPage(driver);
        orderSecondPage = new OrderSecondPage(driver);
    }

    @Test
    public void testOrderFromHeaderButton() {
        mainPage.clickOrderButtonHeader();
        fillOrderAndConfirm();
    }

    @Test
    public void testOrderFromBottomButton() {
        mainPage.clickOrderButtonBottom();
        fillOrderAndConfirm();
    }

    private void fillOrderAndConfirm() {
        // Заполнение первой страницы заказа
        orderFirstPage.setFirstName(firstName);
        orderFirstPage.setLastName(lastName);
        orderFirstPage.setAddress(address);
        orderFirstPage.setMetroStation(metroStation);
        orderFirstPage.setPhoneNumber(phoneNumber);
        orderFirstPage.clickNextButton();

        // Переход на вторую страницу
        orderSecondPage.waitForPageLoad();

        // Заполнение второй страницы заказа
        orderSecondPage.setDeliveryDate(deliveryDate);
        orderSecondPage.setRentalPeriod(rentalPeriod);
        orderSecondPage.selectColor(color);
        orderSecondPage.clickOrderButton();
        orderSecondPage.clickConfirmOrderButton();

        // Проверка подтверждения заказа
        String confirmationMessage = orderSecondPage.getOrderConfirmationMessage();
        assertTrue("Сообщение об успешном заказе не отображается",
                confirmationMessage.contains("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}