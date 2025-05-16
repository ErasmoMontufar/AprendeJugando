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

class ScreenNumeros : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            numerosSwipeScreen()
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun numerosSwipeScreen() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val numeros = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
    val pagerState = rememberPagerState()

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Desliza para cambiar de número", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalPager(
            count = numeros.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val numero = numeros[page]

            val imagenId = when (numero) {
                "1" -> R.drawable.num_1
                "2" -> R.drawable.num_2
                "3" -> R.drawable.num_3
                "4" -> R.drawable.num_4
                "5" -> R.drawable.num_5
                "6" -> R.drawable.num_6
                "7" -> R.drawable.num_7
                "8" -> R.drawable.num_8
                "9" -> R.drawable.num_9
                "10" -> R.drawable.num_10
                else -> R.drawable.num_1
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Número $numero",
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                val scale = animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 600)
                ).value

                Image(
                    painter = painterResource(id = imagenId),
                    contentDescription = "Imagen del número $numero",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    mediaPlayer?.release()
                    val audioId = context.resources.getIdentifier("num_$numero", "raw", context.packageName)
                    if (audioId != 0) {
                        mediaPlayer = MediaPlayer.create(context, audioId)
                        mediaPlayer?.start()
                    }
                }) {
                    Text("🔊 Reproducir sonido")
                }

                Spacer(modifier = Modifier.height(12.dp))

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
