package models

class UsuarioAdmin(nom: String, id: Int, sesion: Boolean = false, val permisos: Boolean = true, escritura: Boolean = true, contrasena: String):
    Usuario(nom, id, sesion, escritura, contrasena) {

    fun banear(usuario: Usuario, numero: Int): Int {
        print("por haceer")
        return 12
    }

}