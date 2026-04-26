package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(if (isLogin) "Iniciar Sesión" else "Registro de Usuario", style = MaterialTheme.typography.h4)

        Spacer(Modifier.height(16.dp))

        if (!isLogin) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        } else {
            OutlinedTextField(value = idInput, onValueChange = { idInput = it }, label = { Text("ID de Usuario") })
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = androidx.compose.ui.text.input.PasswordVisualTransformation()
        )

        if (mensajeError.isNotEmpty()) {
            Text(mensajeError, color = MaterialTheme.colors.error)
        }

        Button(onClick = {
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
                    foro.registrarUsuario(nombre, password)
                    isLogin = true
                    mensajeError = "Usuario registrado. Inicia sesión con tu ID."
                } else {
                    mensajeError = "Contraseña no segura (8+ caracteres, Mayus, Minus, Num)"
                }
            }
        }) {
            Text(if (isLogin) "Entrar" else "Registrarme")
        }

        TextButton(onClick = { isLogin = !isLogin }) {
            Text(if (isLogin) "¿No tienes cuenta? Regístrate" else "¿Ya tienes cuenta? Entra")
        }

        // Recuperar Contraseña
        Divider(Modifier.padding(vertical = 16.dp))
        Text("¿Has olvidado tu contraseña?")
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(value = emailRecuperar, onValueChange = { emailRecuperar = it }, label = { Text("Tu correo") })
            Button(onClick = {
                val user = foro.existeMail(emailRecuperar)
                if (user != null) {
                    automatizacionMail.verInformacionCuenta(user)
                    mensajeError = "Correo enviado"
                } else {
                    mensajeError = "Correo no encontrado"
                }
            }) { Text("Recuperar") }
        }
    }
}