package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // Локаторы первой страницы
    private final By nameField = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButton = By.xpath("//button[text()='Далее']");

    // Локаторы второй страницы
    private final By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodField = By.className("Dropdown-placeholder");
    private final By colorBlackCheckbox = By.id("black");
    private final By colorGreyCheckbox = By.id("grey");
    private final By commentField = By.xpath("//input[@placeholder='Комментарий для курьера']");
    private final By orderButton = By.xpath("//button[text()='Заказать' and contains(@class, 'Button_Middle__1CSJM')]");

    // Локаторы модального окна
    private final By confirmOrderButton = By.xpath("//button[text()='Да']");
    private final By orderSuccessModal = By.xpath("//div[contains(@class, 'Order_ModalHeader') and contains(text(), 'Заказ оформлен')]");

    // Методы для заполнения первой страницы
    public void fillFirstPage(String name, String surname, String address, String metroStation, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));

        driver.findElement(nameField).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);

        driver.findElement(metroField).click();
        String metroOptionXpath = "//div[@class='select-search__select']//*[text()='" + metroStation + "']";
        WebElement metroOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(metroOptionXpath)));
        metroOption.click();

        driver.findElement(phoneField).sendKeys(phone);
    }

    public void clickNextButton() {
        driver.findElement(nextButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField));
    }

    // Методы для заполнения второй страницы
    public void fillSecondPage(String date, String period, String color, String comment) {
        WebElement dateElement = driver.findElement(dateField);
        dateElement.clear();
        dateElement.sendKeys(date);
        dateElement.sendKeys(Keys.ESCAPE);

        driver.findElement(rentalPeriodField).click();

        int optionIndex = 1;
        if (period.contains("двое суток")) {
            optionIndex = 2;
        }

        String optionXpath = "(//div[contains(@class, 'Dropdown-option')])[" + optionIndex + "]";
        WebElement periodOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(optionXpath)));
        periodOption.click();

        if ("black".equals(color)) {
            driver.findElement(colorBlackCheckbox).click();
        } else if ("grey".equals(color)) {
            driver.findElement(colorGreyCheckbox).click();
        }

        driver.findElement(commentField).sendKeys(comment);
    }

    public void clickOrderButton() {
        driver.findElement(orderButton).click();
    }

    public void confirmOrder() {
        WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderButton));
        confirmButton.click();
    }

    public boolean isOrderSuccessDisplayed() {
        WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(orderSuccessModal));
        return modal.isDisplayed();
    }
}
