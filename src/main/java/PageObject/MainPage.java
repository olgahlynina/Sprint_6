package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Локаторы элементов главной страницы:

    // Кнопка "Заказать" в верхнем блоке страницы
    private final By orderButtonHeader = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Button__ra12g')]");

    // Кнопка "Заказать" в нижнем блоке страницы
    private final By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");

    // Кнопка принятия куки
    private final By cookieConsentButton = By.id("rcc-confirm-button");

    // Блок часто задаваемых вопросов
    private final By faqSection = By.className("Home_FAQ__3uVm4");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Методы взаимодействия с элементами...

    public void clickOrderButtonHeader() {
        acceptCookies();
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonHeader)).click();
    }

    public void clickOrderButtonBottom() {
        acceptCookies();
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    public void clickQuestion(int index) {
        acceptCookies();
        By questionButton = By.id("accordion__heading-" + index);
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(questionButton));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public String getAnswerText(int index) {
        By answer = By.cssSelector("#accordion__panel-" + index + " > p");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(answer)).getText();
    }

    private void acceptCookies() {
        List<WebElement> consentElements = driver.findElements(cookieConsentButton);
        if (!consentElements.isEmpty() && consentElements.get(0).isDisplayed()) {
            consentElements.get(0).click();
        }
    }
}