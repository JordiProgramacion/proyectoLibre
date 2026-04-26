package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.Foro
import models.Usuario
import models.UsuarioAdmin

@Composable
fun MainAppScreen(
    foro: Foro,
    usuario: Usuario,
    onLogout: () -> Unit,
    onNavigateAdmin: () -> Unit // Parámetro nuevo para navegar
) {
    var mostrarDialogoPregunta by remember { mutableStateOf(false) }
    var filtroPropias by remember { mutableStateOf(false) }

    // Trigger para refrescar la lista cuando se añade algo
    var refreshTrigger by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StackOverflow 2.0") },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                actions = {
                    // Si es admin, mostramos el botón del panel
                    if (usuario is UsuarioAdmin) {
                        TextButton(onClick = onNavigateAdmin) {
                            Text("ADMIN PANEL", color = Color.White)
                        }
                    }

                    TextButton(onClick = onLogout) {
                        Text("CERRAR SESIÓN", color = Color.White)
                    }
                }
            )
        },
        floatingActionButton = {
            // Solo los que tienen permiso de escritura pueden ver el botón "+"
            if (usuario.escritura) {
                FloatingActionButton(
                    onClick = { mostrarDialogoPregunta = true },
                    backgroundColor = MaterialTheme.colors.secondary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Nueva Pregunta")
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {

            // Filtros
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { filtroPropias = false },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (!filtroPropias) MaterialTheme.colors.primary else Color.LightGray
                    )
                ) { Text("Todas las preguntas") }

                Button(
                    onClick = { filtroPropias = true },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (filtroPropias) MaterialTheme.colors.primary else Color.LightGray
                    )
                ) { Text("Mis preguntas") }
            }

            Spacer(Modifier.height(16.dp))

            // Lista de preguntas
            val preguntasAMostrar = remember(filtroPropias, refreshTrigger) {
                if (filtroPropias) foro.preguntas.filter { it.idAutor == usuario.id }
                else foro.preguntas
            }

            if (preguntasAMostrar.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No hay preguntas para mostrar.")
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(preguntasAMostrar) { pregunta ->
                        PreguntaCard(pregunta, foro, usuario)
                    }
                }
            }
        }
    }

    // Lógica del diálogo para crear pregunta
    if (mostrarDialogoPregunta) {
        DialogoNuevaPregunta(
            onDismiss = { mostrarDialogoPregunta = false },
            onConfirm = { titulo, descripcion ->
                foro.crearPregunta(usuario.nom, usuario.id, titulo, descripcion)
                refreshTrigger++ // Forzamos actualización de la lista
                mostrarDialogoPregunta = false
            }
        )
    }
}