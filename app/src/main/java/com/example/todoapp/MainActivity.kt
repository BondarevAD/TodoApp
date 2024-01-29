package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    private val todoViewModel:TodoViewModel by viewModels{
        SavedStateViewModelFactory(application, this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}