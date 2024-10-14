package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductListScreen(navController: NavController, category: String? = null) {
    val productList = remember { mutableStateListOf<Product>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(category) {
        coroutineScope.launch {
            val products = if (category == null) {
                RetrofitClient.instance.getAllProducts()
            } else {
                RetrofitClient.instance.getProductsByCategory(category)
            }
            productList.clear()
            productList.addAll(products)
        }
    }

    Scaffold(
        floatingActionButton = {
            if (category != null) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("product_create/$category")
                    },
                    containerColor = MaterialTheme.colorScheme.inversePrimary,
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Product")
                }
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable { navController.navigate("product_detail/${product.id}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Price: $${product.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Category: ${product.category.capitalize()}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { navController.navigate("product_update/${product.id}") }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
