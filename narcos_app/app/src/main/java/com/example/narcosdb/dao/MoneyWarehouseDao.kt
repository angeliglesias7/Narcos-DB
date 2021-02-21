package com.example.narcosdb.dao

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.MoneyWarehouse

@Dao
abstract class MoneyWarehouseDao {
    @Insert
    abstract fun insert(moneyWarehouse: MoneyWarehouse?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(moneyWarehouse: MoneyWarehouse?)

    @Delete
    abstract fun delete(moneyWarehouse: MoneyWarehouse?)

    @Transaction
    open fun transferMoney(origin: MoneyWarehouse, destination: MoneyWarehouse, amount: Int) {
        if (origin.amountMoney - amount > 0) {
            origin.amountMoney = origin.amountMoney - amount
            destination.amountMoney = destination.amountMoney + amount
            update(origin)
            update(destination)
        } else {
            println("Impossible to do the transaction")
        }
    }

    @Query("SELECT * FROM moneywarehouse")
    abstract fun getAllWarehouses(): LiveData<List<MoneyWarehouse>>

    @Query("SELECT sum(amountMoney) FROM moneywarehouse")
    abstract fun getAllMoney(): LiveData<Float>

    @NonNull
    @Query("SELECT count(distinct name) FROM moneywarehouse")
    abstract fun getTotalWarehouses(): LiveData<Int>
}