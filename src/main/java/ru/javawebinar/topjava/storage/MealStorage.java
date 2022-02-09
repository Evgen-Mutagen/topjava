package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    Meal save(Meal meal);

    Meal get(int uuid);

    void delete(int uuid);

    List<Meal> getAll();
}
