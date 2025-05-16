package com.app.tea.aprendejugando.ui

import android.os.Bundle
import android.content.Intent // ✅ Necesario para cambiar de pantalla
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
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

class ScreenComunicacion : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComunicacionScreen()
        }
    }
}

@Composable
fun ComunicacionScreen() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF6D3)), // Fondo principal
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // ✅ Título centrado
            Text(
                text = "¡Explora el mundo\nde la Comunicación!",
                color = Color(0xFF67644F),
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 35.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Imagen principal
            Image(
                painter = painterResource(id = R.drawable.ic_comunicacion),
                contentDescription = "Imagen curso comunicación",
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // ✅ Botón Iniciar Actividad con imagen de fondo
            Box(
                modifier = Modifier
                    .clickable {
                        val intent = Intent(context, ScreenContenidoComunicacion::class.java)
                        context.startActivity(intent)
                    }
                    .height(48.dp)
                    .width(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boton),
                    contentDescription = "Iniciar Actividad",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Text(
                    text = "Iniciar Actividad",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ✅ Botón Regresar con imagen
            Box(
                modifier = Modifier
                    .clickable {
                        (context as? ComponentActivity)?.finish()
                    }
                    .height(48.dp)
                    .width(220.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.boton),
                    contentDescription = "Regresar",
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


        }

        // 👇 Emoticono nube en esquina inferior derecha
        Image(
            painter = painterResource(id = R.drawable.nubep),
            contentDescription = "Nube decorativa",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(150.dp)
        )
    }
}

