package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal, int userId);

    boolean delete(int mealId, int usrId);

    Meal get(int mealId, int userId);

    Collection<Meal> getAll();
}
