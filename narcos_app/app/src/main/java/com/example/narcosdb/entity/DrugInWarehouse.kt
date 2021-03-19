package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "druginwarehouse", primaryKeys = ["drugName", "drugQuality", "warehouseName"],
    foreignKeys = [ForeignKey(entity = Drug::class, parentColumns = arrayOf("name","quality"),
        childColumns = arrayOf("drugName","drugQuality"),
        onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE),
        ForeignKey(entity = DrugWarehouse::class, parentColumns = arrayOf("name"),
            childColumns = arrayOf("warehouseName"),
            onDelete = ForeignKey.RESTRICT, onUpdate = ForeignKey.CASCADE)])
class DrugInWarehouse(var drugName: String, var drugQuality: Int, var amount: Int, var warehouseName: String)