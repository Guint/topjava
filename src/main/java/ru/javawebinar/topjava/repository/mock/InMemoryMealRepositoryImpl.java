package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {meal.setUserId(1);
                                            save(meal);});
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save {}", meal);
            return repository.put(meal.getId(), meal);
        }
        Meal inRepMeal = repository.get(meal.getId());
        if (inRepMeal != null && inRepMeal.getUserId() == meal.getUserId()) {
            log.info("update {}", meal);
            return repository.put(meal.getId(), meal);
        } else {
            return null;
        }
    }


    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete {}, {}", mealId, userId);
        Meal meal = repository.get(mealId);
        if (meal != null && meal.getUserId() == userId) {
            repository.remove(mealId);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get {}, {}", mealId, userId);
        Meal meal = repository.get(mealId);
        if (meal != null && meal.getUserId() == userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll {}", userId);
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((meal, nextMeal) -> nextMeal.getDateTime().compareTo(meal.getDateTime()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getBetween(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getBetween {}", userId);
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId && DateTimeUtil.isDateBetween(meal.getDate(), startDate, endDate))
                .sorted((meal, nextMeal) -> nextMeal.getDateTime().compareTo(meal.getDateTime()))
                .collect(Collectors.toList());
    }

}

