package com.app.tea.aprendejugando.ui

import com.app.tea.aprendejugando.ui.theme.AprendeJugandoTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.app.tea.aprendejugando.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.ui.text.style.TextAlign

import com.app.tea.aprendejugando.ui.ScreenComunicacion
import com.app.tea.aprendejugando.ui.ScreenContenidoMatematica
import com.app.tea.aprendejugando.ui.ScreenHabilidades
// Estilos
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
// Imagen y recursos
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
// Sombra (shadow) desde Foundation
import androidx.compose.ui.draw.shadow


class MenuCursosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nombre = intent.getStringExtra("nombreUsuario") ?: "Usuario"

        setContent {
            AprendeJugandoTheme {
                MenuCursosScreen(nombre = nombre) // ✅ Aquí le pasas el nombre correctamente
            }
        }
    }
}

@Composable
fun MenuCursosScreen(nombre: String) {
    val context = LocalContext.current
    val cursos = listOf(
        "Comunicación" to R.drawable.ic_comunicacion,
        "Matemática" to R.drawable.ic_matematica,
        "Ciencia y Ambiente" to R.drawable.ic_ciencia,
        "Habilidades Sociales" to R.drawable.ic_habilidades
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2E2C9)) // fondo general
            .padding(18.dp)
    ) {
        Spacer(modifier = Modifier.height(55.dp))
        // Bienvenida
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(4.dp, RoundedCornerShape(16.dp))
                .background(Color(0xFFBA9380), RoundedCornerShape(16.dp))
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "¡Bienvenido, $nombre!", // Cambiar luego por variable
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "¡Elige una aventura mágica!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF4E342E),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Cuadrícula centrada
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxHeight(0.75f), // altura controlada
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                items(cursos) { (nombre, icono) ->
                    Column(
                        modifier = Modifier
                            .shadow(15.dp, RoundedCornerShape(16.dp))
                            .background(Color(0xFFFFF9F0), shape = RoundedCornerShape(16.dp))
                            .clickable {
                                val intent = when (nombre) {
                                    "Comunicación" -> Intent(context, ScreenComunicacion::class.java)
                                    "Matemática" -> Intent(context, ScreeMatematica::class.java)
                                    "Ciencia y Ambiente" -> Intent(context, SreenCiencia::class.java)
                                    "Habilidades Sociales" -> Intent(context, ScreenHabilidades::class.java)
                                    else -> Intent(context, MainActivity::class.java)
                                }
                                context.startActivity(intent)
                            }
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = icono),
                            contentDescription = nombre,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(90.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = nombre,
                            color = Color(0xFF67644F),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

            }
        )

        //Spacer(modifier = Modifier.weight(1f)) // empuja el botón abajo

        // Botón de cerrar sesión con imagen
        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .clickable {
                    // Acción de cerrar sesión
                    SessionManager.isLoggedIn = false
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.boton), // asegúrate que se llama boton.png y está en res/drawable
                contentDescription = "Cerrar sesión",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Cerrar Sesión",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}




