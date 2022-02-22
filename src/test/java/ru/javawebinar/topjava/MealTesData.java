package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTesData {


    public static final Meal MEAL1 = new Meal(START_SEQ +1, LocalDateTime.of(2020, 1, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(START_SEQ +2, LocalDateTime.of(2020, 1, 30, 22, 0), "Ужин ", 410);
    public static final Meal MEAL3 = new Meal(START_SEQ +3, LocalDateTime.of(2020, 1, 31, 10, 0), "Завтрак админа", 350);
    public static final Meal MEAL4 = new Meal(START_SEQ +4, LocalDateTime.of(2020, 1, 31, 14, 0), "Обед админа", 500);

    public static Meal getNewMeal() {
        return new Meal(null, LocalDateTime.of(2022, 2, 21, 23, 49, 15),
                "пышки", 999);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
