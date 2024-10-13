package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ProductDetailScreen(productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        coroutineScope.launch {
            product = RetrofitClient.instance.getProduct(productId).body()
        }
    }

    product?.let {
        Column {
            Text("Title: ${it.title}")
            Text("Price: $${it.price}")
            Text("Category: ${it.category}")
            // Implement edit and delete functionalities here
        }
    }
}