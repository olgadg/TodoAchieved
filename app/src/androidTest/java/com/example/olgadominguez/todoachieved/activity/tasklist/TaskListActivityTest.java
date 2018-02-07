package com.example.olgadominguez.todoachieved.activity.tasklist;


import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.olgadominguez.todoachieved.R;
import com.example.olgadominguez.todoachieved.task.form.TaskFormActivity;
import com.example.olgadominguez.todoachieved.database.DatabaseHelper;
import com.example.olgadominguez.todoachieved.matchers.RecyclerViewMatcher;
import com.example.olgadominguez.todoachieved.task.list.TaskListActivity;
import com.example.olgadominguez.todoachieved.task.model.DaoSession;
import com.example.olgadominguez.todoachieved.task.model.TodoTask;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class TaskListActivityTest {

    private DaoSession daoSession;
    private static final int TASK_COUNT = 10;
    private static final String TASK_PREFIX = "Task ";

    @Rule
    public ActivityTestRule<TaskListActivity> activityTestRule = new ActivityTestRule<TaskListActivity>(TaskListActivity.class) {
        protected void beforeActivityLaunched() {
            daoSession = DatabaseHelper.getDaoSession((Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());
            addTasks(TASK_COUNT);
        }
    };

    @Before
    public void setup() {

        Intents.init();
        intending(not(hasComponent(TaskListActivity.class.getCanonicalName()))).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    private List<TodoTask> addTasks(int taskCount) {
        List<TodoTask> sampleTasks = new ArrayList<>();
        for (int i = 0; i < taskCount; i++) {
            sampleTasks.add(new TodoTask((long) i, TASK_PREFIX + i, new Date(), new Date()));
        }
        daoSession.getTodoTaskDao().deleteAll();
        daoSession.getTodoTaskDao().insertInTx(sampleTasks);
        return sampleTasks;
    }

    @After
    public void tearDown() {
        daoSession.getTodoTaskDao().deleteAll();
        Intents.release();
    }

    @Test
    public void showItems() {

        for (int i = 0; i < TASK_COUNT; i++) {
            onView(new RecyclerViewMatcher((R.id.recycler_view)).atPosition(i))
                    .check(matches(hasDescendant(withText(TASK_PREFIX + i))));
        }
    }

    @Test
    public void addItem() {

        onView(withId(R.id.floating_action_button)).perform(click());

        intended(hasComponent(TaskFormActivity.class.getCanonicalName()));

    }
}
