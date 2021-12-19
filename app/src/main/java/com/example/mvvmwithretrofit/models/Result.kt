package com.example.mvvmwithretrofit.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val characterId: Int,
    val id: Int,
    val name: String,
)