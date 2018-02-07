package com.example.olgadominguez.todoachieved.activity.taskform;


import android.app.Application;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.database.DatabaseHelper;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivity;
import com.example.olgadominguez.todoachieved.task.model.DaoSession;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TaskFormActivityTest {

    private static final String TASK_TEXT = "Another task";
    private DaoSession daoSession;

    @Rule
    public ActivityTestRule<TaskFormActivity> activityTestRule = new ActivityTestRule<TaskFormActivity>(TaskFormActivity.class) {
        protected void beforeActivityLaunched() {
            daoSession = DatabaseHelper.getDaoSession((Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());
        }
    };

    @Before
    public void setup() {

        daoSession = DatabaseHelper.getDaoSession((Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());
    }

    @After
    public void tearDown() {
        daoSession.getTodoTaskDao().deleteAll();
    }

    @Test
    public void addItem() {

        onView(withId(R.id.task_edittext)).perform(typeText(TASK_TEXT));
        onView(withId(R.id.save_task_button)).perform(click());

        List<TodoTask> tasks = daoSession.getTodoTaskDao().queryBuilder().list();
        assertThat("Task added", tasks.get(0).getText(), is(TASK_TEXT));
    }
}
