package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.Foro
import models.Usuario
import utils.Regex
import automatizacion.automatizacionMail

@Composable
fun AuthScreen(foro: Foro, onLoginSuccess: (Usuario) -> Unit) {
    var isLogin by remember { mutableStateOf(true) }
    var nombre by remember { mutableStateOf("") }
    var idInput by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailRecuperar by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var mensajeExito by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isLogin) "Iniciar Sesión" else "Registro de Usuario",
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primary
        )

        Spacer(Modifier.height(24.dp))

        if (!isLogin) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                modifier = Modifier.width(350.dp)
            )
        } else {
            OutlinedTextField(
                value = idInput,
                onValueChange = { idInput = it },
                label = { Text("ID de Usuario") },
                modifier = Modifier.width(350.dp)
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation(),
            modifier = Modifier.width(350.dp)
        )

        // Mensajes de Feedback
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = MaterialTheme.colors.error, modifier = Modifier.padding(top = 8.dp))
        }

        if (mensajeExito.isNotEmpty()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 8.dp)) {
                Text(mensajeExito, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold)
                Text("¡Anota tu ID antes de cambiar de pantalla!", style = MaterialTheme.typography.caption)
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                if (isLogin) {
                    val id = idInput.toIntOrNull() ?: -1
                    val exito = foro.iniciarSesion(id, password)
                    if (exito) {
                        onLoginSuccess(foro.buscarUsuarioInicio(id, password)!!)
                    } else {
                        mensajeError = "ID o contraseña incorrectos"
                    }
                } else {
                    if (Regex.validarRegex(password) && nombre.isNotBlank()) {
                        // El ID será el tamaño actual de la suma de usuarios y admins + 1
                        val nuevoId = foro.usuarios.size + 1
                        foro.registrarUsuario(nombre, password)
                        mensajeExito = "Registrado correctamente. TU ID ES: $nuevoId"
                        mensajeError = ""
                        nombre = ""
                        password = ""
                    } else {
                        mensajeError = "Contraseña débil (Mín. 8 caracteres, Mayús, Minús y Núm)"
                        mensajeExito = ""
                    }
                }
            },
            modifier = Modifier.width(200.dp)
        ) {
            Text(if (isLogin) "Entrar" else "Registrar")
        }

        TextButton(onClick = {
            isLogin = !isLogin
            mensajeError = ""
            mensajeExito = ""
        }) {
            Text(if (isLogin) "¿No tienes cuenta? Crea una" else "¿Ya tienes cuenta? Inicia sesión")
        }

        // Sección de Recuperación
        Spacer(Modifier.height(32.dp))
        Divider(Modifier.width(350.dp))
        Spacer(Modifier.height(16.dp))

        Text("¿Olvidaste tu contraseña?", style = MaterialTheme.typography.subtitle2)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
            OutlinedTextField(
                value = emailRecuperar,
                onValueChange = { emailRecuperar = it },
                label = { Text("Email de recuperación") },
                modifier = Modifier.width(250.dp)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                val user = foro.existeMail(emailRecuperar)
                if (user != null) {
                    automatizacionMail.verInformacionCuenta(user)
                    mensajeError = "Correo enviado a $emailRecuperar"
                } else {
                    mensajeError = "Este correo no está registrado"
                }
            }) {
                Text("Enviar")
            }
        }
    }
}