package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskViewModel: TasksViewModel

    @Before
    fun setup() {
        val mockContext = ApplicationProvider.getApplicationContext<Application>()
        taskViewModel = TasksViewModel(mockContext)
    }

    @Test
    fun addNewTask_setsNewTaskEvent() {
        taskViewModel.addNewTask()
        val newTaskVal = taskViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(newTaskVal?.getContentIfNotHandled(), (not(nullValue())))
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)
        assertTrue(taskViewModel.tasksAddViewVisible.getOrAwaitValue())
    }
}
