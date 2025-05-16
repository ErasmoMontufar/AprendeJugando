package com.app.tea.aprendejugando.ui
import android.media.MediaPlayer
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
import com.app.tea.aprendejugando.R
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.graphicsLayer
import com.app.tea.aprendejugando.ui.ScreenOCR
import android.content.Intent

class ScreenMonosilabas : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            monosilabasSwipeScreen()
        }
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun monosilabasSwipeScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val monosilabas = listOf("Sol", "Rey", "Pan", "Pez", "Gas", "Cruz", "Sal", "Té", "Luz",
                         "Mar")
    val pagerState = rememberPagerState()

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desliza para cambiar de palabra", style = MaterialTheme.typography.headlineSmall)
        Text("Todas las monosílabas son tónicas, ya que solo tienen una sílaba.", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = monosilabas.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val palabra = monosilabas[page]

            val imagenId = when (palabra) {
                "Sol" -> R.drawable.mono_1
                "Rey" -> R.drawable.mono_2
                "Pan" -> R.drawable.mono_3
                "Pez" -> R.drawable.mono_4
                "Gas" -> R.drawable.mono_5
                "Cruz" -> R.drawable.mono_6
                "Sal" -> R.drawable.mono_7
                "Té" -> R.drawable.mono_8
                "Luz" -> R.drawable.mono_9
                "Mar" -> R.drawable.mono_10
                else -> R.drawable.mono_1
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Palabra ${palabra.uppercase()}",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                val scale = animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 600)
                ).value

                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = "Imagen de la palabra $palabra",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 🔊 Botón para reproducir sonido
                Button(onClick = {
                    mediaPlayer?.release()
                    val audioId = when (palabra) {
                        "Sol" -> R.raw.mono_1
                        "Rey" -> R.raw.mono_2
                        "Pan" -> R.raw.mono_3
                        "Pez" -> R.raw.mono_4
                        "Gas" -> R.raw.mono_5
                        "Cruz" -> R.raw.mono_6
                        "Sal" -> R.raw.mono_7
                        "Té" -> R.raw.mono_8
                        "Luz" -> R.raw.mono_9
                        "Mar" -> R.raw.mono_10
                        else -> R.raw.mono_1
                    }
                    mediaPlayer = MediaPlayer.create(context, audioId)
                    mediaPlayer?.start()
                }) {
                    Text("🔊 Reproducir sonido")
                }

                Spacer(modifier = Modifier.height(12.dp))

                // 🧠 NUEVO: Botón para ir a la camara OCR
                Button(onClick = {
                    val intent = Intent(context, ScreenOCR::class.java)
                    context.startActivity(intent)
                }) {
                    Text("Ir a Cámara OCR")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ✅ Botón fuera del carrusel
        /*Button(onClick = {
            val intent = Intent(context, ScreenOCR::class.java)
            context.startActivity(intent)
        }) {
            Text("Ir a Cámara OCR")
        }*/

        Button(onClick = {
            (context as? ComponentActivity)?.finish()
        }) {
            Text("Regresar")
        }
    }
}
