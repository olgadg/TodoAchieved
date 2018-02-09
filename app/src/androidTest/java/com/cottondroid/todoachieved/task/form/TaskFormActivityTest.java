package com.cottondroid.todoachieved.task.form;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.cottondroid.todoachieved.R;
import com.cottondroid.todoachieved.TodoApplication;
import com.cottondroid.todoachieved.di.DaggerTestApplicationComponent;
import com.cottondroid.todoachieved.di.TestApplicationComponent;
import com.cottondroid.todoachieved.task.model.TodoTask;
import com.cottondroid.todoachieved.task.model.TodoTaskDao;

import javax.inject.Inject;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class TaskFormActivityTest {

    private static final String TASK_TEXT = "Another task";
    @Inject
    TodoTaskDao taskDao;

    @Rule
    public ActivityTestRule<TaskFormActivity> activityTestRule = new ActivityTestRule<>(TaskFormActivity.class, false, false);

    @Before
    public void setUp() {
        TodoApplication application = (TodoApplication) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        TestApplicationComponent component = DaggerTestApplicationComponent
                .builder()
                .application(application)
                .build();
        component.inject(application);
        component.inject(this);
    }


    @Test
    public void addItem() {

        activityTestRule.launchActivity(new Intent());

        onView(withId(R.id.task_edittext)).perform(typeText(TASK_TEXT));
        onView(withId(R.id.done)).perform(click());

        List<TodoTask> tasks = taskDao.list().blockingFirst();
        assertThat("Task added", tasks.get(0).getText(), is(TASK_TEXT));
    }
}
