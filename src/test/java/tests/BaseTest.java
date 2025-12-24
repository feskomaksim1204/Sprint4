package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.File;
import java.io.IOException;
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
            setFirefoxPath(options);
            driver = new FirefoxDriver(options);

        } else {
            // Закрываем все процессы Chrome перед запуском тестов
            try {
                Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
                // Даем время на завершение процессов
                Thread.sleep(1000);
            } catch (IOException | InterruptedException e) {
                // Игнорируем, если Chrome не был запущен
                Thread.currentThread().interrupt();
            }

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

    private void setFirefoxPath(FirefoxOptions options) {
        String programFiles = System.getenv("ProgramFiles");
        if (programFiles != null) {
            String path1 = programFiles + "\\Mozilla Firefox\\firefox.exe";
            if (new File(path1).exists()) {
                options.setBinary(path1);
                return;
            }
        }

        String programFilesX86 = System.getenv("ProgramFiles(x86)");
        if (programFilesX86 != null) {
            String path2 = programFilesX86 + "\\Mozilla Firefox\\firefox.exe";
            if (new File(path2).exists()) {
                options.setBinary(path2);
                return;
            }
        }
        // Если не нашли - Selenium сам найдёт
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
