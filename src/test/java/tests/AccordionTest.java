package tests;
import org.junit.Assert;
import org.junit.Test;
import pages.MainPage;

public class AccordionTest extends BaseTest {

    @Test
    public void testAccordionItems() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int accordionCount = mainPage.getAccordionButtons().size();
        Assert.assertTrue("Аккордеон должен содержать элементы", accordionCount > 0);

        for (int i = 0; i < 3 && i < accordionCount; i++) {
            try {
                org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
                js.executeScript("arguments[0].scrollIntoView(true);",
                        mainPage.getAccordionButtons().get(i));
                Thread.sleep(300);
            } catch (Exception e) {
            }

            mainPage.clickAccordionButton(i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            boolean isDisplayed = mainPage.isAccordionPanelDisplayed(i);
            Assert.assertTrue("Ответ должен отображаться после клика на вопрос " + (i + 1), isDisplayed);

            String panelText = mainPage.getAccordionPanelText(i);
            Assert.assertFalse("Текст ответа не должен быть пустым", panelText.isEmpty());

            mainPage.clickAccordionButton(i);
        }
    }
}
