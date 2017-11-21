package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
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
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save {}", meal);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            return repository.put(meal.getId(), meal);
        }
        Meal inRepMeal = repository.get(meal.getId());
        if (inRepMeal != null && inRepMeal.getUserId() == meal.getUserId()) {
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
        if (meal != null && meal.getId() != userId) {
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll");
        return repository.values()
                .stream()
                .filter(meal -> meal.getUserId() == userId)
                .sorted((meal, nextMeal) -> nextMeal.getDateTime().compareTo(meal.getDateTime()))
                .collect(Collectors.toList());
    }
}

