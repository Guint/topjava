package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealDaoImpl implements MealDao {
    private Map<Integer, Meal> mealDB = new ConcurrentHashMap<>();
    private AtomicInteger idCounter = new AtomicInteger(0);

    {

        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }

    @Override
    public void save(Meal meal) {
        if (meal.getId() == 0) {
            meal.setId(idCounter.incrementAndGet());
        }
        mealDB.put(meal.getId(), meal);
    }

    @Override
    public Meal get(int id) {
        return mealDB.get(id);
    }

    @Override
    public void delete(int id) {
        mealDB.remove(id);
    }


    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>();
        mealDB.forEach((key, value) -> mealList.add(value));
        return mealList;
    }
}
