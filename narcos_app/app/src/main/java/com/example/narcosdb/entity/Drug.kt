package com.example.narcosdb.entity

import androidx.room.Entity

@Entity(tableName = "drug", primaryKeys = ["name", "quality"]) //room ; to create sqlite objects //one table
class Drug(var name: String, var price: Float, var quality: Int, var description: String) {
    var amount: Int? = null

}