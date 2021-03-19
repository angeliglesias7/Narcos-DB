package com.example.narcosdb.repository


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.*
import com.example.narcosdb.entity.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BuysAndSalesRepo(
    private val transactionsDao: TransactionsDao,
    private val mwDao: MoneyWarehouseDao
) {
    val buyHistory: LiveData<List<DrugBuy>> = transactionsDao.getBuyHistory()
    val salesHistory: LiveData<List<DrugSales>> = transactionsDao.getSalesHistory()

    @WorkerThread
    suspend fun buyDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) = withContext(Dispatchers.IO) {
        try {
            transactionsDao.buyDrug(drug, mw, dw, contact, amount, mwDao)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun saleDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) = withContext(Dispatchers.IO) {
        try {
            transactionsDao.saleDrug(drug, mw, dw, contact, amount, mwDao)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
