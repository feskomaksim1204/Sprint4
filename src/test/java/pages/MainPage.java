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

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private final By orderButtonTop = By.className("Button_Button__ra12g");
    private final By orderButtonBottom = By.xpath("//div[contains(@class, 'Home_FinishButton')]//button");
    private final By cookieButton = By.id("rcc-confirm-button");
    private final By accordionButtons = By.className("accordion__button");

    public void acceptCookies() {
        try {
            WebElement cookie = wait.until(ExpectedConditions.elementToBeClickable(cookieButton));
            cookie.click();
        } catch (Exception e) {
            // Игнорируем если кнопки нет
        }
    }

    public void clickOrderButtonTop() {
        try {
            acceptCookies();
            Thread.sleep(1000);

            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(orderButtonTop));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", button);
            Thread.sleep(500);
            button.click();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            try {
                By altButton = By.xpath("//button[text()='Заказать']");
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(altButton));
                button.click();
            } catch (Exception ex) {
                throw new RuntimeException("Не удалось найти верхнюю кнопку 'Заказать'", ex);
            }
        }
    }

    public void scrollAndClickOrderButtonBottom() {
        WebElement bottomButton = driver.findElement(orderButtonBottom);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", bottomButton);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        wait.until(ExpectedConditions.elementToBeClickable(orderButtonBottom)).click();
    }

    public List<WebElement> getAccordionButtons() {
        return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(accordionButtons));
    }

    public void clickAccordionButton(int index) {
        List<WebElement> buttons = getAccordionButtons();
        if (index < buttons.size()) {
            buttons.get(index).click();
        }
    }

    public String getAccordionPanelText(int index) {
        String panelXpath = "(//div[@class='accordion__item'])[" + (index + 1) + "]//div[@class='accordion__panel']";
        try {
            WebElement panel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(panelXpath)));
            return panel.getText();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isAccordionPanelDisplayed(int index) {
        String panelXpath = "(//div[@class='accordion__item'])[" + (index + 1) + "]//div[@class='accordion__panel']";
        try {
            WebElement panel = driver.findElement(By.xpath(panelXpath));
            return panel.isDisplayed() && !panel.getText().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}