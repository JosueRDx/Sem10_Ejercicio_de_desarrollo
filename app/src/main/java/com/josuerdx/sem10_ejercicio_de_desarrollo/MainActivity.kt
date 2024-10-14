package com.josuerdx.sem10_ejercicio_de_desarrollo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.josuerdx.sem10_ejercicio_de_desarrollo.view.*

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

    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "category_list",
            Modifier.padding(paddingValues)
        ) {
            composable("category_list") { CategoryListScreen(navController) }
            composable("product_list/{category}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                if (category == "all") {
                    ProductListScreen(navController) // Show all products
                } else {
                    ProductListScreen(navController, category) // Show by specific category
                }
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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Product Manager") },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.inversePrimary
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.inversePrimary
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.List, contentDescription = "Categories") },
            label = { Text("Categories") },
            selected = navController.currentDestination?.route == "category_list",
            onClick = { navController.navigate("category_list") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Products") },
            label = { Text("Products") },
            selected = navController.currentDestination?.route == "product_list/all",
            onClick = {
                navController.navigate("product_list/all")
            }
        )
    }
}
