package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.Contact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contact: Contact?) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(contact: Contact?) : Int

    @Delete
    fun delete(contact: Contact?) : Int

    @Query("SELECT * FROM contact")
    fun getAll(): LiveData<List<Contact>>
}