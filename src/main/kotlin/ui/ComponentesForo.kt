package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.Foro
import models.Pregunta
import models.Usuario

@Composable
fun PreguntaCard(pregunta: Pregunta, foro: Foro, usuario: Usuario) {
    var respuestaTexto by remember { mutableStateOf("") }

    Card(elevation = 4.dp, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(pregunta.titulo, style = MaterialTheme.typography.h6)
            Text("Por: ${pregunta.nombreAutor} (ID: ${pregunta.idAutor})", style = MaterialTheme.typography.caption)
            Spacer(Modifier.height(8.dp))
            Text(pregunta.descripcion)

            Divider(Modifier.padding(vertical = 8.dp))

            // Respuestas
            val respuestas = foro.respuestas.filter { it.idPreguntaOriginal == pregunta.id }
            respuestas.forEach { resp ->
                Text("@${resp.nombreAutor}: ${resp.respuesta}", style = MaterialTheme.typography.body2)
            }

            if (usuario.escritura) {
                Row(Modifier.padding(top = 8.dp)) {
                    TextField(
                        value = respuestaTexto,
                        onValueChange = { respuestaTexto = it },
                        placeholder = { Text("Escribe una respuesta...") },
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = {
                        if (respuestaTexto.isNotBlank()) {
                            foro.responderPregunta(pregunta.id, usuario.id, usuario.nom, respuestaTexto)
                            respuestaTexto = ""
                        }
                    }) { Text("Responder") }
                }
            }
        }
    }
}

@Composable
fun DialogoNuevaPregunta(onDismiss: () -> Unit, onConfirm: (String, String) -> Unit) {
    var t by remember { mutableStateOf("") }
    var d by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nueva Pregunta") },
        text = {
            Column {
                OutlinedTextField(value = t, onValueChange = { t = it }, label = { Text("Título") })
                OutlinedTextField(value = d, onValueChange = { d = it }, label = { Text("Descripción") })
            }
        },
        confirmButton = { Button(onClick = { onConfirm(t, d) }) { Text("Publicar") } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } }
    )
}