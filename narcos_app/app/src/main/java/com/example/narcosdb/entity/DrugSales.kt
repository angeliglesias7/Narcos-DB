package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "drugsales") //room ; to create sqlite objects //one table
class DrugSales {
    @PrimaryKey(autoGenerate = true)
    var id = 0
    var date: String
    var drugName: String
    var quality: Int
    var quantity: Int
    var contact: String

    @Ignore
    constructor(id: Int, date: String, drugName: String, quality: Int, quantity: Int, contact: String) {
        this.id = id
        this.date = date
        this.drugName = drugName
        this.quality = quality
        this.quantity = quantity
        this.contact = contact
    }

    constructor(date: String, drugName: String, quality: Int, quantity: Int, contact: String) {
        this.date = date
        this.drugName = drugName
        this.quality = quality
        this.quantity = quantity
        this.contact = contact
    }

}