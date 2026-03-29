package models

class Usuario(val nom: String, val id: Int, var sesion: Boolean = false) {

    fun identificarse() {

        println("Me llamo $nom, mi id es $id y mi sesión ${if (sesion) "esta iniciada" else "no esta iniciada"}")

    }
}