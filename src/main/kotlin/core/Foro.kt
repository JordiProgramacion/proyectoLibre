package core

import models.Pregunta
import models.Usuario
import models.UsuarioAdmin

class Foro() {

    val preguntas: MutableList<Pregunta> = mutableListOf()
    private val baneados: MutableList<Usuario> = mutableListOf()
    val usuarios: MutableList<Usuario> = mutableListOf()
    private val administradores: MutableList<UsuarioAdmin> = mutableListOf()
    private val idS = usuarios.size + administradores.size + 1

    fun anadirListaUsuarios(usuario: Usuario): Boolean {
        return usuarios.add(usuario)
    }
    fun crearPregunta() {
        
    }
    fun baneoUsuario() {

    }
    fun registrarUsuario(nom: String, contrasena: String) {
        val idNuevoUsuario = idS
        val newUsuario = Usuario(nom = nom, id = idNuevoUsuario, contrasena = contrasena)
        anadirListaUsuarios(newUsuario)
        if (anadirListaUsuarios(newUsuario)) {
            println("El usuario ${newUsuario.nom} con id ${newUsuario.id} se agrego correctamente, ya puede iniciar sesión\n(no olvide su id, sin el no podra acceder a la aplicación).")
        }
    }
    fun iniciarSesion(id: Int, contrasena: String): Boolean {
        usuarios.forEach {
            if (it.id == id) {
                if (it.contrasena == contrasena) {
                    it.iniciarSesion()
                    println("Has iniciado sesión correctamente, bienvenido ${it.nom}.")
                    return true
                }
                println("Contraseña incorrecta, vuelva a intentarlo y asegurese que el ID introducido es el de su cuenta.")
                return false
            }
            println("El id introducido no pertenece a ningun usuario.")
            return false
        }
        println("Error inesperado, vuelva a intentarlo, si este error permanece contacte con un administrador \nmandado un mail a la siguiente dirección: jmart3@insdanielblanxart.cat")
        return false
    }
}