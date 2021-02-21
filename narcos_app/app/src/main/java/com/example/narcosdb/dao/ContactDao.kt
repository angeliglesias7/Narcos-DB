package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.Contact

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(contact: Contact?)

    @Delete
    fun delete(contact: Contact?)

    @get:Query("SELECT * FROM contact")
    val all: LiveData<List<Contact?>?>?
}