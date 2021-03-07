package com.example.narcosdb.entity

import androidx.room.Entity

@Entity(tableName = "drug", primaryKeys = ["name", "quality"])
class Drug(var name: String, var price: Double, var quality: Int, var description: String) {
    var amount: Int? = null
    var active: Boolean = false



}