package com.app.tea.aprendejugando.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.app.tea.aprendejugando.R
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import android.content.Context


// Sombra (shadow) desde Foundation
import androidx.compose.ui.draw.shadow

class ScreenContenidoComunicacion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContenidoComunicacionScreen()
        }
    }
}

@Composable
fun ContenidoComunicacionScreen() {
    val context = LocalContext.current

    val contenidos = listOf(
        "Vocales" to R.drawable.vocales,
        "Abecedario" to R.drawable.abe,
        "Meses del Año" to R.drawable.meses
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF6D3)) // Fondo principal
            .padding(40.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxHeight()
        ) {
            Text(
                text = "¡Explora el contenido mágico!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF67644F),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Primera fila: 2 contenidos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                contenidoCard("Vocales", R.drawable.vocales, context, ScreenVocales::class.java)
                contenidoCard("Abecedario", R.drawable.abe, context, ScreenAbecedario::class.java)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Segunda fila: 1 contenido centrado
            contenidoCard("Meses del Año", R.drawable.meses, context, null)

            Spacer(modifier = Modifier.height(24.dp))

            // Botón Regresar
            Box(
                modifier = Modifier
                    .clickable {
                        (context as? ComponentActivity)?.finish()
                    }
                    .height(56.dp)
                    .fillMaxWidth(0.65f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boton),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Regresar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Nube en esquina inferior izquierda
            Spacer(modifier = Modifier.height(24.dp))
            Image(
                painter = painterResource(id = R.drawable.nubep),
                contentDescription = "Decoración nube",
                modifier = Modifier
                    .align(Alignment.Start)
                    .graphicsLayer(scaleX = -1f) // Voltea horizontalmente
                    .size(125.dp)
            )
        }

    }
}

@Composable
fun contenidoCard(nombre: String, icono: Int, context: Context, destino: Class<*>?) {
    Box(
        modifier = Modifier
            .shadow(10.dp, RoundedCornerShape(16.dp))
            .background(Color(0xFFFFF9F0), RoundedCornerShape(16.dp))
            .clickable {
                destino?.let {
                    val intent = Intent(context, it)
                    context.startActivity(intent)
                }
            }
            .padding(20.dp)
            .width(87.dp) // Ajusta si lo ves necesario
            .height(110.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = icono),
                contentDescription = nombre,
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = nombre,
                color = Color(0xFF67644F),
                fontWeight = FontWeight.Medium
            )
        }
    }
}

