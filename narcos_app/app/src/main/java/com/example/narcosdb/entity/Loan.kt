package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "loan")
class Loan {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var contact: String
    var amount: Double
    var totalAmount: Double
    var warehouse: String
    var loanDate: String
    var returnDate: String? = null
    var dueDate: String
    var interest: Double
    var completed: Boolean
    var type: Int


    constructor(contact: String, warehouse: String, amount: Double, totalAmount: Double,
                interest: Double, completed: Boolean, type: Int, loanDate: String, dueDate: String ) {
        this.contact = contact
        this.amount = amount
        this.totalAmount = totalAmount
        this.warehouse = warehouse
        this.loanDate = loanDate
        this.dueDate = dueDate
        this.interest = interest
        this.completed = completed
        this.type = type
    }
}