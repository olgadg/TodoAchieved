package com.example.olgadominguez.todoachieved.tasklist;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.TodoApplication;
import com.example.olgadominguez.todoachieved.di.DaggerTestApplicationComponent;
import com.example.olgadominguez.todoachieved.di.TestApplicationComponent;
import com.example.olgadominguez.todoachieved.matchers.RecyclerViewMatcher;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivity;
import com.example.olgadominguez.todoachieved.task.list.TaskListActivity;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;
import com.example.olgadominguez.todoachieved.task.model.TodoTaskDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TaskListActivityTest {

    private static final int TASK_COUNT = 10;
    private static final String TASK_PREFIX = "Task ";

    @Inject
    TodoTaskDao todoTaskDao;

    @Rule
    public ActivityTestRule<TaskListActivity> activityTestRule = new ActivityTestRule<>(TaskListActivity.class, false, false);

    @Before
    public void setUp() {
        TodoApplication application = (TodoApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestApplicationComponent component = DaggerTestApplicationComponent
                .builder()
                .application(application)
                .build();
        component.inject(application);
        component.inject(this);
        addTasks(TASK_COUNT);
        Intents.init();
        intending(not(hasComponent(TaskListActivity.class.getCanonicalName()))).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    private List<TodoTask> addTasks(int taskCount) {
        List<TodoTask> sampleTasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            todoTaskDao.insert(new TodoTask((long) i, TASK_PREFIX + i, new Date().getTime(), new Date().getTime()));
        }
        return sampleTasks;
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void showItems() {

        activityTestRule.launchActivity(new Intent());
        for (int i = 0; i < TASK_COUNT; i++) {
            onView(new RecyclerViewMatcher((R.id.recycler_view)).atPosition(i))
                    .check(matches(hasDescendant(withText(TASK_PREFIX + i))));
        }
    }

    @Test
    public void addItem() {

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.floating_action_button)).perform(click());

        intended(hasComponent(TaskFormActivity.class.getCanonicalName()));

    }
}
