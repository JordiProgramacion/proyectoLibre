package models

open class Usuario(val nom: String, val id: Int, var sesion: Boolean = false, var escritura: Boolean = true, val contrasena: String) {

    open fun identificarse() {
        println("Nombre: $nom, ID: $id, sesión: ${if (sesion) "esta iniciada" else "no esta iniciada"}")
    }
    open fun iniciarSesion() {
        sesion = true
    }
    open fun cerrarSesion() {
        sesion = false
    }
    fun quitarEscritura() {
        escritura = false
    }
    fun devolverEscritura() {
        escritura = true
    }
}