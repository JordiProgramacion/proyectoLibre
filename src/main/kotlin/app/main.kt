package app

import core.Foro

fun capsulaComienzo(): Boolean {
    do {
        menuInicioSesion()
        val opcio = readln()
        when (opcio) {
            "1" -> {
                print("Ini")
                return true
            } // Iniciar sesión
            "2" -> {
                print("regis")
                return true
            } // Registro
            "3" -> {
                print("invi")
                return true
            } // Sesion de invitado
            "4" -> {
                print("Saliendo del programa")
            }
            else -> print("Escribe una opción valida (1-4).")
        }
    } while (opcio != "4")
    return false

}
fun menuInicioSesion() {

    print("""
        --------------------
        Inicio de sesión
        --------------------
        1. Iniciar sesión
        2. Registrarse
        3. Sesión de invitado (solo lectura)
        4. Salir del programa
        --------------------
    """.trimIndent())
}
fun iniciarSesion() {

}
fun sesionInvitado() {
    println("Has iniciado sesión como invitado, solo podras leer conversaciones, si quieres participar puedes iniciar sesión, si no tienes cuenta, registrarte!!!")
}
fun main() {

    val foro = Foro()
    val salir = capsulaComienzo()
    if (!salir) {
        // Arreglar inicio de sesión.
    }
}