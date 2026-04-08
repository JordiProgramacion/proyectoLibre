package app

import core.Foro
import utils.Utils
import models.Usuario
import models.UsuarioAdmin
import utils.Regex
// Todavía no se usa import models.UsuarioInvitado

fun registroUsuario(foro: Foro) {
    print("Bienvenido al panel de registro de usuarios, por favor ingrese su nombre: ")
    val nombre = readln().trim().replaceFirstChar {it.uppercase()}
    if (nombre.isBlank()) {
        return println("Tienes que escribir algo en el apartado de nombre.")
    }
    print("Ahora ingrese una contraseña segura: ")
    val contrasenaNuevoUsuario = readln()
    // Implementar metodo para que no se vea la contraseña en el terminal
    if (Regex.validarRegex(contrasenaNuevoUsuario)) {
        foro.registrarUsuario(nombre, contrasenaNuevoUsuario)
    } else {
        println("La contraseña no es segura, asegurate de que tenga minimo una mayuscula, una minuscula, un numero y sea por lo menos de 8 caracteres.")
    }
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
            "4" -> recuperarContrasena()
            "0" -> {
                println("Saliendo del programa...")
            }
            else -> println("Escribe una opción valida (1-4).")
        }
    } while (opcio != "0")
    return Pair(null, 0)
}
fun recuperarContrasena() {
    println("Escribe tu correo y te mandaremos un mail con tu información")
    val correo = readln().trim()
    if (utils.Regex.mailRegex(correo)) {
        println("ver si")
    } else {
        println("Correo invalido, solo permitimos correos tipo @insdanilblanxart.cat")
    }
}
fun menuMenuSesionIniciada(usuario: Usuario) {
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
    """.trimIndent())

    if (usuario is UsuarioAdmin) {
        println("""
            7. Ver todos los usuarios registrados
            8. Quitar permisos de escritura a usuario
            9. Devolver permisos de escritura a usuario
            10. Banear a un usuario (eliminar)
        """.trimIndent())
    }
    println("""
        11. Añadir correo electronico (gmail)
        0. Cerrar sesión
        ------------------------------------
    """.trimIndent())
}
fun hacerPregunta(foro: Foro, usuario: Usuario) {
    print("Escribe el titulo de la pregunta, ej: Ordenador roto. ")
    val titulo = readln().trim().replaceFirstChar { it.uppercase() }
    if (titulo.isBlank()) {
        return println("El titulo no puede estar en blanco.")
    }
    print("Escribe la descripción de tu pregunta, ej: El ordenador se enciende pero la pantalla no da señal. ")
    val descripcion = readln().trim().replaceFirstChar { it.uppercase() }
    if (descripcion.isBlank()) {
        return println("La descripción no puede estar en blanco")
    }
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
    if (respuesta.isBlank()) {
        return println("Tu respuesta no puede estar en blanco, como vas a responder?")
    }
    foro.responderPregunta(idPregunta, usuario.id, usuario.nom, respuesta)
}
fun menuSesionIniciada(foro: Foro, usuario: Usuario): Boolean {
    do {
        menuMenuSesionIniciada(usuario)
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> foro.mostrarPreguntasTotales(foro.preguntas)
            "2" -> {
                if (usuario.escritura == false) {
                    println("No tienes permisos de escritura, si un administrador te he quitado los permisos puedes apelar enviando un correo a 'jmart3@insdanielblanxart.cat'")
                    continue
                }
                hacerPregunta(foro, usuario)
            }
            "3" -> foro.mostrarPreguntasPropias(foro.preguntas, usuario)
            "4" -> buscarPorId(foro)
            "5" -> println("Ahora mismo hay un total de: ${Utils.contarPreguntas(foro.preguntas)} preguntas.")
            "6" -> {
                if (usuario.escritura == false) {
                    println("No tienes permisos de escritura, si un administrador te he quitado los permisos puedes apelar enviando un correo a 'jmart3@insdanielblanxart.cat'")
                    continue
                }
                responderPregunta(foro, usuario)
            }
            "7" -> {
                if (usuario !is UsuarioAdmin) {
                    println("Esta función solo esta disponible para administradores.")
                    continue
                }
                foro.listarUsuarios()
            }
            "8" -> {
                if (usuario !is UsuarioAdmin) {
                    println("Esta función solo esta disponible para administradores.")
                    continue
                }
                quitarPermisosEscritura(foro)
            }
            "9" -> {
                if (usuario !is UsuarioAdmin) {
                    println("Esta función solo esta disponible para administradores.")
                    continue
                }
                darPermisosEscritura(foro)
            }
            "10" -> {
                if (usuario !is UsuarioAdmin) {
                    println("Esta función solo esta disponible para administradores.")
                    continue
                }
                println("Por hacer")
                banearUsuario(foro)
            }
            "11" -> mail(foro, usuario)
            "0" -> println("Cerrando sesión...")
            else -> println("Escribe una opción valida (1-4)")
        }
    } while (opcio != "0")
    return false
}
fun mail(foro: Foro, usuario: Usuario()) {
    println("Escribe tu correo electronico (acabado el @insdanielblanxart.cat)")
    val mail = readln().trim()
     if (utils.Regex.mailRegex(mail)) {
        usuario.mail = mail
        println("Se ha añadido correctamente el mail, ahora podras recuperar tu contraseña desde este.")
     } else {
        println("El correo electornico no existe o no pertenece a @insdanielblanxart.cat, si no es un error\n contacta con un administrador para que te ayude.")
     }
}
// Por hacer FALTA LA FUNCION PARA BANEAR USUARIOS !importante
fun banearUsuario(foro: Foro) {
    foro.usuarios.forEach {
        println(it.identificarse())
    }
    println("ID del usuario a banear: ")
    val id = readln().trim()
    // Metodo del foro para banear AQUÍ
}
fun darPermisosEscritura(foro: Foro) {
    val usuariosNoEscritura = foro.usuarios.filter { it.escritura == false }
    usuariosNoEscritura.forEach { println(it.identificarse()) }
    println("Escribe el ID del usuario a devolver el permiso de escritura: ")
    val id = readln().trim().toIntOrNull() ?: -1
    foro.darEscritura(id)
}
fun quitarPermisosEscritura(foro: Foro) {
    val usuariosEscritura = foro.usuarios.filter { it.escritura == true }
    usuariosEscritura.forEach { println(it.identificarse()) }
    println("Escribe el ID del usuario a quitar el permiso de escritura: ")
    val id = readln().trim().toIntOrNull() ?: -1
    foro.quitarEscritura(id)
}
fun sesionInvitado() {
    println("Has iniciado sesión como invitado, solo podras leer conversaciones, si quieres participar puedes iniciar sesión, si no tienes cuenta, registrarte!!!")
}
fun main() {
    val foro = Foro()
    // Para no tener que crear el usuario durante las pruebas
    foro.registrarUsuario("Jordi", "123")
    foro.registrarAdmin("Administrador", "EwnizEv5")
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