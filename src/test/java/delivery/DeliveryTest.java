package delivery;

import com.codeborne.selenide.Configuration;
import data.DeliveryData;
import page.DeliveryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDelivery() {
        DeliveryData data = DataGenerator.generateData();
        DeliveryPage page = new DeliveryPage();

        // 1. Первое планирование
        page.fillForm(data);

        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + data.getDate()));

        // 2. Перепланирование
        open("http://localhost:9999");

        // Генерируем новую дату через DataGenerator (как просил преподаватель)
        String newDateStr = DataGenerator.generateNewDate();

        DeliveryData replanData = new DeliveryData(data.getCity(), data.getName(), data.getPhone(), newDateStr);
        page.fillForm(replanData);

        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Перепланировать"));

        $("[data-test-id='replan-notification'] button")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Успешно! Встреча успешно запланирована на " + newDateStr));
    }
}