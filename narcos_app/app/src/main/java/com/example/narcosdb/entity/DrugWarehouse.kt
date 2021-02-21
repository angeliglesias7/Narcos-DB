package com.example.narcosdb.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drugwarehouse") //room ; to create sqlite objects //one table
class DrugWarehouse // You may add getters and setters according to your requirement
    (@field:PrimaryKey var name: String, var place: String, var m2: Int)