package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent() {
        val mockContext = ApplicationProvider.getApplicationContext<Application>()
        val taskViewModel = TasksViewModel(mockContext)

        val observer = Observer<Event<Unit>> {}
        try {
            taskViewModel.newTaskEvent.observeForever(observer)
            taskViewModel.addNewTask()

            val value = taskViewModel.newTaskEvent.value
            assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
        } finally {
            taskViewModel.newTaskEvent.removeObserver(observer)
        }
    }
}
