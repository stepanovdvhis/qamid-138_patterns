package page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ClickOptions;
import data.DeliveryData;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

public class DeliveryPage {
    private SelenideElement cityField = $("span[data-test-id='city'] input");
    private SelenideElement dateField = $("span[data-test-id='date'] input");
    private SelenideElement nameField = $("span[data-test-id='name'] input");
    private SelenideElement phoneField = $("span[data-test-id='phone'] input");
    private SelenideElement agreementCheckbox = $("[data-test-id='agreement'] input");
    private SelenideElement submitButton = $("button.button_view_extra");

    public DeliveryPage fillForm(DeliveryData data) {
        // Указываем таймаут в ассертах
        cityField.shouldBe(visible, Duration.ofSeconds(10)).setValue(data.getCity());
        dateField.shouldBe(visible, Duration.ofSeconds(10)).setValue(data.getDate());
        nameField.shouldBe(visible, Duration.ofSeconds(10)).setValue(data.getName());
        phoneField.shouldBe(visible, Duration.ofSeconds(10)).setValue(data.getPhone());

        agreementCheckbox.click(ClickOptions.usingJavaScript());
        submitButton.shouldBe(visible, Duration.ofSeconds(10)).click(ClickOptions.usingJavaScript());

        return this;
    }
}