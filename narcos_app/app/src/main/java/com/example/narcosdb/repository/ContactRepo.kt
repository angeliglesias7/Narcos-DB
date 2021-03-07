package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.ContactDao
import com.example.narcosdb.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactRepo(
    private val contactDao: ContactDao
) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAll()

    @WorkerThread
    suspend fun insert(contact: Contact) = withContext(Dispatchers.IO) {
        try {
            contactDao.insert(contact)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun update(contact: Contact) = withContext(Dispatchers.IO) {
        try {
            contactDao.update(contact)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun delete(contact: Contact) = withContext(Dispatchers.IO) {
        try {
            contactDao.delete(contact)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

}