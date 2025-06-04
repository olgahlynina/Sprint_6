package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderFirstPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов страницы оформления заказа (шаг 1):

    // Заголовок страницы "Для кого самокат"
    private final By pageHeader = By.xpath("//div[contains(text(), 'Для кого самокат')]");

    // Поле ввода "Имя"
    private final By firstNameField = By.xpath("//input[@placeholder='* Имя']");

    // Поле ввода "Фамилия"
    private final By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");

    // Поле ввода "Адрес"
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");

    // Поле выбора "Станция метро"
    private final By metroStationField = By.cssSelector("[placeholder='* Станция метро']");

    // Поле ввода "Телефон"
    private final By phoneNumberField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее"
    private final By nextButton = By.xpath("//button[text()='Далее']");

    public OrderFirstPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Методы взаимодействия с элементами...

    public void setFirstName(String firstName) {
        setField(firstNameField, firstName);
    }

    public void setLastName(String lastName) {
        setField(lastNameField, lastName);
    }

    public void setAddress(String address) {
        setField(addressField, address);
    }

    public void setMetroStation(String metroStation) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(metroStationField));
        input.click();
        input.sendKeys(metroStation);

        // Локатор опции в выпадающем списке метро
        By stationOption = By.xpath("//div[contains(@class, 'select-search__select')]//*[text()='" + metroStation + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(stationOption)).click();
    }

    public void setPhoneNumber(String phoneNumber) {
        setField(phoneNumberField, phoneNumber);
    }

    public void clickNextButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        button.click();
    }

    private void setField(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(value);
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageHeader));
    }
}