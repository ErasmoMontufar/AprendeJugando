package com.app.tea.aprendejugando.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.tea.aprendejugando.ui.theme.AprendeJugandoTheme
import androidx.compose.ui.text.style.TextAlign
import com.app.tea.aprendejugando.R
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.draw.shadow
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuthUserCollisionException



// Íconos
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.res.painterResource

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AprendeJugandoTheme {
                var name by remember { mutableStateOf("") }
                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var confirmPassword by remember { mutableStateOf("") }

                RegisterScreen(
                    name = name,
                    email = email,
                    password = password,
                    confirmPassword = confirmPassword,
                    onNameChange = { name = it },
                    onEmailChange = { email = it },
                    onPasswordChange = { password = it },
                    onConfirmPasswordChange = { confirmPassword = it },
                    onRegisterClick = {
                        if (password != confirmPassword) {
                            Toast.makeText(this@RegisterActivity, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        } else {
                            val auth = FirebaseAuth.getInstance()
                            val db = FirebaseFirestore.getInstance()

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val userId = auth.currentUser?.uid
                                        val usuario = hashMapOf(
                                            "nombre" to name,
                                            "correo" to email
                                        )

                                        userId?.let {
                                            db.collection("usuarios").document(it).set(usuario)
                                                .addOnSuccessListener {
                                                    Toast.makeText(
                                                        this@RegisterActivity,
                                                        "✨ ¡Te has registrado exitosamente! ¡Bienvenido/a a la aventura! ✨",
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                                }
                                                .addOnFailureListener {
                                                    Toast.makeText(this@RegisterActivity, "Error al guardar datos", Toast.LENGTH_SHORT).show()
                                                }
                                        }
                                    } else {
                                        val exception = task.exception
                                        if (exception is FirebaseAuthUserCollisionException) {
                                            Toast.makeText(this@RegisterActivity, "Este correo ya está registrado. Inicia sesión.", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(this@RegisterActivity, "Error: ${exception?.message}", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                        }
                    },
                    onNavigateToLogin = { finish() }
                )
            }
        }
    }
}


@Composable
fun RegisterScreen(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(listOf(Color(0xFFD4FFDD), Color(0xFF4C8970))))
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_register),
            contentDescription = "Gatito registro",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(120.dp)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFFFF9F0), shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Crea tu cuenta mágica!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF5D4037),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomInputField(
                value = name,
                onValueChange = onNameChange,
                label = "Nombre",
                icon = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomInputField(
                value = email,
                onValueChange = onEmailChange,
                label = "Correo Electrónico",
                icon = Icons.Default.Email
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomInputField(
                value = password,
                onValueChange = onPasswordChange,
                label = "Contraseña",
                icon = Icons.Default.Lock,
                isPassword = true,
                passwordVisible = passwordVisible,
                onTogglePasswordVisibility = { passwordVisible = !passwordVisible }
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomInputField(
                value = confirmPassword,
                onValueChange = onConfirmPasswordChange,
                label = "Confirmar",
                icon = Icons.Default.Lock,
                isPassword = true,
                passwordVisible = confirmPasswordVisible,
                onTogglePasswordVisibility = { confirmPasswordVisible = !confirmPasswordVisible }
            )


            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7DBB8A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp))
            ) {
                Text("Registrarme", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color.Gray)
            }
        }
    }
}

