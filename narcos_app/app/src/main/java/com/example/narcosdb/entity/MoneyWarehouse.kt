package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "moneywarehouse") //room ; to create sqlite objects //one table
class MoneyWarehouse{
    @field:PrimaryKey var name: String
    var place: String
    var m2: Int
    var amountMoney: Float

    constructor(name: String, place: String, m2: Int, amountMoney: Float) {
        this.name = name
        this.place = place
        this.m2 = m2
        this.amountMoney = amountMoney
    }

    @Ignore
    constructor(){
        name = ""
        place = ""
        m2 = 0
        amountMoney = 0F
    }
}