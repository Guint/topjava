package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealDaoImpl  implements MealDao {
    private static Map<Integer, Meal> mealDB = new ConcurrentHashMap<>();
    private static AtomicInteger idCounter = new AtomicInteger(0);

    @Override
    public void add(Meal meal) {
        meal.setId(idCounter.incrementAndGet());
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
    public void update(Meal meal) {
        mealDB.put(meal.getId(), meal);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> mealList = new ArrayList<>();
        mealDB.forEach((key, value) -> mealList.add(key - 1, value));
        return mealList;
    }
}
