package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.ContactDao
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.entity.Drug
import kotlinx.coroutines.*

class ContactRepo(
    private val contactDao: ContactDao
) {

    val allContacts: LiveData<List<Contact>> = contactDao.getAll()
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    suspend fun insert(contact: Contact) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result: Deferred<Long> = async {
                contactDao.insert(contact)
            }
            insertValue = result.await()
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(contact: Contact) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                contactDao.update(contact)
            }
            updateValue = result.await()
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(contact: Contact) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                contactDao.delete(contact)
            }
            deleteValue = result.await()
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }



}