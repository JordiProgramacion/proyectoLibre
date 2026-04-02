package app

import core.Foro
import utils.Utils

fun registroUsuario(foro: Foro) {
    print("Bienvenido al panel de registro de usuarios, por favor ingrese su nombre: ")
    val nombre = readln().replaceFirstChar {it.uppercase()}
    print("Ahora ingrese una contraseña segura: ")
    // Implementar metodo para que no se vea la contraseña en el terminal
    val contrasenaNuevoUsuario = readln()
    foro.registrarUsuario(nombre, contrasenaNuevoUsuario)
}
fun inicioSesion(foro: Foro): Boolean {
    print("Bienvenido al panel de inicio de sesión, por favor ingrese su id: ")
    val idUsuario = readln().toIntOrNull() ?: 10000000
    print("Ahora introduzca su contraseña: ")
    val contraUsuario = readln()
    return foro.iniciarSesion(idUsuario, contraUsuario)
}
fun menuMenuDeInicio() {
    print("""
            ------------------------
            Inicio de sesión
            ------------------------
            1. Iniciar sesión
            2. Registrarse
            3. Sesión de invitado (solo lectura) // ESTO SE IMPLEMENTARÁ EN LA SIGUIENTE VERSIÓN
            0. Salir del programa
            ------------------------
        """.trimIndent())
}
fun menuDeInicio(foro: Foro): Pair<Boolean, Int> {
    do {
        menuMenuDeInicio()
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> {
                // Iniciar sesión
                val estado = inicioSesion(foro)
                return Pair(estado, 1)
            }
            "2" -> {
                // Registro usuarios
                registroUsuario(foro)
                return Pair(false, 1)
            }
            "84938493" -> {
                sesionInvitado() // Pendiente de arreglar, sesión invitado
                return Pair(false, 1)
            }
            "0" -> {
                println("Saliendo del programa...")
            }
            else -> println("Escribe una opción valida (1-4).")
        }
    } while (opcio != "0")
    return Pair(false, 0)
}
fun menuMenuSesionIniciada() {
    println("""
        ------------------------------------
        Bienvenido a StackOverflow 2.0!!!
        ------------------------------------
        1. Ver preguntas
        2. Hacer una pregunta
        3. Ver mis preguntas
        4. Buscar preguntas por ID de usuario
        5. Total preguntas aplicación
        0. Cerrar sesión
        ------------------------------------
    """.trimIndent())
}
fun hacerPregunta() {
    // hacer pregunta
}
fun menuSesionIniciada(foro: Foro): Boolean {
    do {
        menuMenuSesionIniciada()
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> {
                return true
            }
            "2" -> {
                return true
            }
            "3" -> {
                return true
            }
            "4" -> {
                return true
            }
            "5" -> {
                println("Ahora mismo hay un total de: ${Utils.contarPreguntas(foro.preguntas)}")
                return true
            }
            "0" -> {
                println("Cerrando sesión...")
            }
            else -> println("Escribe una opción valida (1-4)")
        }
    } while (opcio != "0")
    return false
}
fun sesionInvitado() {
    println("Has iniciado sesión como invitado, solo podras leer conversaciones, si quieres participar puedes iniciar sesión, si no tienes cuenta, registrarte!!!")
}
fun main() {
    val foro = Foro()
    var usuarioLog = false
    var seguirPrograma = 0
    do {
        if (!usuarioLog) {
            val (estado, programa) = menuDeInicio(foro)
            usuarioLog = estado
            seguirPrograma = programa
        } else {
            usuarioLog = menuSesionIniciada(foro)
        }
    } while (seguirPrograma != 0)
}