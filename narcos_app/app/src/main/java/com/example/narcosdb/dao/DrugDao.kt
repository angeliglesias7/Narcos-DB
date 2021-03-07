package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.Drug
import io.reactivex.Completable

@Dao
interface DrugDao {
    @Insert
    fun insert(drug: Drug?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(drug: Drug?)

    @Delete
    suspend fun delete(drug: Drug?)

    @Query("SELECT * FROM drug")
    fun getAll(): LiveData<List<Drug>>

    //Buy Drug
    //Sale Drug
}