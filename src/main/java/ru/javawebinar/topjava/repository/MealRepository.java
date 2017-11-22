package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int mealId, int usrId);

    Meal get(int mealId, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId);
}
