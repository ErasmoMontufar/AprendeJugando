package com.app.tea.aprendejugando.ui
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import com.app.tea.aprendejugando.ui.ScreenMonosilabas
import com.google.accompanist.pager.ExperimentalPagerApi

class ScreenSilabas : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SilabasScreen()
        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SilabasScreen() {
    val context = LocalContext.current

    val silabas = listOf("Monosílabas", "Bisílabas", "Trisílabas", "Polisílabas")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // ✅ Botones de silabas
        silabas.forEach { silaba -> // Cambia el nombre aquí
            Button(
                onClick = {
                    val intent = when (silaba) { // Aquí usamos 'silaba' no 'silabas'
                        "Monosílabas" -> Intent(context, ScreenMonosilabas::class.java)
                        else -> Intent(context, MainActivity::class.java)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(silaba) // También aquí
            }
        }




        Spacer(modifier = Modifier.height(32.dp)) // espacio antes del botón de cerrar sesión

        // 🔒 Botón de cerrar sesión
        Button(
            onClick = {
                SessionManager.isLoggedIn = false // ⛔ Cerramos sesión

                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }

    }
}