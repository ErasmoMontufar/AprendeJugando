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

class ScreenAbecedario : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbecedarioSwipeScreen()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AbecedarioSwipeScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val abecedario = listOf("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
        "l", "m", "n", "ñ", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z")
    val pagerState = rememberPagerState()

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desliza para cambiar de letra", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = abecedario.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val letra = abecedario[page]

            val imagenId = when (letra) {
                "a" -> R.drawable.ab_a
                "b" -> R.drawable.ab_b
                "c" -> R.drawable.ab_c
                "d" -> R.drawable.ab_d
                "e" -> R.drawable.ab_e
                "f" -> R.drawable.ab_f
                "g" -> R.drawable.ab_g
                "h" -> R.drawable.ab_h
                "i" -> R.drawable.ab_i
                "j" -> R.drawable.ab_j
                "k" -> R.drawable.ab_k
                "l" -> R.drawable.ab_l
                "m" -> R.drawable.ab_m
                "n" -> R.drawable.ab_n
                "ñ" -> R.drawable.ab_enie
                "o" -> R.drawable.ab_o
                "p" -> R.drawable.ab_p
                "q" -> R.drawable.ab_q
                "r" -> R.drawable.ab_r
                "s" -> R.drawable.ab_s
                "t" -> R.drawable.ab_t
                "u" -> R.drawable.ab_u
                "v" -> R.drawable.ab_v
                "w" -> R.drawable.ab_w
                "x" -> R.drawable.ab_x
                "y" -> R.drawable.ab_y
                "z" -> R.drawable.ab_z
                else -> R.drawable.ab_a
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Letra ${letra.uppercase()}",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                val scale = animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 600)
                ).value

                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = "Imagen de letra $letra",
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
                    val audioId = when (letra) {
                        "a" -> R.raw.letra_a
                        "b" -> R.raw.letra_b
                        "c" -> R.raw.letra_c
                        "d" -> R.raw.letra_d
                        "e" -> R.raw.letra_e
                        "f" -> R.raw.letra_f
                        "g" -> R.raw.letra_g
                        "h" -> R.raw.letra_h
                        "i" -> R.raw.letra_i
                        "j" -> R.raw.letra_j
                        "k" -> R.raw.letra_k
                        "l" -> R.raw.letra_l
                        "m" -> R.raw.letra_m
                        "n" -> R.raw.letra_n
                        "ñ" -> R.raw.letra_enie
                        "o" -> R.raw.letra_o
                        "p" -> R.raw.letra_p
                        "q" -> R.raw.letra_q
                        "r" -> R.raw.letra_r
                        "s" -> R.raw.letra_s
                        "t" -> R.raw.letra_t
                        "u" -> R.raw.letra_u
                        "v" -> R.raw.letra_v
                        "w" -> R.raw.letra_w
                        "x" -> R.raw.letra_x
                        "y" -> R.raw.letra_y
                        "z" -> R.raw.letra_z
                        else -> R.raw.letra_a
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
