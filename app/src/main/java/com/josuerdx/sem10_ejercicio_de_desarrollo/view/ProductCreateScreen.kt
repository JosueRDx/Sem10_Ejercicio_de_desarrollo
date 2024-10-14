package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.data.Product
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.launch

@Composable
fun ProductCreateScreen(navController: NavController, category: String) {
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create New Product",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    val product = Product(0, title, price.toDoubleOrNull() ?: 0.0, description, category, "")
                    RetrofitClient.instance.createProduct(product)
                    navController.popBackStack()
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            Text("Create Product", fontSize = 18.sp, color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}
