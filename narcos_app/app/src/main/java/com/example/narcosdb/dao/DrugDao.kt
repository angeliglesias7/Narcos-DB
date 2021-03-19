package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.Drug
import io.reactivex.Completable

@Dao
interface DrugDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(drug: Drug?) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(drug: Drug?) : Int

    @Delete
    fun delete(drug: Drug?) : Int

    @Query("SELECT * FROM drug")
    fun getAll(): LiveData<List<Drug>>

}