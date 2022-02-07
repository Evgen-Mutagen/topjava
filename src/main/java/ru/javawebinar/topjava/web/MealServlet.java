package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.MapStorage;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
   MapStorage storage;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String datetime = request.getParameter("datetime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");
        LocalDateTime localDateTime = LocalDateTime.parse(datetime, DATE_FORMATTER);
        Meal meal;
        if (uuid.equals("0")) {
            meal = new Meal(localDateTime, description, Integer.parseInt(calories));
            storage.save(meal);
        } else {
            meal = new Meal(Integer.parseInt(uuid), localDateTime, description, Integer.parseInt(calories));
            storage.update(meal);
        }
        log.debug("doPost - redirect to meals");
        response.sendRedirect("meals");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String uuid = request.getParameter("mealsUuid");

        String url = "";
        switch (action != null ? action : "") {
            case "insert":
                url = "/WEB-INF/jsp/edit.jsp";
                request.setAttribute("meal", Meal.EMPTY);
                break;
            case "delete":
                storage.delete(Integer.parseInt(uuid));
                response.sendRedirect("meals");
                return;
            case "edit":
                url = "/WEB-INF/jsp/edit.jsp";
                request.setAttribute("meal", storage.get(Integer.parseInt(uuid)));
                break;
            default:
                List<Meal> meals = storage.getAll();
                List<MealTo> mealTo
                        = filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
                request.setAttribute("mealTo", mealTo);
                url = "/WEB-INF/jsp/meals.jsp";
                break;
        }
        getServletContext().getRequestDispatcher((url)).forward(request, response);
    }
}

