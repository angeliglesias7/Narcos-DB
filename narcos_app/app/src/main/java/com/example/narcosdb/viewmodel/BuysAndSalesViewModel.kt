package com.example.narcosdb.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.narcosdb.database.BuysAndSalesDatabase
import com.example.narcosdb.entity.DrugBuy
import com.example.narcosdb.entity.DrugSales
import com.example.narcosdb.repository.BuysAndSalesRepo

class BuysAndSalesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BuysAndSalesRepo

    private val buyHistory: LiveData<List<DrugBuy>>
    private val salesHistory: LiveData<List<DrugSales>>

    init {
        val buyDao = BuysAndSalesDatabase.getBuyInstance(application).buyDao()
        val salesDao = BuysAndSalesDatabase.getSalesInstance(application).salesDao()
        repository = BuysAndSalesRepo(buyDao, salesDao)
        buyHistory = repository.buyHistory
        salesHistory = repository.salesHistory
    }

    fun getBuyHistory() = repository.buyHistory

    fun getSalesHistory() = repository.salesHistory
}
