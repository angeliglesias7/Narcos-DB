package com.example.narcosdb.repository


import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.*
import com.example.narcosdb.entity.*
import kotlinx.coroutines.*

class BuysAndSalesRepo(
    private val transactionsDao: TransactionsDao,
    private val mwDao: MoneyWarehouseDao
) {
    private var buyValue: Int = 0
    private var saleValue: Int = 0

    val buyHistory: LiveData<List<DrugBuy>> = transactionsDao.getBuyHistory()
    val salesHistory: LiveData<List<DrugSales>> = transactionsDao.getSalesHistory()

    suspend fun buyDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) : Int {
        val buyCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                transactionsDao.buyDrug(drug, mw, dw, contact, amount, mwDao)
            }
            buyValue = result.await()
        }
        joinAll(buyCoroutine)
        return buyValue
    }

    suspend fun saleDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int) : Int {
        val saleCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                transactionsDao.saleDrug(drug, mw, dw, contact, amount, mwDao)
            }
            saleValue = result.await()
        }
        joinAll(saleCoroutine)
        return saleValue
    }

}
