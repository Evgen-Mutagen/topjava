package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(1, meal));
        save(2, new Meal(LocalDateTime.now(), "Пышки", 2000));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save userId= {} meal= {}", userId, meal);
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMeals.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete userId= {} id= {}", userId, id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return  userMeals != null && !(userMeals.remove(id) == null);
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get userId= {} id= {}", userId, id);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null ? userMeals.get(id) : null;
    }


    @Override
    public List<Meal> getByDate(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getFiltered(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDateTime(), startDateTime, endDateTime));
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId= {} ", userId);
        return getFiltered(userId, meal -> true);
    }

    private  List<Meal> getFiltered(int userId, Predicate<Meal> filter) {
        log.info("getFiltered userId= {} filter= {}", userId, filter);
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? Collections.emptyList() : userMeals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

