package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.*
import java.sql.Date
import java.util.*

@Dao
interface LoanDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertLoan(loan: Loan?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLoan(loan: Loan?): Int

    @Query("SELECT sum(totalAmount) FROM loan WHERE type = 1")
    fun getAllMoneyReceived(): LiveData<Float>

    @Query("SELECT sum(totalAmount) FROM loan WHERE type = 2")
    fun getAllMoneyPaid(): LiveData<Float>

    @Query("SELECT sum(totalAmount) FROM loan WHERE type = 1 AND completed = 0")
    fun getPendingMoneyToReceive(): LiveData<Float>

    @Query("SELECT sum(totalAmount) FROM loan WHERE type = 2 AND completed = 0")
    fun getPendingMoneyToPay(): LiveData<Float>

    @Query("SELECT contact FROM loan WHERE type = 1 GROUP BY contact LIMIT 1")
    fun getContactMoreLoansMade(): String

    @Query("SELECT contact FROM loan WHERE type = 2 GROUP BY contact LIMIT 1")
    fun getContactMoreLoansToPay(): String

    @Transaction
    suspend fun newLoanMade(loan: Loan, mwDao: MoneyWarehouseDao) : Int {
        try{
            //Check if the warehouse has enough money
            var moneyWarehouse = mwDao.getMoneyWarehouse(loan.warehouse)
            if(moneyWarehouse.amountMoney >= loan.totalAmount){
                moneyWarehouse.amountMoney -= loan.totalAmount
                mwDao.update(moneyWarehouse)
                insertLoan(loan)
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


}