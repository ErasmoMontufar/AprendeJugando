package com.app.tea.aprendejugando.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.clickable
import com.app.tea.aprendejugando.R
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import android.content.Intent
import androidx.compose.ui.text.style.TextAlign



class ScreenVocales : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocalesSimpleScreen()
        }
    }
}

@Composable
fun VocalesSimpleScreen() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF6D3)) // 🎨 Fondo beige
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🟡 Título centrado
        Text(
            text = "¡Explora el mundo\nde las Vocales!",
            color = Color(0xFF67644F),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 36.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 🖼 Imagen principal
        Image(
            painter = painterResource(id = R.drawable.vocales1), // tu imagen central
            contentDescription = "Ilustración vocales",
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(240.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 📷 Botón con imagen + texto explicativo para niños
        Box(
            modifier = Modifier
                .height(70.dp)
                .clickable {
                    val intent = Intent(context, ScreenOCR::class.java)
                    context.startActivity(intent)
                },
            contentAlignment = Alignment.Center // 🧡 Esto centra el texto
        ) {
            Image(
                painter = painterResource(id = R.drawable.boton),
                contentDescription = "Botón Realidad Aumentada",
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "\uD83E\uDDE0 Aprende jugando",
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )
        }


        Spacer(modifier = Modifier.height(32.dp))


        // 🔙 Botón regresar con imagen personalizada + texto
        Box(
            modifier = Modifier
                .height(70.dp)
                .clickable {
                    (context as? ComponentActivity)?.finish()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.boton), // puedes usar otra imagen si deseas
                contentDescription = "Botón Regresar",
                modifier = Modifier.fillMaxSize()
            )

            Text(
                text = "Regresar", // 🔙
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }


    }
}



