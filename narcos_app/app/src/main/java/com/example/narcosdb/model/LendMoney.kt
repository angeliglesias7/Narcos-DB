package com.example.narcosdb.model

import java.sql.Date

class LendMoney(var username: String, var contact: String, var amount: Int, var warehouse: String, var loanDate: Date, var returnDate: Date, var dueDate: Date) {
    var amountToReturn: Int? = null
}