package com.example.narcosdb.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.*
import com.example.narcosdb.entity.*
import com.example.narcosdb.repository.BuysAndSalesRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class BuysAndSalesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BuysAndSalesRepo

    private val buyHistory: LiveData<List<DrugBuy>>
    private val salesHistory: LiveData<List<DrugSales>>

    private var buyValue: Int = 0
    private var saleValue: Int = 0

    init {
        val transactionsDao = DrugDatabase.getInstance(application).transactionsDao()
        val mwDao = DrugDatabase.getInstance(application).moneyWarehouseDao()
        repository = BuysAndSalesRepo(transactionsDao, mwDao)
        buyHistory = repository.buyHistory
        salesHistory = repository.salesHistory
    }

    fun getBuyHistory() = repository.buyHistory

    fun getSalesHistory() = repository.salesHistory

    suspend fun buyDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) : Int {
        val buyCoroutine = GlobalScope.launch {
            val result = repository.buyDrug(drug, mw, dw, contact, amount)
            buyValue = result
        }
        joinAll(buyCoroutine)
        return buyValue
    }

    suspend fun saleDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) : Int {
        val saleCoroutine = GlobalScope.launch {
            val result = repository.saleDrug(drug, mw, dw, contact, amount)
            saleValue = result
        }
        joinAll(saleCoroutine)
        return saleValue
    }
}
