package com.example.mvvmwithretrofit.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mvvmwithretrofit.CharacterApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterWorkManager(private val context: Context,params: WorkerParameters):
    Worker(context,params) {
    override fun doWork(): Result {
        Log.d("workManager","Work")
       val repository=(context as CharacterApplication).characterRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getCharacterBackground()
        }
        return Result.success()
    }
}