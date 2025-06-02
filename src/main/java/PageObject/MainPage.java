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
    private WebDriver driver;

    // Улучшенные локаторы элементов
    private By orderButtonHeader = By.xpath(".//button[text()='Заказать' and contains(@class, 'Button_Button__ra12g')]");
    private By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");
    private By question1Button = By.id("accordion__heading-0");
    private By question1Answer = By.cssSelector("#accordion__panel-0 > p");
    private By cookieConsentButton = By.id("rcc-confirm-button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void acceptCookies() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        List<WebElement> consentElements = driver.findElements(cookieConsentButton);
        if (!consentElements.isEmpty() && consentElements.get(0).isDisplayed()) {
            consentElements.get(0).click();
        }
    }

    public void clickOrderButtonHeader() {
        acceptCookies(); // Гарантируем закрытие куки-баннера
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(orderButtonHeader)).click();
    }

    public void clickOrderButtonBottom() {
        acceptCookies(); // Гарантируем закрытие куки-баннера

        // Прокрутка к кнопке перед кликом
        WebElement button = driver.findElement(orderButtonBottom);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", button);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(button)).click();
    }

    public void clickQuestion1() {
        acceptCookies(); // Гарантируем закрытие куки-баннера

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(question1Button));

        // Прокрутка и клик
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    public String getAnswer1Text() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(question1Answer)).getText();
    }
}
