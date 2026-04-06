package app

import core.Foro
import utils.Utils
import models.Usuario
// Todavía no se usa import models.UsuarioInvitado

fun registroUsuario(foro: Foro) {
    print("Bienvenido al panel de registro de usuarios, por favor ingrese su nombre: ")
    val nombre = readln().trim().replaceFirstChar {it.uppercase()}
    print("Ahora ingrese una contraseña segura: ")
    // Implementar metodo para que no se vea la contraseña en el terminal
    val contrasenaNuevoUsuario = readln()
    foro.registrarUsuario(nombre, contrasenaNuevoUsuario)
}
fun inicioSesion(foro: Foro): Usuario? {
    print("Bienvenido al panel de inicio de sesión, por favor ingrese su id: ")
    val idUsuario = readln().toIntOrNull() ?: -1
    print("Ahora introduzca su contraseña: ")
    val contraUsuario = readln()

    val inicioSesion = foro.iniciarSesion(idUsuario, contraUsuario)
    val usuario = foro.usuarios.find { it.id == idUsuario && it.contrasena == contraUsuario }
    return if (inicioSesion) {
        usuario
    } else {
        null
    }
    // return foro.usuarios.find { it.id == id && it.contrasena == pass} (esto es una mejor opción, hay que mirarlo)
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
fun menuDeInicio(foro: Foro): Pair<Usuario?, Int> {
    do {
        menuMenuDeInicio()
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> {
                // Iniciar sesión
                val usuario = inicioSesion(foro)
                return if (usuario != null) Pair(usuario, 1) else Pair(null, 1)
                //return Pair(estado, 1)
            }
            "2" -> {
                // Registro usuarios
                registroUsuario(foro)
                return Pair(null, 1)
            }
            "84938493" -> {
                sesionInvitado() // Pendiente de arreglar, sesión invitado
                return Pair(null, 1) // Hay que cambiar lo de que retorne null
            }
            "0" -> {
                println("Saliendo del programa...")
            }
            else -> println("Escribe una opción valida (1-4).")
        }
    } while (opcio != "0")
    return Pair(null, 0)
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
        6. Responder a una pregunta
        0. Cerrar sesión
        ------------------------------------
    """.trimIndent())
}
fun hacerPregunta(foro: Foro, usuario: Usuario) {
    print("Escribe el titulo de la pregunta, ej: Ordenador roto. ")
    val titulo = readln().trim().replaceFirstChar { it.uppercase() }
    print("Escribe la descripción de tu pregunta, ej: El ordenador se enciende pero la pantalla no da señal. ")
    val descripcion = readln().trim().replaceFirstChar { it.uppercase() }
    foro.crearPregunta(usuario.nom, usuario.id, titulo, descripcion)
}
fun buscarPorId(foro: Foro) {
    print("Id a buscar: ")
    val id = readln().toIntOrNull() ?: 0
    Utils.buscarPorID(foro, id)
}
fun responderPregunta(foro: Foro, usuario: Usuario) {
    print("Escribe el ID de la pregunta que quieres responder: ")
    val idPregunta = readln().toIntOrNull() ?: 0
    print("Escribe tu respuesta: ")
    val respuesta = readln().trim().replaceFirstChar { it.uppercase() }
    foro.responderPregunta(idPregunta, usuario.id, usuario.nom, respuesta)
}
fun menuSesionIniciada(foro: Foro, usuario: Usuario): Boolean {
    do {
        menuMenuSesionIniciada()
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> foro.mostrarPreguntasTotales(foro.preguntas)
            "2" -> hacerPregunta(foro, usuario)
            "3" -> foro.mostrarPreguntasPropias(foro.preguntas, usuario)
            "4" -> buscarPorId(foro)
            "5" -> println("Ahora mismo hay un total de: ${Utils.contarPreguntas(foro.preguntas)} preguntas.")
            "6" -> responderPregunta(foro, usuario)
            "0" -> println("Cerrando sesión...")
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
    // Para no tener que crear el usuario durante las pruebas
    foro.registrarUsuario("Jordi", "123")
    var usuarioActual: Usuario? = null
    //var usuarioLog = false
    var seguirPrograma = 0
    do {
        if (usuarioActual == null) {
            val (usuario, programa) = menuDeInicio(foro)
            usuarioActual = usuario
            seguirPrograma = programa
        } else {
            val sigueLogueado = menuSesionIniciada(foro, usuarioActual)
            if (!sigueLogueado) {
                usuarioActual = null
            }
        }
    } while (seguirPrograma != 0)
}