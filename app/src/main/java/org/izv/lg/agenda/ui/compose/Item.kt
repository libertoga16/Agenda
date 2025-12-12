package org.izv.dam.psp.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.dam.psp.agenda.model.data.Contact
import org.izv.lg.agenda.ui.viewmodel.ContactFileViewModel

@Composable
fun Item(contact: Contact, navController: NavController, viewModel: ContactFileViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            viewModel.currentContact = contact
            navController.navigate("edit-contact")}
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${contact.name}  (${contact.phone})")
        }
    }
}