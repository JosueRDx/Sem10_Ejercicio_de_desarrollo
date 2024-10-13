package com.josuerdx.sem10_ejercicio_de_desarrollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductDetailScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "product_list") {
                composable("product_list") { ProductListScreen() }
                composable("product_detail/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
                    ProductDetailScreen(productId)
                }
            }
        }
    }
}