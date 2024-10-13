package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ProductListScreen(category: String) {
    val productList = remember { mutableStateListOf<Product>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(category) {
        coroutineScope.launch {
            val products = RetrofitClient.instance.getProductsByCategory(category)
            productList.addAll(products)
        }
    }

    LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        items(productList) { product ->
            ProductItem(product = product)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${product.title}")
            Text(text = "Price: $${product.price}")
            Text(text = "Category: ${product.category}")
        }
    }
}