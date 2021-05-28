package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.*
import com.example.narcosdb.repository.LoanRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class LoanViewModel(application: Application): AndroidViewModel(application) {

    private val repository: LoanRepo

    private val allLoansMade: LiveData<List<Loan>>
    private val allLoansToPay: LiveData<List<Loan>>

    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var newLoanValue: Int = 0

    init {
        val loanDao = DrugDatabase.getInstance(application).loanDao()
        val mwDao = DrugDatabase.getInstance(application).moneyWarehouseDao()
        repository = LoanRepo(loanDao, mwDao)
        allLoansMade = repository.allLoansMade
        allLoansToPay = repository.allLoansToPay
    }

    suspend fun insertLoan(loan: Loan) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result = repository.insertLoan(loan)
            insertValue = result
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun updateLoan(loan: Loan) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result = repository.updateLoan(loan)
            updateValue = result
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    fun getAllLoansMade() = repository.allLoansMade

    fun getAllLoansToPay() = repository.allLoansToPay

    suspend fun newLoanMade(loan: Loan) : Int {
        val newLoanCoroutine = GlobalScope.launch {
            val result = repository.newLoanMade(loan)
            newLoanValue = result
        }
        joinAll(newLoanCoroutine)
        return newLoanValue
    }

}