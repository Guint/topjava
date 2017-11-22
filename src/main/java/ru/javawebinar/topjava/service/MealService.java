package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface MealService {

    Meal create(Meal meal);

    void delete(int mealId, int userId) throws NotFoundException;

    Meal get(int mealId, int userId) throws NotFoundException;

    void update(Meal meal);

    List<Meal> getAll(int userId);

    List<Meal> getBetween(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int userId);
}
