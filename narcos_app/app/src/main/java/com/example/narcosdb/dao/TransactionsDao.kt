package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.*
import java.sql.Date
import java.util.*

@Dao
interface TransactionsDao {

    @Query("SELECT * FROM drugbuy")
    fun getBuyHistory(): LiveData<List<DrugBuy>>

    @Query("SELECT * FROM drugsales")
    fun getSalesHistory(): LiveData<List<DrugSales>>

    @Query("SELECT * FROM druginwarehouse WHERE drugName=:drugName AND drugQuality=:drugQuality AND warehouseName=:warehouseName ")
    fun getDrugsInWarehouse(drugName: String, drugQuality: Int, warehouseName: String): DrugInWarehouse

    @Insert
    fun insertBuy(drugBuy: DrugBuy)

    @Insert
    fun insertSales(drugSales: DrugSales)

    @Insert
    fun insertDrugInWarehouse(drugInWarehouse: DrugInWarehouse)

    @Update
    fun updateDrugInWarehouse(drugInWarehouse: DrugInWarehouse)

    @Transaction
    suspend fun buyDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int,
                        mwDao: MoneyWarehouseDao) : Int {
        try{
            //Check if this drug exists in the warehouse
            var drugInWarehouse = getDrugsInWarehouse(drug.name, drug.quality, dw.name)
            //If exists, update the amount. Otherwise, create the drug
            if(drugInWarehouse != null){
                drugInWarehouse.amount += amount
                updateDrugInWarehouse(drugInWarehouse)
            }else{
                var newDrugInWarehouse = DrugInWarehouse(drug.name, drug.quality, amount, dw.name)
                insertDrugInWarehouse(newDrugInWarehouse)
            }
            //Update money in warehouse
            if(mw.amountMoney.minus(amount * drug.price) >= 0){
                mw.amountMoney = mw.amountMoney.minus(amount * drug.price)
                mwDao.update(mw)
                //Save transaction register
                var date = Calendar.getInstance()
                var amountMoney: Double = amount * drug.price
                var drugBuy = DrugBuy(Date(date.timeInMillis).toString(),drug.name, drug.quality, amount, contact.surname, amountMoney)
                insertBuy(drugBuy)
                return 1
            }
            else{
                throw Exception("Money in warehouse < 0")
                return 0
            }
        }catch (e: Exception){
            println(e.message)
            return 0
        }
    }

    @Transaction
    suspend fun saleDrug(drug: Drug, mw: MoneyWarehouse, dw: DrugWarehouse, contact: Contact, amount: Int,
                        mwDao: MoneyWarehouseDao) : Int {
        try{
            //Check if this drug exists in the warehouse
            var drugInWarehouse = getDrugsInWarehouse(drug.name, drug.quality, dw.name)
            //If exists, update the amount. Otherwise, create the drug
            if(drugInWarehouse != null && (drugInWarehouse.amount - amount >= 0)){
                drugInWarehouse.amount -= amount
                updateDrugInWarehouse(drugInWarehouse)
                //Update money in warehouse
                mw.amountMoney = mw.amountMoney.plus(amount * drug.price)
                mwDao.update(mw)
                //Save transaction register
                var date = Calendar.getInstance()
                var amountMoney: Double = amount * drug.price
                var drugSales = DrugSales(Date(date.timeInMillis).toString(),drug.name, drug.quality, amount, contact.surname, amountMoney)
                insertSales(drugSales)
                return 1
            }else{
                throw Exception("No drug in this warehouse")
                return 0
            }
        }catch (e: Exception){
            println(e.message)
            return 0
        }
    }


}