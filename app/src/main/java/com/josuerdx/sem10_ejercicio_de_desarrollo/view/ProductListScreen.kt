package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductListScreen(navController: NavController, category: String) {
    val productList = remember { mutableStateListOf<Product>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(category) {
        coroutineScope.launch {
            val products = RetrofitClient.instance.getProductsByCategory(category)
            productList.addAll(products)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("product_create/$category")
                },
                containerColor = Color.Blue
            ) {
                Text("+", color = Color.White)
            }
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
            items(productList) { product ->
                ProductItem(
                    product = product,
                    navController = navController,
                    onDelete = {
                        coroutineScope.launch {
                            RetrofitClient.instance.deleteProduct(product.id)
                            productList.remove(product)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ProductItem(
    product: Product,
    navController: NavController,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clickable { navController.navigate("product_detail/${product.id}") } // Navega a los detalles del producto
        ) {
            Text(text = "Title: ${product.title}")
            Text(text = "Price: $${product.price}")
            Text(text = "Category: ${product.category}")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navController.navigate("product_update/${product.id}") }) { // Navega a la pantalla de actualización
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
    }
}
