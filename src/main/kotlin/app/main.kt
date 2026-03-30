package app

import core.Foro
import models.Usuario

fun registroUsuario(foro: Foro) {
    print("Bienvenido al panel de registro de usuarios, por favor ingrese su nombre: ")
    val nombre = readln().replaceFirstChar {it.uppercase()}
    print("Ahora ingrese una contraseña segura: ")
    // Implementar metodo para que no se vea la contraseña en el terminal
    val contrasenaNuevoUsuario = readln()
    foro.registrarUsuario(nombre, contrasenaNuevoUsuario)
}
fun inicioSesion(foro: Foro) {
    print("Bienvenido al panel de inicio de sesión, por favor ingrese su id: ")
    val idUsuario = readln().toIntOrNull() ?: 10000000
    print("Ahora introduzca su contraseña: ")
    val contraUsuario = readln()
    foro.iniciarSesion(idUsuario, contraUsuario)
}
fun menuDeInicio(foro: Foro) {
    do {
        print("""
            --------------------
            Inicio de sesión
            --------------------
            1. Iniciar sesión
            2. Registrarse
            3. Sesión de invitado (solo lectura) // ESTO SE IMPLEMENTARÁ EN LA SIGUIENTE VERSIÓN
            4. Salir del programa
            --------------------
        """.trimIndent())
        print("\nEscoja la opció que quiere hacer: ")
        val opcio = readln()
        when (opcio) {
            "1" -> {
                // Iniciar sesión
                inicioSesion(foro)
            }
            "2" -> {
                // Registro usuarios
                registroUsuario(foro)
            }
            "84938493" -> {
                // Aquí va la sesión de invitado (siguiente versión)
                print("pendiente de actualización")
            } // Sesion de invitado
            "4" -> {
                print("Saliendo del programa")
            }
            else -> print("Escribe una opción valida (1-4).")
        }
    } while (opcio != "4")//haceralgo para saalir al iniciar seson
}
fun iniciarSesion() {

}
fun sesionInvitado() {
    println("Has iniciado sesión como invitado, solo podras leer conversaciones, si quieres participar puedes iniciar sesión, si no tienes cuenta, registrarte!!!")
}
fun main() {

    val foro = Foro()
    // hacer algo inicio sesión
    val salir = menuDeInicio(foro)
    print("golaaaaaaaa")
    val prueba = readln()
}