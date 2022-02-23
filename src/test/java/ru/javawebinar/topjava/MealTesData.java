package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTesData {

    public static final Meal userMeal1 = new Meal(100_003, LocalDateTime.of(2020, 1, 30, 7, 0), "Завтрак", 800);
    public static final Meal userMeal2 = new Meal(100_004, LocalDateTime.of(2020, 1, 30, 12, 0), "Обед ", 1000);
    public static final Meal userMeal3 = new Meal(100_005, LocalDateTime.of(2020, 1, 30, 19, 0), "Ужин", 300);
    public static final Meal adminMeal1 = new Meal(100_006, LocalDateTime.of(2020, 1, 30, 7, 0), "Завтрак", 300);
    public static final Meal adminMeal2 = new Meal(100_007, LocalDateTime.of(2020, 1, 30, 12, 0), "Обед ", 500);
    public static final Meal adminMeal3 = new Meal(100_008, LocalDateTime.of(2020, 1, 30, 19, 0), "Ужин ", 300);
    public static final Meal adminMeal4 = new Meal(100_009, LocalDateTime.of(2020, 1, 31, 7, 0), "Завтрак", 800);
    public static final Meal adminMeal5 = new Meal(100_010, LocalDateTime.of(2020, 1, 31, 12, 0), "Обед", 500);
    public static final Meal adminMeal6 = new Meal(100_011, LocalDateTime.of(2020, 1, 31, 19, 0), "Ужин", 800);

    public static Meal getNewMeal() {
        return new Meal(LocalDateTime.of(2022, 2, 21, 23, 0), "пышки", 999);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingDefaultElementComparator().isEqualTo(expected);
    }
}
