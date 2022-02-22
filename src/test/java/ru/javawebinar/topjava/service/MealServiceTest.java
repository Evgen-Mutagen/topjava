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

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTesData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

@ContextConfiguration({
        "classpath:spring/spring-test.xml",
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
        Meal meal = service.get(START_SEQ + 1, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test
    public void delete() {
        service.delete(START_SEQ + 3, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(START_SEQ + 3, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(START_SEQ + 1, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(MEAL2.getDate(), MEAL2.getDate(), START_SEQ + 2));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, MEAL3, MEAL4);
    }

    @Test
    public void update() {
        Meal meal = service.get(START_SEQ + 2, USER_ID);
        meal.setDescription("пирожики");
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL2.getId(), USER_ID), meal);
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(new Meal(300_000, LocalDateTime.now(), "test", USER_ID), USER_ID));
    }

    @Test
    public void create() {
        Meal expected = getNewMeal();
        Meal created = service.create(expected, ADMIN_ID);
        Integer newId = created.getId();
        expected.setId(newId);
        assertMatch(service.get(newId, ADMIN_ID), expected);
    }
}