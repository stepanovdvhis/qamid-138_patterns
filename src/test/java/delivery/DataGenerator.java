package delivery;

import com.github.javafaker.Faker;
import data.DeliveryData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static DeliveryData generateData() {
        Faker faker = new Faker(new Locale("ru"));
        Random random = new Random();

        String[] cities = {"Москва", "Санкт-Петербург", "Казань", "Екатеринбург", "Новосибирск"};
        String city = cities[random.nextInt(cities.length)];

        String name = faker.name().fullName();
        String phone = faker.phoneNumber().phoneNumber();

        String dateStr = generateNewDate();

        return new DeliveryData(city, name, phone, dateStr);
    }

    // Метод для генерации новой даты (вынесен сюда по требованию преподавателя)
    public static String generateNewDate() {
        LocalDate date = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }
}