package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugWarehouseDatabase
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.repository.DrugWarehouseRepo
import kotlinx.coroutines.launch

class DrugWarehouseViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrugWarehouseRepo

    val allDrugWarehouses: LiveData<List<DrugWarehouse>>

    init {
        val drugWarehouseDao = DrugWarehouseDatabase.getInstance(application).drugWarehouseDao()
        repository = DrugWarehouseRepo(drugWarehouseDao)
        allDrugWarehouses = repository.allDrugWarehouses
    }

    fun insert(drugWarehouse: DrugWarehouse) = viewModelScope.launch {
        repository.insert(drugWarehouse)
    }

    fun update(drugWarehouse: DrugWarehouse) = viewModelScope.launch {
        repository.update(drugWarehouse)
    }

    fun delete(drugWarehouse: DrugWarehouse) = viewModelScope.launch {
        repository.delete(drugWarehouse)
    }

    fun getAll() = repository.allDrugWarehouses

}