package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

       // List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(22, 0), 2000);
      //  mealsTo.forEach(System.out::println);
        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(22, 0), 2000);
        mealsTo.forEach(System.out::println);
//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesByDate  = new HashMap<>();
        List<UserMealWithExcess> list = new ArrayList<>();
        for (UserMeal meal : meals) {
            sumOfCaloriesByDate .merge(meal.getDateTime().toLocalDate(), meal.getCalories(), (a, b) -> Integer.sum(a, b));
        }
        for (UserMeal meal : meals) {
            LocalDateTime dateTime = meal.getDateTime();
            if (!TimeUtil.isBetweenHalfOpen(dateTime.toLocalTime(), startTime, endTime)) {
                continue;
            }
            list.add(new UserMealWithExcess(dateTime, meal.getDescription(), meal.getCalories(),
                    sumOfCaloriesByDate .get(dateTime.toLocalDate()) >= caloriesPerDay));
        }
        return list;
    }



    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesByDate = meals.stream()
                .filter(x -> isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(() -> new HashMap<LocalDate, Integer>(),
                        (map, item) -> map.merge(item.getDateTime().toLocalDate(), item.getCalories(), (a, b) -> Integer.sum(a, b)),
                        (a, b) -> {
                        });
        return meals.stream()
                .filter(x -> isBetweenHalfOpen(x.getDateTime().toLocalTime(), startTime, endTime))
                .collect(
                        () -> new ArrayList<>(),
                        (list, meal) -> list.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                sumOfCaloriesByDate.get(meal.getDateTime().toLocalDate()) <= caloriesPerDay)),
                        (a, b) -> {
                        });
    }
}
