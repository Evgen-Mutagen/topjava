package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MealRepository {

    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getByDate(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    List<Meal> getAll(int userId);
}
