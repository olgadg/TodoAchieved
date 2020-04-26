package com.cottondroid.todoachieved.task.list

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.cottondroid.todoachieved.R
import com.cottondroid.todoachieved.TodoApplication
import com.cottondroid.todoachieved.di.DaggerTestApplicationComponent
import com.cottondroid.todoachieved.di.TestApplicationComponent
import com.cottondroid.todoachieved.matchers.RecyclerViewMatcher
import com.cottondroid.todoachieved.rule.RxSchedulerRule
import com.cottondroid.todoachieved.task.form.TaskFormActivity
import com.cottondroid.todoachieved.task.model.TodoTask
import com.cottondroid.todoachieved.task.model.TodoTaskDao
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.ArrayList
import java.util.Date
import javax.inject.Inject

class TaskListActivityTest {
    @Inject
    lateinit var todoTaskDao: TodoTaskDao

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(TaskListActivity::class.java, false, false)

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
        addTasks()
        Intents.init()
        Intents.intending(Matchers.not(IntentMatchers.hasComponent(TaskListActivity::class.java.canonicalName)))
                .respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    private fun addTasks(): List<TodoTask> {
        val sampleTasks: List<TodoTask> = ArrayList()
        for (i in 0 until TASK_COUNT) {
            todoTaskDao.insertOrReplace(
                    TodoTask(
                            id = i.toLong(),
                            serverId = i.toString(),
                            text = TASK_PREFIX + i,
                            createdDate = Date().time
                    )
            ).subscribe()
        }
        return sampleTasks
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun showItems() {
        activityTestRule.launchActivity(Intent())
        for (i in 0 until TASK_COUNT) {
            Espresso.onView(RecyclerViewMatcher(R.id.recyclerView).atPosition(i))
                    .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(TASK_PREFIX + i))))
        }
    }

    @Test
    fun addItem() {
        activityTestRule.launchActivity(Intent())
        Espresso.onView(withId(R.id.fab)).perform(ViewActions.click())
        Intents.intended(IntentMatchers.hasComponent(TaskFormActivity::class.java.canonicalName))
    }

    companion object {
        private const val TASK_COUNT = 10
        private const val TASK_PREFIX = "Task "
    }
}