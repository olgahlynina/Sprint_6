package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderSecondPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов страницы оформления заказа (шаг 2):

    // Заголовок страницы "Про аренду"
    private final By rentHeader = By.xpath("//div[contains(text(), 'Про аренду')]");

    // Поле ввода "Когда привезти самокат"
    private final By deliveryDateField = By.cssSelector("[placeholder='* Когда привезти самокат']");

    // Выпадающий список "Срок аренды"
    private final By rentalPeriodDropdown = By.cssSelector(".Dropdown-control");

    // Чекбокс "Чёрный жемчуг"
    private final By blackColorCheckbox = By.id("black");

    // Чекбокс "Серая безысходность"
    private final By greyColorCheckbox = By.id("grey");

    // Поле для комментария
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");

    // Кнопка "Заказать"
    private final By orderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle')]");

    // Модальное окно подтверждения заказа
    private final By confirmationModal = By.className("Order_Modal__YZ-d3");

    // Кнопка "Да" в модальном окне
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");

    // Сообщение об успешном оформлении заказа
    private final By orderConfirmationMessage = By.cssSelector(".Order_ModalHeader__3FDaJ");

    public OrderSecondPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Методы взаимодействия с элементами...

    public void setDeliveryDate(String deliveryDate) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(deliveryDateField));
        element.clear();
        element.sendKeys(deliveryDate);
        element.sendKeys(Keys.ENTER);
    }

    public void setRentalPeriod(String period) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(rentalPeriodDropdown));
        dropdown.click();

        // Локатор опции в выпадающем списке периода аренды
        By periodOption = By.xpath("//div[contains(@class, 'Dropdown-option') and text()='" + period + "']");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption)).click();
    }

    public void selectColor(String color) {
        if (color.equalsIgnoreCase("black")) {
            clickCheckbox(blackColorCheckbox);
        } else if (color.equalsIgnoreCase("grey")) {
            clickCheckbox(greyColorCheckbox);
        }
    }

    public void clickOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmationModal));
    }

    public void clickConfirmOrderButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        button.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationMessage));
    }

    public String getOrderConfirmationMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderConfirmationMessage)).getText();
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(rentHeader));
    }

    private void clickCheckbox(By locator) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(locator));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }
}