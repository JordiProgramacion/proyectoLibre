package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.Foro
import models.Usuario
import models.UsuarioAdmin
import utils.Regex

@Composable
fun MainAppScreen(
    foro: Foro,
    usuario: Usuario,
    onLogout: () -> Unit,
    onNavigateAdmin: () -> Unit
) {
    var mostrarDialogoPregunta by remember { mutableStateOf(false) }
    var mostrarDialogoCorreo by remember { mutableStateOf(false) }
    var filtroPropias by remember { mutableStateOf(false) }
    var refreshTrigger by remember { mutableStateOf(0) }

    // Estado para el nuevo correo
    var nuevoCorreo by remember { mutableStateOf(usuario.mail ?: "") }
    var errorCorreo by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("StackOverflow 2.0", style = MaterialTheme.typography.h6)
                        Text("Usuario: ${usuario.nom} (ID: ${usuario.id})", style = MaterialTheme.typography.caption)
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                actions = {
                    // Botón para gestionar el Email
                    IconButton(onClick = { mostrarDialogoCorreo = true }) {
                        Icon(Icons.Default.Email, contentDescription = "Configurar Correo", tint = Color.White)
                    }

                    if (usuario is UsuarioAdmin) {
                        TextButton(onClick = onNavigateAdmin) {
                            Text("ADMIN PANEL", color = Color.White)
                        }
                    }

                    TextButton(onClick = onLogout) {
                        Text("SALIR", color = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            if (usuario.escritura) {
                FloatingActionButton(
                    onClick = { mostrarDialogoPregunta = true },
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Preguntar")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { filtroPropias = false }) { Text("Todas") }
                Button(onClick = { filtroPropias = true }) { Text("Mis Preguntas") }
            }

            Spacer(Modifier.height(16.dp))

            val preguntas = remember(filtroPropias, refreshTrigger) {
                if (filtroPropias) foro.preguntas.filter { it.idAutor == usuario.id }
                else foro.preguntas
            }

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(preguntas) { pregunta ->
                    PreguntaCard(pregunta, foro, usuario)
                }
            }
        }
    }

    // --- DIÁLOGO PARA AÑADIR/EDITAR CORREO ---
    if (mostrarDialogoCorreo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoCorreo = false },
            title = { Text("Vincular Email") },
            text = {
                Column {
                    Text("Introduce un correo de @insdanielblanxart.cat para recuperar tu cuenta si olvidas la clave.")
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = nuevoCorreo,
                        onValueChange = { nuevoCorreo = it },
                        label = { Text("Email Corporativo") },
                        isError = errorCorreo.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorCorreo.isNotEmpty()) {
                        Text(errorCorreo, color = MaterialTheme.colors.error, style = MaterialTheme.typography.caption)
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (utils.Regex.mailRegex(nuevoCorreo)) {
                        usuario.mail = nuevoCorreo
                        foro.guardarTodo() // Guardamos en el JSON
                        mostrarDialogoCorreo = false
                        errorCorreo = ""
                    } else {
                        errorCorreo = "Debe ser un correo @insdanielblanxart.cat válido"
                    }
                }) { Text("Guardar") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoCorreo = false }) { Text("Cancelar") }
            }
        )
    }

    // Diálogo de nueva pregunta
    if (mostrarDialogoPregunta) {
        DialogoNuevaPregunta(
            onDismiss = { mostrarDialogoPregunta = false },
            onConfirm = { titulo, desc ->
                foro.crearPregunta(usuario.nom, usuario.id, titulo, desc)
                refreshTrigger++
                mostrarDialogoPregunta = false
            }
        )
    }
}