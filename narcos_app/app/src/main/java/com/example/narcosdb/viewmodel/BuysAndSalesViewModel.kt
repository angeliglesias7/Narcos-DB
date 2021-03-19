package com.example.narcosdb.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.*
import com.example.narcosdb.entity.*
import com.example.narcosdb.repository.BuysAndSalesRepo
import kotlinx.coroutines.launch

class BuysAndSalesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BuysAndSalesRepo

    private val buyHistory: LiveData<List<DrugBuy>>
    private val salesHistory: LiveData<List<DrugSales>>

    init {
        val transactionsDao = DrugDatabase.getInstance(application).transactionsDao()
        val mwDao = DrugDatabase.getInstance(application).moneyWarehouseDao()
        repository = BuysAndSalesRepo(transactionsDao, mwDao)
        buyHistory = repository.buyHistory
        salesHistory = repository.salesHistory
    }

    fun getBuyHistory() = repository.buyHistory

    fun getSalesHistory() = repository.salesHistory

    fun buyDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) = viewModelScope.launch {
        repository.buyDrug(drug, mw, dw, contact, amount)
    }

    fun saleDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) = viewModelScope.launch {
        repository.saleDrug(drug, mw, dw, contact, amount)
    }
}
