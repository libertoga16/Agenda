package org.izv.lg.agenda.model.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.izv.dam.psp.agenda.model.data.Contact
import java.io.File
import kotlin.collections.indexOfFirst
import kotlin.text.split
import kotlin.text.toInt

class ContactFileRepository(private val context: Context) {

    val fileName = "contacts.csv"



    //Leemos el archivo
    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    //Le asiganamos cada atributo
    private fun saveListToFile(contacts: List<Contact>) {
        val file = getFile()
        var data = ""
        contacts.forEach { contact ->
            data += "${contact.id};${contact.name};${contact.phone}\n"
        }
        file.writeText(data)
    }

    //Corrutinas
    suspend fun readContacts(): List<Contact> {
        return withContext(Dispatchers.IO) {
            val file = getFile()
            if (!file.exists()) {
                return@withContext emptyList<Contact>()
            }
            val contacts = mutableListOf<Contact>()

            // Leemo
            file.readLines().forEach { line ->
                val parts = line.split(";")
                try {
                    val id = parts[0].toInt()
                    val name = parts[1]
                    val phone = parts[2]
                    contacts.add(Contact(id, name, phone))
                } catch (e: Exception) {
                }
            }
            return@withContext contacts
        }
    }

    suspend fun writeContacts(contact: Contact): Unit {
        return withContext(Dispatchers.IO) {
            val currentList = readContacts().toMutableList()
            val newId = if (currentList.isEmpty()) 1 else currentList.maxOf { it.id } + 1
            val newContact = contact.copy(id = newId)
            currentList.add(newContact)
            saveListToFile(currentList)
        }
    }

    suspend fun editContacts(contact: Contact): Unit {
        return withContext(Dispatchers.IO) {
            val currentList = readContacts().toMutableList()
            val index = currentList.indexOfFirst { it.id == contact.id }

            if (index != -1) {
                currentList[index] = contact
                saveListToFile(currentList)
            }
        }
    }

    suspend fun deleteContacts(contact: Contact): Unit {
        return withContext(Dispatchers.IO) {
            val currentList = readContacts().toMutableList()
            val newList = currentList.filter { it.id != contact.id }
            saveListToFile(newList)
        }
    }
}