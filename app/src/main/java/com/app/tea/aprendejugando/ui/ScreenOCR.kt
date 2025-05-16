package com.app.tea.aprendejugando.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import android.app.Activity
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import android.media.SoundPool
import com.app.tea.aprendejugando.R
import androidx.compose.ui.text.font.FontWeight
import android.content.Intent
import android.content.Context
import android.widget.Toast
import android.webkit.WebView
import androidx.compose.ui.zIndex
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import com.unity3d.player.UnityPlayerActivity


class ScreenOCR : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 100)
        } else {
            launchVocalOCRScreen()
        }
    }

    private fun launchVocalOCRScreen() {
        setContent {
            val context = LocalContext.current

            val soundPool = remember {
                android.media.SoundPool.Builder().setMaxStreams(5).build()
            }

            val soundMap = remember { mutableStateMapOf<String, Int>() }

            var textoDetectado by remember { mutableStateOf("") }
            var ultimaVocal by remember { mutableStateOf<String?>(null) }
            var puedeDetectar by remember { mutableStateOf(true) }

            Box(modifier = Modifier.fillMaxSize()) {

                // OCR y cámara
                AndroidView(
                    factory = { ctx ->
                        val previewView = PreviewView(ctx).apply { keepScreenOn = true }

                        // Carga sonidos
                        soundMap["A"] = soundPool.load(ctx, R.raw.vocal_a, 1)
                        soundMap["E"] = soundPool.load(ctx, R.raw.vocal_e, 1)
                        soundMap["I"] = soundPool.load(ctx, R.raw.vocal_i, 1)
                        soundMap["O"] = soundPool.load(ctx, R.raw.vocal_o, 1)
                        soundMap["U"] = soundPool.load(ctx, R.raw.vocal_u, 1)

                        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

                        cameraProviderFuture.addListener({
                            val cameraProvider = cameraProviderFuture.get()

                            val preview = Preview.Builder().build().also {
                                it.setSurfaceProvider(previewView.surfaceProvider)
                            }

                            val analyzer = TextAnalyzer(ctx) { textoOCR ->
                                val vocal = textoOCR?.trim()?.uppercase() ?: ""
                                if (puedeDetectar && vocal.length == 1 && vocal in "AEIOU" && vocal != ultimaVocal) {
                                    ultimaVocal = vocal
                                    textoDetectado = vocal

                                    soundMap[vocal]?.let { id ->
                                        soundPool.play(id, 1f, 1f, 0, 0, 1f)
                                    }

                                    // 👉 Lanzar Unity
                                    val unityIntent = Intent(ctx, UnityPlayerActivity::class.java)
                                    unityIntent.putExtra("letra_detectada", vocal)
                                    ctx.startActivity(unityIntent)

                                    puedeDetectar = false
                                    GlobalScope.launch {
                                        delay(2500)
                                        puedeDetectar = true
                                    }
                                }
                            }

                            val imageAnalysis = ImageAnalysis.Builder()
                                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                                .build().apply {
                                    setAnalyzer(ContextCompat.getMainExecutor(ctx), analyzer)
                                }

                            try {
                                cameraProvider.unbindAll()
                                cameraProvider.bindToLifecycle(
                                    this@ScreenOCR,
                                    CameraSelector.DEFAULT_BACK_CAMERA,
                                    preview,
                                    imageAnalysis
                                )
                            } catch (e: Exception) {
                                Log.e("OCR", "Error al iniciar la cámara", e)
                            }

                        }, ContextCompat.getMainExecutor(ctx))

                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )

                // UI vocal detectada
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .zIndex(3f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Vocales",
                        color = Color.White,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .background(Color(0xFFA77449), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = if (textoDetectado.isNotEmpty()) "La vocal $textoDetectado" else "Detectando...",
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .background(Color(0xFFA77449), RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    // 🔊 Botón para repetir sonido
                    Button(
                        onClick = {
                            textoDetectado.takeIf { it.isNotEmpty() }?.let {
                                soundMap[it]?.let { id -> soundPool.play(id, 1f, 1f, 0, 0, 1f) }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color(0xFFA77449))
                    ) {
                        Text("🔊 Reproducir Audio", color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔙 Botón regresar
                    Button(
                        onClick = { (context as? Activity)?.finish() },
                        colors = ButtonDefaults.buttonColors(Color(0xFFA77449))
                    ) {
                        Text("Regresar", color = Color.White)
                    }
                }
            }
        }
    }
}
