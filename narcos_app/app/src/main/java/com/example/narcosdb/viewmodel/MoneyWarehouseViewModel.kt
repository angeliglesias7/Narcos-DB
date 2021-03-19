package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.entity.MoneyWarehouse
import com.example.narcosdb.repository.MoneyWarehouseRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class MoneyWarehouseViewModel(application: Application): AndroidViewModel(application) {

    private val repository: MoneyWarehouseRepo
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

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

    suspend fun insert(moneyWarehouse: MoneyWarehouse) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result = repository.insert(moneyWarehouse)
            insertValue = result
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(moneyWarehouse: MoneyWarehouse) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result = repository.update(moneyWarehouse)
            updateValue = result
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(moneyWarehouse: MoneyWarehouse) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result = repository.delete(moneyWarehouse)
            deleteValue = result
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

    fun transferMoney(origin: MoneyWarehouse, destination: MoneyWarehouse, amount: Int) = viewModelScope.launch {
        repository.transferMoney(origin, destination, amount)
    }

    fun getAllWarehouses() = repository.allWarehouses

    fun getAllMoney() = repository.allMoney

    fun getTotalWarehouses() = repository.totalWarehouses

}