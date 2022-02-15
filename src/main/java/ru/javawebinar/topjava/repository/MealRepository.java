package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;


public interface MealRepository {
    // null if updated meal does not belong to userId
    Meal save(int userId, Meal meal);

    boolean delete(int userId, int id);

    Meal get(int userId, int id);

    Collection<Meal> getByDate(int userId, LocalDate startDate, LocalDate endDate);

    Collection<Meal> getAll(int userId);
}
