package com.example.narcosdb.repository


import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.BuyDao
import com.example.narcosdb.dao.SalesDao
import com.example.narcosdb.entity.DrugBuy
import com.example.narcosdb.entity.DrugSales

class BuysAndSalesRepo(
    private val buyDao: BuyDao,
    private val salesDao: SalesDao
) {
    val buyHistory: LiveData<List<DrugBuy>> = buyDao.getBuyHistory()
    val salesHistory: LiveData<List<DrugSales>> = salesDao.getSalesHistory()
}
