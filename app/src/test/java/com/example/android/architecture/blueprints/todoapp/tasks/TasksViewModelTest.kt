package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.Matchers.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class TasksViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var taskViewModel: TasksViewModel
    private lateinit var tasksRepository: FakeRepository

    @Before
    fun setup() {
        tasksRepository = FakeRepository()
        tasksRepository.addTasks(
                Task("Just a simple task", "Nothing to do"),
                Task("Simple task 2", "Nothing to do", true),
                Task("Simple task 3", "Nothing to do", true)
        )
        taskViewModel = TasksViewModel(tasksRepository)
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
