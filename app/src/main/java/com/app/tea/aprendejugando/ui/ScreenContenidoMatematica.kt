package com.app.tea.aprendejugando.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent


class ScreenContenidoMatematica : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContenidoMatematicaScreen()
        }
    }
}

@Composable
fun ContenidoMatematicaScreen() {
    val context = LocalContext.current

    val contenidos = listOf(
        "Numeros de 1 - 100", "Figuras Geometricas", "Unidad/Decena/Centena"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text("Selecciona un contenido", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Botones por cada contenido
        contenidos.forEach { contenido ->
            Button(
                onClick = {
                    val intent = when (contenido) {
                        "Numeros de 1 - 100" -> Intent(context, ScreenNumeros::class.java)
                        // Más contenido luego: "Abecedario" -> Intent(context, ScreenAbecedario::class.java)
                        else -> null
                    }

                    intent?.let {
                        context.startActivity(it)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(contenido)
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // 🧱 Espacio antes del botón regresar

        // ✅ Solo un botón "Regresar", fuera del forEach
        Button(
            onClick = {
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}
