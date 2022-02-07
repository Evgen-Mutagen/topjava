package ru.javawebinar.topjava.storage;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapStorage implements Storage {
    private static final Map<Integer, Meal> storageMeal = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    static {
        storageMeal.put(0, new Meal(0, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        storageMeal.put(1, new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        storageMeal.put(2, new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        storageMeal.put(3, new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        storageMeal.put(4, new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        storageMeal.put(5, new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        storageMeal.put(6, new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public void save(Meal meal) {
        meal.setUuid(counter.incrementAndGet());
        storageMeal.put(meal.getUuid(), meal);
    }

    @Override
    public void update(Meal meal) {
        storageMeal.merge(meal.getUuid(), meal, (meal1, meal2) -> meal2);
    }

    @Override
    public Meal get(int uuid) {
        return storageMeal.get(uuid);
    }

    @Override
    public void delete(int uuid) {
        storageMeal.remove(uuid);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storageMeal.values());
    }
}
