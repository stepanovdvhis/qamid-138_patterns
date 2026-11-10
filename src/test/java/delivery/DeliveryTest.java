package delivery;

import com.codeborne.selenide.Configuration;
import data.DeliveryData;
import page.DeliveryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";
        Configuration.headless = true;
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanDelivery() {
        // 1. Генерируем данные
        DeliveryData data = DataGenerator.generateData();
        DeliveryPage page = new DeliveryPage();

        // 2. Заполняем форму первый раз
        page.fillForm(data);

        // 3. Проверяем уведомление об успешном планировании
        $("[data-test-id='success-notification']").shouldBe(visible);

        // 4. Открываем форму снова
        open("http://localhost:9999");

        // 5. Генерируем новую дату (на 5 дней позже)
        LocalDate newDate = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String newDateStr = newDate.format(formatter);

        // 6. Заполняем форму повторно с новой датой
        DeliveryData replanData = new DeliveryData(data.getCity(), data.getName(), data.getPhone(), newDateStr);
        page.fillForm(replanData);

        // 7. Проверяем, что появилось уведомление о перепланировании
        $("[data-test-id='replan-notification']").shouldBe(visible);

        // 8. Нажимаем кнопку "Перепланировать" внутри уведомления
        $("[data-test-id='replan-notification'] button").shouldBe(visible).click();

        // 9. Проверяем уведомление об успешном перепланировании
        $("[data-test-id='success-notification']").shouldBe(visible);
    }
}