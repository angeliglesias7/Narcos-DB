package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.entity.DrugInWarehouse
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.model.DrugInWarehousePOJO
import com.example.narcosdb.repository.DrugWarehouseRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class DrugWarehouseViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrugWarehouseRepo
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    val allDrugWarehouses: LiveData<List<DrugWarehouse>>

    init {
        val drugWarehouseDao = DrugDatabase.getInstance(application).drugWarehouseDao()
        repository = DrugWarehouseRepo(drugWarehouseDao)
        allDrugWarehouses = repository.allDrugWarehouses
    }

    suspend fun insert(drugWarehouse: DrugWarehouse) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result = repository.insert(drugWarehouse)
            insertValue = result
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(drugWarehouse: DrugWarehouse) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result = repository.update(drugWarehouse)
            updateValue = result
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(drugWarehouse: DrugWarehouse) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result = repository.delete(drugWarehouse)
            deleteValue = result
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

    fun getAll() = repository.allDrugWarehouses

    fun getDrugsInWarehouse(warehouseName: String) : LiveData<List<DrugInWarehousePOJO>>{
        return repository.getDrugsInWarehouse(warehouseName)
    }

}