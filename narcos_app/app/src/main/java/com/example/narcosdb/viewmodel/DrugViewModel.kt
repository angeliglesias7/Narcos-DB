package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.repository.DrugRepo
import kotlinx.coroutines.launch

class DrugViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrugRepo

    private val allDrugs: LiveData<List<Drug>>

    init {
        val drugDao = DrugDatabase.getInstance(application).drugDao()
        repository = DrugRepo(drugDao)
        allDrugs = repository.allDrugs
    }

    fun insert(drug: Drug) = viewModelScope.launch {
        repository.insert(drug)
    }

    fun update(drug: Drug) = viewModelScope.launch {
        repository.update(drug)
    }

    fun delete(drug: Drug) = viewModelScope.launch {
        repository.delete(drug)
    }

    fun getAll() = repository.allDrugs
}