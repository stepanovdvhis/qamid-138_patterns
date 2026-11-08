package delivery;

import com.codeborne.selenide.Configuration;
import data.DeliveryData;
import page.DeliveryPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        sleep(2000);

        // 3. Открываем форму снова
        open("http://localhost:9999");

        // 4. Готовим данные для перепланирования (меняем дату)
        String newDate = "25.12.2026";
        DeliveryData replanData = new DeliveryData(data.getCity(), data.getName(), data.getPhone(), newDate);

        // 5. Заполняем форму повторно
        page.fillForm(replanData);
        sleep(2000);

        // 6. Нажимаем кнопку "Перепланировать" (вторая кнопка с классом button_view_extra)
        $$(".button_view_extra").get(1).shouldBe(visible).click();

        // 7. Ждем завершения
        sleep(2000);
    }
}