package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTesData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(userMeal1.getId(), USER_ID);
        assertMatch(meal, userMeal1);
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(userMeal2.getId(), ADMIN_ID));
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(300_000, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(adminMeal1.getId(), ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(adminMeal1.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(100_1000, ADMIN_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(userMeal1.getId(), ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 30), USER_ID), userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void getBetweenWithNullDates() {
       assertMatch(service.getBetweenInclusive(null, null, USER_ID), userMeal3, userMeal2, userMeal1);
    }


    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, userMeal3, userMeal2, userMeal1);
    }

    @Test
    public void update() {
        Meal meal = service.get(userMeal2.getId(), USER_ID);
        meal.setCalories(256);
        meal.setDescription("пирожики");
        meal.setDateTime(LocalDateTime.of(2021, 1, 31, 19, 0));
        service.update(meal, USER_ID);
        assertMatch(service.get(userMeal2.getId(), USER_ID), meal);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(300_000, LocalDateTime.of(
                2020, 1, 31, 19, 0), "test", USER_ID), USER_ID));
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(userMeal1, ADMIN_ID));
        assertMatch(service.get(userMeal1.getId(), USER_ID), userMeal1);
    }

    @Test
    public void create() {
        Meal created = service.create(getNewMeal(), USER_ID);
        int newId = created.getId();
        Meal newMeal = getNewMeal();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}