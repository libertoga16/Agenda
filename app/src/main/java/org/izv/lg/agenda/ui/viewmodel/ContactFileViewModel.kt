package org.izv.lg.agenda.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izv.dam.psp.agenda.model.data.Contact
import org.izv.lg.agenda.model.repository.ContactFileRepository

class ContactFileViewModel(private val repository: ContactFileRepository) : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = _contacts.asStateFlow()
    var currentContact: Contact? = null

    fun readContacts(){
        viewModelScope.launch {
            _contacts.value = repository.readContacts()
        }
    }

    fun saveContact(name: String, phone: String) {
        viewModelScope.launch {
            val contact = Contact(id = 0, name = name, phone = phone)
            repository.writeContacts(contact)
            readContacts()
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            repository.editContacts(contact)
            readContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            repository.deleteContacts(contact)
            readContacts()
        }
    }
}