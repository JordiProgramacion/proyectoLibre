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
    private val idsPreguntas = preguntas.size + 1

    fun anadirListaUsuarios(usuario: Usuario): Boolean {
        return usuarios.add(usuario)
    }
    fun crearPregunta(nomCreador: String, idCreador: Int, titulo: String, descripcion: String) {
        val idNuevaPregunta = idsPreguntas
        val nuevaPregunta = Pregunta(titulo, idNuevaPregunta, descripcion, nomCreador, idCreador)
        val anadido = preguntas.add(nuevaPregunta)
        if (anadido) {
            println("Se ha añadido la pregunta correctamente.")
        } else {
            println("Error, la pregunta no se ha añadido.")
        }
    }
    fun baneoUsuario() {
// POR HACER
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
        println("La contraseña es incorrecta, asegurese de poner bien tanto el id como su contraseña para poder iniciar \nsesión correctamente.")
        return false
    }
    fun mostrarPreguntasTotales(preguntas: MutableList<Pregunta>) {
        if (preguntas.isEmpty()) {
            println("Todavía no hay ninguna pregunta, puedes escribir la primera!!!")
        } else {
            preguntas.forEach {
                println("""
                ====================================================
                |            INFORMACIÓN DE LA PREGUNTA            |
                ====================================================
        
                TÍTULO:      ${it.titulo}
                CREADOR:     ${it.nombreAutor}
          
                DESCRIPCIÓN:
                ${it.descripcion}
          
                ====================================================
            """.trimIndent())
            }
        }
    }
    fun mostrarPreguntasPropias(preguntas: MutableList<Pregunta>, usuario: Usuario) {
        // Hacemos otra lista con las preguntas buenas
        val misPreguntas = preguntas.filter { it.idAutor == usuario.id }

        if (misPreguntas.isEmpty()) {
            println("Todavía no has publicado ninguna pregunta.")
        } else {
            misPreguntas.forEach {
                println("""
                ====================================================
                |              DETALLES DE LA PREGUNTA             |
                ====================================================
                  > ID PREGUNTA: #${it.id}
                  > TÍTULO:      ${it.titulo}
    
                  DESCRIPCIÓN:
                  ${it.descripcion}

                ----------------------------------------------------
                  DATOS DEL AUTOR
                  ID: ${it.idAutor} | Nombre: ${it.nombreAutor}
               ====================================================
            """.trimIndent())
            }
        }
    }
}