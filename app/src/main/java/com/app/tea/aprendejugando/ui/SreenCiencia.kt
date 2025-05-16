package com.app.tea.aprendejugando.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext // ✅ Para obtener el contexto actual
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import com.app.tea.aprendejugando.R
import androidx.compose.ui.Alignment
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.clickable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import android.content.Intent

class SreenCiencia : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CienciaScreen()
        }
    }
}

@Composable
fun CienciaScreen() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFB7F9D8)) // Fondo verde agua
            .padding(24.dp),
        contentAlignment = Alignment.Center // ✅ Muy importante para centrar la columna
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "¡Explora el mundo\nde Ciencia y Ambiente!",
                fontSize = 30.sp,
                color = Color(0xFF67644F),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.ic_ciencia),
                contentDescription = "Imagen curso Ciencia",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Botón Iniciar Actividad
            Box(
                modifier = Modifier
                    .clickable {
                        //val intent = Intent(context, ScreenContenidoCiencia::class.java)
                        //context.startActivity(intent)
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
                    text = "Iniciar Actividad",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

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
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        // 🌥 Emoticono nube parte inferior izquierda
        Image(
            painter = painterResource(id = R.drawable.nube1),
            contentDescription = "Nube decorativa",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(100.dp)
        )
    }
}

