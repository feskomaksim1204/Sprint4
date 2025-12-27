package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // Локаторы
    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By accordionButtons = By.className("accordion__button");

    // Методы
    public void acceptCookies() {
        try {
            WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookie.click();
        } catch (Exception e) {
            // Игнорируем если кнопки нет
        }
    }

    public void clickOrderButtonTop() {
        acceptCookies();
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
        js.executeScript("arguments[0].scrollIntoView(true);", button);
        button.click();
    }

    public void clickOrderButtonBottom() {
        acceptCookies();
        WebElement button = wait.until(ExpectedConditions.presenceOfElementLocated(orderButtonBottom));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        js.executeScript("arguments[0].click();", button);
    }

    public List<WebElement> getAccordionButtons() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionButtons));
    }

    public void scrollToAccordionSection() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionButtons));
    }

    public void clickAccordionButton(int index) {
        List<WebElement> buttons = getAccordionButtons();
        if (index < buttons.size()) {
            WebElement button = buttons.get(index);

            scrollToAccordionSection();
            wait.until(ExpectedConditions.elementToBeClickable(button));
            button.click();

            String panelXpath = "(//div[@class='accordion__item'])[" + (index + 1) + "]//div[@class='accordion__panel']";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(panelXpath)));
        }
    }

    public String getAccordionPanelText(int index) {
        String panelXpath = "(//div[@class='accordion__item'])[" + (index + 1) + "]//div[@class='accordion__panel']";
        WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(panelXpath)));
        return panel.getText();
    }

    public boolean isAccordionPanelDisplayed(int index) {
        String panelXpath = "(//div[@class='accordion__item'])[" + (index + 1) + "]//div[@class='accordion__panel']";
        WebElement panel = driver.findElement(By.xpath(panelXpath));
        return panel.isDisplayed() && !panel.getText().isEmpty();
    }
}