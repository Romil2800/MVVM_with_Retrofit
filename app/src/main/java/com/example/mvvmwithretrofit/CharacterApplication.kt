package com.example.mvvmwithretrofit

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.mvvmwithretrofit.api.CharacterService
import com.example.mvvmwithretrofit.api.RetrofitHelper
import com.example.mvvmwithretrofit.database.CharacterDatabase
import com.example.mvvmwithretrofit.repository.CharacterRepository
import com.example.mvvmwithretrofit.worker.CharacterWorkManager
import java.util.concurrent.TimeUnit

class CharacterApplication : Application() {

    lateinit var characterRepository: CharacterRepository

    override fun onCreate() {
        super.onCreate()
        initialize()
        setUpWorker()
    }

    private fun setUpWorker() {
        val constraint = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest =
            PeriodicWorkRequest.Builder(CharacterWorkManager::class.java, 2, TimeUnit.MINUTES)
                .setConstraints(constraint)
                .build()

        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize() {
        val characterService = RetrofitHelper.getInstance().create(CharacterService::class.java)
        val database = CharacterDatabase.getDatabase(applicationContext)
        characterRepository = CharacterRepository(characterService, database, applicationContext)
    }
}