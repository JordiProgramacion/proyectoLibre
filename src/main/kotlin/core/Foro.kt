package core

import models.Pregunta
import models.Respuesta
import models.Usuario
import models.UsuarioAdmin

class Foro() {

    val preguntas: MutableList<Pregunta> = mutableListOf()
    private val baneados: MutableList<Usuario> = mutableListOf()
    val usuarios: MutableList<Usuario> = mutableListOf()
    private val administradores: MutableList<UsuarioAdmin> = mutableListOf()
    private val idS = usuarios.size + administradores.size + 1
    private val idsPreguntas = preguntas.size + 1
    val respuestas: MutableList<Respuesta> = mutableListOf()
    private val idSRespuestas = respuestas.size + 1
    private val idSAdmins = administradores.size + usuarios.size + 1

    fun anadirListaUsuarios(usuario: Usuario): Boolean {
        return usuarios.add(usuario)
    }
    fun anadirListaAdministradores(usuarioAdmin: UsuarioAdmin): Boolean {
        return administradores.add(usuarioAdmin)
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
        val nuevoUsuario = Usuario(nom = nom, id = idNuevoUsuario, contrasena = contrasena)
        if (anadirListaUsuarios(nuevoUsuario)) {
            println("El usuario ${nuevoUsuario.nom} con id ${nuevoUsuario.id} se agrego correctamente, ya puede iniciar sesión\n(no olvide su id, sin el no podra acceder a la aplicación).")
        }
    }
    // Hacer privada esta funcion una vez exista la persistencia de datos
    fun registrarAdmin(nom: String, contrasena: String) {
        val idAdministrador = idSAdmins
        val nuevoAdministrador = UsuarioAdmin(nom, idAdministrador, contrasena = contrasena)
        if (anadirListaAdministradores(nuevoAdministrador)) {
            println("El usuario ${nuevoAdministrador.nom} con id ${nuevoAdministrador.id} se agrego correctamente, ya puede iniciar sesión\n(no olvide su id, sin el no podra acceder a la aplicación).")
        }
    }
    fun iniciarSesion(id: Int, contrasena: String): Boolean {

        val usuario = usuarios.find { it.id == id }
        if (usuario != null) {
            if (usuario.contrasena == contrasena) {
                usuario.iniciarSesion()
                println("Inicio de sesión correcto, bienvenido ${usuario.nom}.")
                return true
            } else {
                println("La contraseña es incorrecta.")
                return false
            }
        }
        val admin = administradores.find { it.id == id }
        if (admin != null) {
            if (admin.contrasena == contrasena) {
                admin.iniciarSesion()
                println("Has iniciado sesión como administrador, bienvenido ${admin.nom}.")
                return true
            } else {
                println("La contraseña es incorrecta.")
                return false
            }
        }
        println("El id introducido no pertenece a ningún usuario, puedes registrare para poder iniciar sesión!")
        return false
    }
    fun responderPregunta(idPregunta: Int, idAutor: Int, nombreAutor: String, respuesta: String) {
        val pregunta = preguntas.find { idPregunta == it.id }
        if (pregunta != null) {
            val nuevaRespuesta = Respuesta(nombreAutor, idAutor, idPregunta, respuesta, idSRespuestas)
            respuestas.add(nuevaRespuesta)
            println("La respuesta se ha añadido correctamente.")
        } else {
            println("No se ha encontrado ninguna pregunta con el id: $idPregunta")
        }
    }
    fun mostrarPreguntasTotales(preguntas: MutableList<Pregunta>) {
        if (preguntas.isEmpty()) {
            println("Todavía no hay ninguna pregunta, puedes escribir la primera!!!")
        } else {
            preguntas.forEach { pregunta ->
                println("""
            ====================================================
            |            INFORMACIÓN DE LA PREGUNTA            |
            ====================================================
    
            TÍTULO:      ${pregunta.titulo}
            CREADOR:     ${pregunta.nombreAutor}
            ID PREGUNTA: ${pregunta.id}
      
            DESCRIPCIÓN:
            ${pregunta.descripcion}
            """.trimIndent())
                val respuestasDeEstaPregunta = respuestas.filter { it.idPreguntaOriginal == pregunta.id }
                if (respuestasDeEstaPregunta.isNotEmpty()) {
                    println("""
                |                RESPUESTAS                        |
                ----------------------------------------------------""".trimIndent())

                    respuestasDeEstaPregunta.forEach { resp ->
                        println("""
                    > @${resp.nombreAutor} respondió:
                      "${resp.respuesta}"
                    ----------------------------------------------------""".trimIndent())
                    }
                }
                println("====================================================\n")
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
                  > ID:          ${it.id}
    
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