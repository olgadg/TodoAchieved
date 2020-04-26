package com.cottondroid.todoachieved.task.form

import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.di.DaggerTestApplicationComponent
import com.cottondroid.todoachieved.di.TestApplicationComponent
import com.cottondroid.todoachieved.rule.RxSchedulerRule
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

class TaskFormActivityTest {
    @Inject
    lateinit var taskDao: TodoTaskDao

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TaskFormActivity::class.java, false, false)

    @Rule
    @JvmField
    val rxSchedulerRule = RxSchedulerRule()

    @Before
    fun setUp() {
        val application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as TodoApplication
        val component: TestApplicationComponent = DaggerTestApplicationComponent
                .builder()
                .application(application)
                .build()
        component.inject(application)
        component.inject(this)
    }

    @Test
    fun addItem() {
        activityTestRule.launchActivity(Intent())
        Espresso.onView(ViewMatchers.withId(R.id.taskEditText)).perform(ViewActions.typeText(TASK_TEXT))
        Espresso.onView(ViewMatchers.withId(R.id.done)).perform(ViewActions.click())
        val tasks = taskDao.list().blockingFirst()
        ViewMatchers.assertThat("Task added", tasks[0].text, Matchers.`is`(TASK_TEXT))
    }

    companion object {
        private const val TASK_TEXT = "Another task"
    }
}