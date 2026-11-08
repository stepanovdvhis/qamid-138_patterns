package page;

import com.codeborne.selenide.ClickOptions;
import data.DeliveryData;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.visible;

public class DeliveryPage {
    public DeliveryPage fillForm(DeliveryData data) {
        $$("input").get(0).shouldBe(visible).setValue(data.getCity());
        com.codeborne.selenide.Selenide.sleep(500);

        $$("input").get(1).shouldBe(visible).setValue(data.getDate());
        $$("input").get(2).shouldBe(visible).setValue(data.getName());
        $$("input").get(3).shouldBe(visible).setValue(data.getPhone());

        if ($$("input[type='checkbox']").size() > 0) {
            $$("input[type='checkbox']").get(0).click(ClickOptions.usingJavaScript());
        }

        $(".button_view_extra").shouldBe(visible).click(ClickOptions.usingJavaScript());

        return this;
    }
}