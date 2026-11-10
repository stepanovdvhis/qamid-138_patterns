package page;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ClickOptions;
import data.DeliveryData;

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
        cityField.shouldBe(visible).setValue(data.getCity());
        dateField.shouldBe(visible).setValue(data.getDate());
        nameField.shouldBe(visible).setValue(data.getName());
        phoneField.shouldBe(visible).setValue(data.getPhone());

        // Клик с использованием JavaScript, чтобы обойти перекрытия (выпадающие списки)
        agreementCheckbox.click(ClickOptions.usingJavaScript());
        submitButton.shouldBe(visible).click(ClickOptions.usingJavaScript());

        return this;
    }
}