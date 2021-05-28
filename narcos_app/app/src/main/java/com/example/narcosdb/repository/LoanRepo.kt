package com.example.narcosdb.repository

import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.LoanDao
import com.example.narcosdb.dao.MoneyWarehouseDao
import com.example.narcosdb.entity.*
import kotlinx.coroutines.*

class LoanRepo(
    private val loanDao: LoanDao,
    private val mwDao: MoneyWarehouseDao
) {
    val allLoansMade: LiveData<List<Loan>> = loanDao.getAllLoansMade()
    val allLoansToPay: LiveData<List<Loan>> = loanDao.getAllLoansToPay()
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var newLoanValue: Int = 0

    suspend fun insertLoan(loan: Loan) : Long{
        val insertCoroutine = GlobalScope.launch {
            val result: Deferred<Long> = async {
                loanDao.insertLoan(loan)
            }
            insertValue = result.await()
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun updateLoan(loan: Loan) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                loanDao.updateLoan(loan)
            }
            updateValue = result.await()
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun newLoanMade(loan: Loan) : Int {
        val newLoanCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                loanDao.newLoanMade(loan, mwDao)
            }
            newLoanValue = result.await()
        }
        joinAll(newLoanCoroutine)
        return newLoanValue
    }
}