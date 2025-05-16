package com.app.tea.aprendejugando.ui

import android.content.Intent
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.app.tea.aprendejugando.ui.theme.AprendeJugandoTheme
import androidx.compose.ui.text.style.TextAlign
import com.app.tea.aprendejugando.R
import androidx.compose.foundation.Image
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.TextStyle

// Íconos
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

// Sombra (shadow) desde Foundation
import androidx.compose.ui.draw.shadow

//base de datos
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AprendeJugandoTheme {

                var email by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }

                val context = this@MainActivity

                LoginScreen(
                    email = email,
                    onEmailChange = { email = it },
                    password = password,
                    onPasswordChange = { password = it },
                    onLoginClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        } else {
                            val auth = FirebaseAuth.getInstance()
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    val userId = auth.currentUser?.uid
                                    val db = FirebaseFirestore.getInstance()

                                    userId?.let {
                                        db.collection("usuarios").document(it).get()
                                            .addOnSuccessListener { document ->
                                                val nombre = document.getString("nombre") ?: "Usuario"
                                                val intent = Intent(context, MenuCursosActivity::class.java)
                                                intent.putExtra("nombreUsuario", nombre)
                                                context.startActivity(intent)
                                                Toast.makeText(
                                                    context,
                                                    "🌟 ¡Bienvenido/a de nuevo, $nombre! 🌟",
                                                    Toast.LENGTH_LONG
                                                ).show()

                                                finish()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(context, "Error al obtener nombre", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                        }
                    },
                    onNavigateToRegister = {
                        val intent = Intent(context, RegisterActivity::class.java)
                        context.startActivity(intent)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onTogglePasswordVisibility: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .background(Color(0xFF917C71), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            )
        },
        trailingIcon = if (isPassword && onTogglePasswordVisibility != null) {
            {
                IconButton(onClick = { onTogglePasswordVisibility() }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        } else null,
        singleLine = true,
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        textStyle = TextStyle(color = Color.Black),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Color.Gray,
            focusedBorderColor = Color(0xFF917C71),
            unfocusedLabelColor = Color.Gray,
            focusedLabelColor = Color(0xFF917C71),
            cursorColor = Color(0xFF917C71),
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(62.dp)
    )
}


@Composable
fun LoginScreen(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFFD4FFDD), Color(0xFF4C8970))
                )
            )
    ) {
        Image(
            painter = painterResource(id = R.drawable.cat_login),
            contentDescription = "Gatito",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(160.dp)
                .padding(8.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(26.dp)
                .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                .background(Color(0xFFFFF9F0), shape = RoundedCornerShape(16.dp))
                .padding(26.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido a tu\nmundo mágico!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF5D4037),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

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

            Button(
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF7DBB8A)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp))
            ) {
                Text("Iniciar Sesión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate", color = Color.Gray)
            }
        }
    }
}







