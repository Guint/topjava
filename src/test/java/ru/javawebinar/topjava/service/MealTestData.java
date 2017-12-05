package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.service.UserTestData.ADMIN_ID;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static int id = ADMIN_ID;


    public static final Meal USER_BREAKFAST = new Meal(++id, LocalDateTime.of(2017, 10, 19, 10,0), "Завтрак", 200);
    public static final Meal USER_LUNCH = new Meal(++id, LocalDateTime.of(2017, 10, 19, 13,0), "Обед", 500);
    public static final Meal USER_DINNER = new Meal(++id, LocalDateTime.of(2017, 10, 19, 19,0), "Ужин", 1000);


    public static void assertMatch(Meal actual, Meal excepted) {
        assertThat(actual).isEqualToComparingFieldByField(excepted);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
