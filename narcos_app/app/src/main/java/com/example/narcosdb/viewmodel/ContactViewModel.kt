package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.Contact
import com.example.narcosdb.repository.ContactRepo
import kotlinx.coroutines.launch

class ContactViewModel(application: Application): AndroidViewModel(application) {

    private val repository: ContactRepo

    private val allContacts: LiveData<List<Contact>>

    init {
        val contactDao = DrugDatabase.getInstance(application).contactDao()
        repository = ContactRepo(contactDao)
        allContacts = repository.allContacts
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)
    }

    fun update(contact: Contact) = viewModelScope.launch {
        repository.update(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {
        repository.delete(contact)
    }

    fun getAll() = repository.allContacts
}