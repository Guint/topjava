package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;


import java.time.LocalDate;
import java.time.LocalDateTime;


import static ru.javawebinar.topjava.service.MealTestData.*;
import static ru.javawebinar.topjava.service.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal meal = service.get(USER_BREAKFAST.getId(), USER_ID);
        assertMatch(meal, USER_BREAKFAST);
    }

    @Test
    public void delete() throws Exception {
        service.delete(USER_BREAKFAST.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), USER_DINNER, USER_LUNCH);
    }

    @Test
    public void getBetweenDates() throws Exception {
        LocalDate startDate = LocalDate.of(2017, 10, 19);
        LocalDate endDate = LocalDate.of(2017, 10, 19);
        assertMatch(service.getBetweenDates(startDate, endDate, USER_ID), USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime startDateTime = LocalDateTime.of(2017,10, 19, 9, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2017, 10, 19, 12, 0);
        assertMatch(service.getBetweenDateTimes(startDateTime, endDateTime, USER_ID), USER_BREAKFAST);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(USER_BREAKFAST);
        updated.setDescription("Яблоко");
        updated.setCalories(200);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_BREAKFAST.getId(), USER_ID), updated);

    }

    @Test
    public void create() throws Exception {
        Meal meal = new Meal(null, LocalDateTime.now(), "Food", 1000);
        Meal created = service.create(meal, USER_ID);
        meal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), meal, USER_DINNER, USER_LUNCH, USER_BREAKFAST);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundMealId(){
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFoundUserId(){
        service.delete(USER_BREAKFAST.getId(), 1);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundMealId(){
        service.update(new Meal(1, LocalDateTime.now(), "Dinner", 200), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFoundUserId(){
        service.update(USER_BREAKFAST, 1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundMealId(){
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFoundUserId(){
        service.get(USER_BREAKFAST.getId(), 1);
    }

    @Test(expected = DataAccessException.class)
    public void createDuplicateDate(){
        LocalDateTime now = LocalDateTime.now();
        service.create(new Meal(now, "Dinner", 200), USER_ID);
        service.create(new Meal(now, "Lunch", 1000), USER_ID);
    }


}