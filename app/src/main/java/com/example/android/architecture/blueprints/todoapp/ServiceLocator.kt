package com.example.android.architecture.blueprints.todoapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import kotlinx.coroutines.runBlocking

object ServiceLocator {

    private var database: ToDoDatabase? = null
    @Volatile
    var taskRepository: TasksRepository? = null
        @VisibleForTesting set
    private var lock = Any()

    fun provideTaskRepository(context: Context): TasksRepository {
        synchronized(this) {
            return taskRepository?: createTaskRepository(context)
        }
    }

    private fun createTaskRepository(context: Context): TasksRepository {
        val newRepo = DefaultTasksRepository(
                TasksRemoteDataSource,
                createTaskLocalDataSource(context))
        return newRepo.also {
            taskRepository = it
        }
    }

    private fun createTaskLocalDataSource(context: Context): TasksDataSource {
        val database = database?: createDatabase(context)
        return TasksLocalDataSource(database.taskDao())
    }

    private fun createDatabase(context: Context): ToDoDatabase {
        return Room.databaseBuilder(
                context.applicationContext,
                ToDoDatabase::class.java,
                "Tasks.db"
        ).build().also {
            database = it
        }
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            runBlocking {
                TasksRemoteDataSource.deleteAllTasks()
            }

            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            taskRepository = null
        }
    }
}
