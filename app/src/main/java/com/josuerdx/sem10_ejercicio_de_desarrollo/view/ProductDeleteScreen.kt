package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ProductDeleteScreen(navController: NavController, productId: Int) {
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            RetrofitClient.instance.deleteProduct(productId)
        }
        navController.popBackStack()
    }
}
