import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import core.Foro
import models.Usuario

// Pantallas
import ui.MainAppScreen
import ui.AuthScreen
import ui.AdminScreen

// Estados de navegación
enum class Pantalla { AUTH, FORO, ADMIN }

fun main() = application {
    // Inicializamos el objeto Foro (lógica persistente)
    val foro = remember { Foro() }

    // Estados de la sesión y navegación
    var usuarioActual by remember { mutableStateOf<Usuario?>(null) }
    var pantallaActual by remember { mutableStateOf(Pantalla.AUTH) }

    Window(
        onCloseRequest = {
            foro.guardarTodo() // Guarda los JSON antes de cerrar
            exitApplication()
        },
        title = "StackOverflow 2.0 - Desktop",
        state = rememberWindowState(width = 1000.dp, height = 800.dp)
    ) {
        val user = usuarioActual

        if (user == null) {
            // Si no hay usuario, forzamos pantalla de Auth
            AuthScreen(foro) { usuarioLogueado ->
                usuarioActual = usuarioLogueado
                pantallaActual = Pantalla.FORO
            }
        } else {
            // Switch de navegación principal
            when (pantallaActual) {
                Pantalla.FORO -> {
                    MainAppScreen(
                        foro = foro,
                        usuario = user,
                        onLogout = {
                            usuarioActual = null
                            pantallaActual = Pantalla.AUTH
                        },
                        onNavigateAdmin = {
                            pantallaActual = Pantalla.ADMIN
                        }
                    )
                }
                Pantalla.ADMIN -> {
                    AdminScreen(
                        foro = foro,
                        onBack = {
                            pantallaActual = Pantalla.FORO
                        }
                    )
                }
                else -> {}
            }
        }
    }
}