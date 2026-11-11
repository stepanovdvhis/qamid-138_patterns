package delivery;

import com.codeborne.selenide.Configuration;
import data.DeliveryData;
import page.DeliveryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        // Убрали настройки (headless, timeout, browserSize), как просил преподаватель
        Configuration.browser = "firefox";
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDelivery() {
        DeliveryData data = DataGenerator.generateData();
        DeliveryPage page = new DeliveryPage();

        // 1. Первое планирование
        page.fillForm(data);

        // Проверяем уведомление об успехе и наличие даты в тексте
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Успешно"))
                .shouldHave(text(data.getDate())); // Проверяем, что дата есть в сообщении

        // 2. Перепланирование
        open("http://localhost:9999");

        // Генерируем новую дату
        LocalDate newDate = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String newDateStr = newDate.format(formatter);

        DeliveryData replanData = new DeliveryData(data.getCity(), data.getName(), data.getPhone(), newDateStr);
        page.fillForm(replanData);

        // Проверяем уведомление о перепланировании
        $("[data-test-id='replan-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Перепланировать"));

        // Нажимаем кнопку
        $("[data-test-id='replan-notification'] button")
                .shouldBe(visible, Duration.ofSeconds(10))
                .click();

        // Проверяем успех и наличие новой даты
        $("[data-test-id='success-notification']")
                .shouldBe(visible, Duration.ofSeconds(10))
                .shouldHave(text("Успешно"))
                .shouldHave(text(newDateStr)); // Проверяем новую дату
    }
}