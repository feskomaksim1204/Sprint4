package tests;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
import tests.TestData;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class OrderTest extends BaseTest {

    private final String testName;
    private final boolean useTopButton;
    private final String[] userData;

    public OrderTest(String testName, boolean useTopButton, String[] userData) {
        this.testName = testName;
        this.useTopButton = useTopButton;
        this.userData = userData;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {
                        "Заказ через ВЕРХНЮЮ кнопку - первый набор данных",
                        true,
                        new String[] {
                                TestData.User.NAME,
                                TestData.User.SURNAME,
                                TestData.User.ADDRESS,
                                TestData.User.METRO_STATION,
                                TestData.User.PHONE,
                                TestData.User.DATE,
                                TestData.User.RENTAL_PERIOD,
                                TestData.User.COLOR,
                                TestData.User.COMMENT
                        }
                },
                {
                        "Заказ через НИЖНЮЮ кнопку - второй набор данных",
                        false,
                        new String[] {
                                TestData.SecondUser.NAME,
                                TestData.SecondUser.SURNAME,
                                TestData.SecondUser.ADDRESS,
                                TestData.SecondUser.METRO_STATION,
                                TestData.SecondUser.PHONE,
                                TestData.SecondUser.DATE,
                                TestData.SecondUser.RENTAL_PERIOD,
                                TestData.SecondUser.COLOR,
                                TestData.SecondUser.COMMENT
                        }
                }
        });
    }

    @Test
    public void testOrderWithParameters() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.acceptCookies();

        if (useTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.scrollAndClickOrderButtonBottom();
        }

        orderPage.fillFirstPage(
                userData[0],
                userData[1],
                userData[2],
                userData[3],
                userData[4]
        );
        orderPage.clickNextButton();

        orderPage.fillSecondPage(
                userData[5],
                userData[6],
                userData[7],
                userData[8]
        );

        orderPage.clickOrderButton();
        orderPage.confirmOrder();

        Assert.assertTrue("Должно появиться окно успешного оформления заказа",
                orderPage.isOrderSuccessDisplayed());
    }
}