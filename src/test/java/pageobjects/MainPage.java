package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Кнопка «Заказать» вверху
    @FindBy(xpath = ".//button[text()='Заказать']")
    private WebElement orderButtonTop;

    // Кнопка «Заказать» внизу
    @FindBy(xpath = ".//div[@class='Home_FinishButton__1_cWm']/button")
    private WebElement orderButtonBottom;

    // Кнопка принятия куки
    @FindBy(id = "rcc-confirm-button")
    private WebElement cookieButton;

    public void clickOrderButtonTop() {
        orderButtonTop.click();
    }

    public void clickOrderButtonBottom() {
        orderButtonBottom.click();
    }

    public void acceptCookies() {
        if (cookieButton.isDisplayed()) {
            cookieButton.click();
        }
    }

    public void open() {
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }
}