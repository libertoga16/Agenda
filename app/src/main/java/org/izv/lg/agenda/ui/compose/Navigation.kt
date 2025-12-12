package org.izv.dam.psp.agenda.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.izv.lg.agenda.model.repository.ContactFileRepository
import org.izv.lg.agenda.ui.compose.AddContact
import org.izv.lg.agenda.ui.compose.EditContact
import org.izv.lg.agenda.ui.viewmodel.ContactFileViewModel


@Composable
fun MainScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Navigation(innerPadding)
    }
}

@Composable
fun Navigation(innerPadding: PaddingValues) {
    val navController = rememberNavController()

    val context = LocalContext.current
    val repository = ContactFileRepository(context)
    val viewModel: ContactFileViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ContactFileViewModel(repository) as T
            }
        }
    )

    NavHost(
        navController = navController,
        startDestination = "home-screen"
    ) {
        composable("home-screen") {
            HomeScreen(navController,viewModel ,innerPadding)
        }
        composable("add-contact") {
            AddContact(navController, viewModel ,innerPadding)
        }
        composable("edit-contact") {
            EditContact(navController, viewModel ,innerPadding)
        }
    }
}