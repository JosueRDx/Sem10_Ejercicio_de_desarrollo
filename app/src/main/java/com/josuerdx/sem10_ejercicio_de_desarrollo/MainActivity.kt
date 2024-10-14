package com.josuerdx.sem10_ejercicio_de_desarrollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.CategoryListScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductCreateScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductDeleteScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductDetailScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductListScreen
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.ProductUpdateScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "category_list") {
        composable("category_list") { CategoryListScreen(navController) }
        composable("product_list/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ProductListScreen(navController, category)
        }
        composable("product_create/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            ProductCreateScreen(navController, category)
        }
        composable("product_update/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductUpdateScreen(navController, productId)
        }
        composable("product_delete/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDeleteScreen(navController, productId)
        }
        composable("product_detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toInt() ?: 0
            ProductDetailScreen(productId)
        }
    }
}
