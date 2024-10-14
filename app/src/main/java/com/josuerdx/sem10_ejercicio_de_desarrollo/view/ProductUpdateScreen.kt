package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ProductUpdateScreen(navController: NavController, productId: Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(productId) {
        coroutineScope.launch {
            product = RetrofitClient.instance.getProduct(productId).body()
            product?.let {
                title = it.title
                price = it.price.toString()
                description = it.description
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = price, onValueChange = { price = it }, label = { Text("Price") })
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            coroutineScope.launch {
                val updatedProduct = product?.copy(title = title, price = price.toDouble(), description = description)
                updatedProduct?.let { RetrofitClient.instance.updateProduct(productId, it) }
                navController.popBackStack()
            }
        }) {
            Text("Update Product")
        }
    }
}