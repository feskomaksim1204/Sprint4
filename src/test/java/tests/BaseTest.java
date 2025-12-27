package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            String firefoxPath = System.getProperty("firefox.binary.path");
            if (firefoxPath == null || firefoxPath.trim().isEmpty()) {
                firefoxPath = System.getenv("FIREFOX_BINARY_PATH");
            }

            if (firefoxPath != null && !firefoxPath.trim().isEmpty()) {
            firefoxPath = firefoxPath.replace("\"", "").trim();
                options.setBinary(firefoxPath);
            }

            driver = new FirefoxDriver(options);
        } else {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments(
                    "--remote-allow-origins=*",
                    "--no-sandbox",
                    "--disable-dev-shm-usage",
                    "--window-size=1920,1080"
            );
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}