package com.example.covidtracker

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.covidtracker.database.CovidDao
import com.example.covidtracker.database.LocalDataSource
import com.example.covidtracker.network.RemoteDataSource
import com.example.covidtracker.repository.Repository
import com.example.covidtracker.repository.RepositoryContract

object ServiceLocator {
    private val lock = Any()

    @Volatile
    var repository: RepositoryContract? = null
        @VisibleForTesting set
    fun provideRepository(context: Context): RepositoryContract {
        synchronized(this) {
            return repository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): RepositoryContract {
        val newRepo = Repository(RemoteDataSource, createTaskLocalDataSource(context))
        repository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): CovidDao {
        return LocalDataSource(context)
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            repository = null
        }
    }
}