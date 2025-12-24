package tests;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pages.MainPage;
import pages.OrderPage;
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

        // Шаг 1: Нажать на кнопку "Заказать"
        if (useTopButton) {
            mainPage.clickOrderButtonTop();
        } else {
            mainPage.clickOrderButtonBottom();
        }

        // Шаг 2: Заполнить первую страницу заказа
        orderPage.fillFirstPage(
                userData[0], // имя
                userData[1], // фамилия
                userData[2], // адрес
                userData[3], // станция метро
                userData[4]  // телефон
        );

        // Шаг 3: Перейти на вторую страницу
        orderPage.clickNextButton();

        // Шаг 4: Заполнить вторую страницу заказа
        orderPage.fillSecondPage(
                userData[5], // дата
                userData[6], // срок аренды
                userData[7], // цвет
                userData[8]  // комментарий
        );

        // Шаг 5: Нажать кнопку "Заказать"
        orderPage.clickOrderButton();

        // Шаг 6: Подтвердить заказ
        orderPage.confirmOrder();

        // Шаг 7: Проверить успешное оформление
        Assert.assertTrue("Должно появиться окно успешного оформления заказа",
                orderPage.isOrderSuccessDisplayed());
    }
}