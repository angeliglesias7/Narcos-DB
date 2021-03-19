package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.repository.ContactRepo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

class ContactViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ContactRepo

    private val allContacts: LiveData<List<Contact>>

    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    init {
        val contactDao = DrugDatabase.getInstance(application).contactDao()
        repository = ContactRepo(contactDao)
        allContacts = repository.allContacts
    }

    suspend fun insert(contact: Contact) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result = repository.insert(contact)
            insertValue = result
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(contact: Contact) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result = repository.update(contact)
            updateValue = result
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(contact: Contact) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result = repository.delete(contact)
            deleteValue = result
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

    fun getAll() = repository.allContacts
}