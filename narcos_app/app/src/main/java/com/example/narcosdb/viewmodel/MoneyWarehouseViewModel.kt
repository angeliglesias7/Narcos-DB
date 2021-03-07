package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.repository.MoneyWarehouseRepo
import kotlinx.coroutines.launch

class MoneyWarehouseViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MoneyWarehouseRepo

    private val allWarehouses: LiveData<List<MoneyWarehouse>>
    private val allMoney: LiveData<Float>
    private val totalWarehouses: LiveData<Int>

    init {
        val moneyWarehouseDao = DrugDatabase.getInstance(application).moneyWarehouseDao()
        repository = MoneyWarehouseRepo(moneyWarehouseDao)
        allWarehouses = repository.allWarehouses
        allMoney = repository.allMoney
        totalWarehouses = repository.totalWarehouses
    }

    fun insert(moneyWarehouse: MoneyWarehouse) = viewModelScope.launch {
        repository.insert(moneyWarehouse)
    }

    fun update(moneyWarehouse: MoneyWarehouse) = viewModelScope.launch {
        repository.update(moneyWarehouse)
    }

    fun delete(moneyWarehouse: MoneyWarehouse) = viewModelScope.launch {
        repository.delete(moneyWarehouse)
    }

    fun transferMoney(origin: MoneyWarehouse, destination: MoneyWarehouse, amount: Int) = viewModelScope.launch {
        repository.transferMoney(origin, destination, amount)
    }

    fun getAllWarehouses() = repository.allWarehouses

    fun getAllMoney() = repository.allMoney

    fun getTotalWarehouses() = repository.totalWarehouses

}