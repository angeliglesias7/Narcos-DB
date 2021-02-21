package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact") //room ; to create sqlite objects //one table
class Contact(@field:PrimaryKey var surname: String, var passport: String, var phone: String, var address: String) {
    var overdueLoans: Int? = null
    var purchaseAmount = 0
    var saleAmount = 0

    override fun toString(): String {
        return surname
    }

}