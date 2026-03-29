package models

open class Usuario(val nom: String, val id: Int, var sesion: Boolean = false, var escritura: Boolean = true, val contrasena: String) {

    fun identificarse() {

        println("Me llamo $nom, mi id es $id y mi sesión ${if (sesion) "esta iniciada" else "no esta iniciada"}")

    }
}