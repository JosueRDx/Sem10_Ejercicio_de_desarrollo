package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun CategoryListScreen(navController: NavController) {
    val categories = remember { mutableStateListOf<String>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val fetchedCategories = RetrofitClient.instance.getCategories()
            categories.addAll(fetchedCategories)
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(categories) { category ->
            Text(
                text = category,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate("product_list/$category")
                    }
            )
        }
    }
}