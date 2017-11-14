package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.InMemoryMealDaoImpl;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static String ADD_OR_UPDATE = "/meal.jsp";
    private static String MEAL_LIST = "/meals.jsp";
    private MealDao dao = new InMemoryMealDaoImpl();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String forward = "";
        if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            log.info("Delete {}", mealId);
            dao.delete(mealId);
            forward = MEAL_LIST;
            List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealList);

        } else if (action.equalsIgnoreCase("edit")) {
            forward = ADD_OR_UPDATE;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            Meal meal = dao.get(mealId);
            req.setAttribute("meal", meal);

        } else if (action.equalsIgnoreCase("meals")) {
            forward = MEAL_LIST;
            log.info("GetAll");
            List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            req.setAttribute("mealList", mealList);

        } else {

            forward = ADD_OR_UPDATE;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String dt = req.getParameter("dateTime");
        LocalDateTime dateTime = LocalDateTime.parse(dt);
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String idString = req.getParameter("mealId");
        Meal meal = new Meal(dateTime, description, calories);
        if (Objects.isNull(idString) || idString.isEmpty()) {
            log.info("Create");
            dao.save(meal);
        } else {
            int id = Integer.parseInt(idString);
            log.info("Edit {}", id);
            meal.setId(id);
            dao.save(meal);
        }

        List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(dao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("mealList", mealList);
        req.getRequestDispatcher(MEAL_LIST).forward(req, resp);
    }
}
