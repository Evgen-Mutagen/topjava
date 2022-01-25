package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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

        /* List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(22, 0), 2000);
        mealsTo.forEach(System.out::println);*/
        List<UserMealWithExcess> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(22, 0), 2000);
        mealsTo.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesByDate = new HashMap<>();
        List<UserMealWithExcess> listWithExcess;
        for (UserMeal meal : meals) {
            sumOfCaloriesByDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }
        listWithExcess = new ArrayList<>();
        for (UserMeal meal : meals) {
            LocalDateTime dateEat = meal.getDateTime();
            if (!TimeUtil.isBetweenHalfOpen(dateEat.toLocalTime(), startTime, endTime)) {
                continue;
            }
            listWithExcess.add(new UserMealWithExcess(dateEat, meal.getDescription(), meal.getCalories(),
                    sumOfCaloriesByDate.get(dateEat.toLocalDate()) > caloriesPerDay));
        }
        return listWithExcess;
    }


    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumOfCaloriesByDate = meals.stream()
                .collect(Collectors.toMap((UserMeal m) -> m.getDateTime().toLocalDate(),
                        UserMeal::getCalories, Integer::sum, HashMap::new));

        return meals.stream()
                .filter(m -> isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> (new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(),
                        userMeal.getCalories(), sumOfCaloriesByDate.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());
    }
}
