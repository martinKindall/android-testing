package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @Test
    fun addNewTask_setsNewTaskEvent() {
        val mockContext = ApplicationProvider.getApplicationContext<Application>()
        val taskViewModel = TasksViewModel(mockContext)
        taskViewModel.addNewTask()
    }
}
