package com.josuerdx.sem10_ejercicio_de_desarrollo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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

    val scrollState = rememberScrollState()

    product?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(scrollState), // Permite el desplazamiento
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Imagen del producto
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color.LightGray, Color.White)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = it.image,
                            contentDescription = "Product Image",
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Precio del producto
                    Text(
                        text = "Price: $${it.price}",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(4.dp)
                    )

                    // Divider
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 12.dp)
                    )

                    // Descripción del producto
                    Text(
                        text = it.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = 24.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}
