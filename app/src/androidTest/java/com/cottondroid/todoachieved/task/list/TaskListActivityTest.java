package com.cottondroid.todoachieved.task.list;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.cottondroid.todoachieved.R;
import com.cottondroid.todoachieved.TodoApplication;
import com.cottondroid.todoachieved.di.DaggerTestApplicationComponent;
import com.cottondroid.todoachieved.di.TestApplicationComponent;
import com.cottondroid.todoachieved.matchers.RecyclerViewMatcher;
import com.cottondroid.todoachieved.task.form.TaskFormActivity;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;
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
            todoTaskDao.insertOrReplace(new TodoTask((long) i, TASK_PREFIX + i, new Date().getTime(), new Date().getTime()));
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
