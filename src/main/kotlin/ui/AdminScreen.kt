package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.Foro
import models.UsuarioAdmin

@Composable
fun AdminScreen(foro: Foro, onBack: () -> Unit) {
    // Este trigger forzará a la pantalla a redibujarse cuando hagamos un cambio
    var refreshTrigger by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Administración") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp).fillMaxSize()) {
            Text("Gestión de Usuarios", style = MaterialTheme.typography.h5)
            Spacer(Modifier.height(16.dp))

            // Usamos el trigger para que Compose sepa que debe volver a leer la lista
            val listaUsuarios = remember(refreshTrigger) { foro.usuarios.toList() }

            if (listaUsuarios.isEmpty()) {
                Text("No hay usuarios normales registrados.")
            } else {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(listaUsuarios) { usuario ->
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            elevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(Modifier.weight(1f)) {
                                    Text(usuario.nom, style = MaterialTheme.typography.subtitle1)
                                    Text("ID: ${usuario.id} | Email: ${usuario.mail ?: "Sin correo"}", style = MaterialTheme.typography.caption)
                                    Text(
                                        text = if (usuario.escritura) "Puede escribir" else "Escritura bloqueada",
                                        color = if (usuario.escritura) Color(0xFF2E7D32) else Color.Red,
                                        style = MaterialTheme.typography.caption
                                    )
                                }

                                // Botones de acción
                                Row {
                                    if (usuario.escritura) {
                                        Button(
                                            onClick = {
                                                foro.quitarEscritura(usuario.id)
                                                refreshTrigger++ // Recarga la UI
                                            },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFF59D))
                                        ) { Text("Bloquear") }
                                    } else {
                                        Button(
                                            onClick = {
                                                foro.darEscritura(usuario.id)
                                                refreshTrigger++ // Recarga la UI
                                            },
                                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFA5D6A7))
                                        ) { Text("Desbloquear") }
                                    }

                                    Spacer(Modifier.width(8.dp))

                                    Button(
                                        onClick = {
                                            foro.quitarUsuario(usuario.id)
                                            refreshTrigger++ // Recarga la UI
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFEF9A9A))
                                    ) { Text("Banear") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}