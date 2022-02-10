package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.MapMealStorage;
import ru.javawebinar.topjava.storage.MealStorage;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealStorage storage = new MapMealStorage();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("datetime"), TimeUtil.DATE_FORMATTER);
        Meal meal = id == null ? new Meal(localDateTime, description, calories) :
                new Meal(Integer.parseInt(id), localDateTime, description, calories);
        storage.save(meal);
        log.info("doPost - redirect to meals");
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        switch (action != null ? action : "") {
            case "insert":
                request.setAttribute("meal", new Meal());
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            case "delete":
                String id = request.getParameter("id");
                log.info("delete");
                storage.delete(Integer.parseInt(id));
                response.sendRedirect("meals");
                return;
            case "edit":
                id = request.getParameter("id");
                log.info("edit");
                request.setAttribute("meal", storage.get(Integer.parseInt(id)));
                request.getRequestDispatcher("edit.jsp").forward(request, response);
                break;
            default:
                log.info("getAll");
                request.setAttribute("meals", MealsUtil.filteredByStreams(storage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("meals.jsp").forward(request, response);
        }
    }
}

