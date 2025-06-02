package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage1 {
    private WebDriver driver;
    private WebDriverWait wait;

    // Улучшенные локаторы
    private By firstNameField = By.xpath("//input[@placeholder='* Имя']");
    private By lastNameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroStationField = By.cssSelector("[placeholder='* Станция метро']");
    private By phoneNumberField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    public OrderPage1(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void setFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(firstNameField));
        element.clear();
        element.sendKeys(firstName);
    }

    public void setLastName(String lastName) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(lastNameField));
        element.clear();
        element.sendKeys(lastName);
    }

    public void setAddress(String address) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(addressField));
        element.clear();
        element.sendKeys(address);
    }

    public void setMetroStation(String metroStation) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(metroStationField));
        input.click();
        input.sendKeys(metroStation);

        // Ожидание и выбор конкретной станции
        By stationOption = By.xpath("//div[contains(@class, 'select-search__select')]//*[text()='" + metroStation + "']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(stationOption)).click();
    }

    public void setPhoneNumber(String phoneNumber) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(phoneNumberField));
        element.clear();
        element.sendKeys(phoneNumber);
    }

    public void clickNextButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        button.click();

        // Ожидание перехода на следующую страницу
        wait.until(ExpectedConditions.invisibilityOfElementLocated(nextButton));
    }

    public void fillForm(String firstName, String lastName, String address, String metroStation, String phoneNumber) {
        setFirstName(firstName);
        setLastName(lastName);
        setAddress(address);
        setMetroStation(metroStation);
        setPhoneNumber(phoneNumber);
    }
}
