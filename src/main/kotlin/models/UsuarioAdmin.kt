package models

class UsuarioAdmin(nom: String, id: Int, sesion: Boolean = false, val permisos: Boolean = true, escritura: Boolean = true, contrasena: String):
    Usuario(nom, id, sesion, escritura, contrasena) {

    fun banear(usuario: Usuario, numero: Int): Int {
        print("por hacer")
        return 12
    }
    override fun iniciarSesion() {
        sesion = true
        println("Has iniciado sesión como administrador.")
    }
    override fun cerrarSesion() {
        sesion = false
        println("La sesión de administrador se ha cerrado.")
    }

}