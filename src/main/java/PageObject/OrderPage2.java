package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage2 {
    private WebDriver driver;
    private WebDriverWait wait;

    // Улучшенные локаторы
    private By deliveryDateField = By.cssSelector("[placeholder='* Когда привезти самокат']");
    private By rentalPeriodDropdown = By.cssSelector(".Dropdown-control");
    private By blackColorCheckbox = By.id("black");
    private By greyColorCheckbox = By.id("grey");
    private By orderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle')]");
    private By confirmOrderButton = By.xpath("//button[text()='Да']");
    private By orderConfirmationMessage = By.cssSelector(".Order_ModalHeader__3FDaJ");

    public OrderPage2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void setDeliveryDate(String deliveryDate) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField));
        element.clear();
        element.sendKeys(deliveryDate);
        element.sendKeys(Keys.ENTER); // Закрыть календарь
    }

    public void setRentalPeriod(String period) {
        // Раскрытие выпадающего списка
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        dropdown.click();

        // Выбор конкретного периода
        By periodOption = By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + period + "']");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption)).click();
    }

    public void selectColor(String color) {
        if (color.equalsIgnoreCase("black")) {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(blackColorCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        } else if (color.equalsIgnoreCase("grey")) {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(greyColorCheckbox));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    public void clickOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        button.click();

        // Ожидание появления модального окна подтверждения
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmOrderButton));
    }

    public void clickConfirmOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        button.click();

        // Ожидание появления сообщения о подтверждении
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationMessage));
    }

    public String getOrderConfirmationMessage() {
        WebElement message = wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationMessage));
        return message.getText();
    }

    public void fillForm(String deliveryDate, String rentalPeriod, String color) {
        setDeliveryDate(deliveryDate);
        setRentalPeriod(rentalPeriod);
        selectColor(color);
    }
}
