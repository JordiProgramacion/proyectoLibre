package core

import models.Pregunta
import models.Respuesta
import models.Usuario
import models.UsuarioAdmin
import com.google.gson.reflect.TypeToken
import persistencia.JsonRepository

class Foro() {

    private val repoUsuarios = JsonRepository<Usuario>("usuarios.json", object : TypeToken<List<Usuario>>() {}.type)
    private val repoAdmins =
        JsonRepository<UsuarioAdmin>("administradores.json", object : TypeToken<List<UsuarioAdmin>>() {}.type)
    private val repoPreguntas = JsonRepository<Pregunta>("preguntas.json", object : TypeToken<List<Pregunta>>() {}.type)
    private val repoRespuestas =
        JsonRepository<Respuesta>("respuestas.json", object : TypeToken<List<Respuesta>>() {}.type)

    val usuarios: MutableList<Usuario> = repoUsuarios.findAll().toMutableList()
    private val administradores: MutableList<UsuarioAdmin> = repoAdmins.findAll().toMutableList()
    val preguntas: MutableList<Pregunta> = repoPreguntas.findAll().toMutableList()
    val respuestas: MutableList<Respuesta> = repoRespuestas.findAll().toMutableList()
    fun guardarTodo() {
        repoUsuarios.saveAll(usuarios)
        repoAdmins.saveAll(administradores)
        repoPreguntas.saveAll(preguntas)
        repoRespuestas.saveAll(respuestas)
    }
    fun anadirListaUsuarios(usuario: Usuario): Boolean {
        val exito = usuarios.add(usuario)
        if (exito) repoUsuarios.saveAll(usuarios)
        return exito
    }
    fun anadirListaAdministradores(usuarioAdmin: UsuarioAdmin): Boolean {
        val exito = administradores.add(usuarioAdmin)
        if (exito) repoAdmins.saveAll(administradores)
        return exito
    }
    fun quitarUsuario(id: Int): Boolean {
        val usuarioEliminar = usuarios.find { it.id == id }
        if (usuarioEliminar == null) {
            println("El ID introducido no pertenece a ningun usuario.")
            return false
        }
        usuarios.remove(usuarioEliminar)
        repoUsuarios.saveAll(usuarios)
        println("Usuario eliminado correctamente")
        return true
    }
    fun crearPregunta(nomCreador: String, idCreador: Int, titulo: String, descripcion: String) {
        val idNuevaPregunta = preguntas.size + 1
        val nuevaPregunta = Pregunta(titulo, idNuevaPregunta, descripcion, nomCreador, idCreador)
        if (preguntas.add(nuevaPregunta)) {
            repoPreguntas.saveAll(preguntas) // Guardar pregunta nueva
            println("Se ha añadido la pregunta correctamente.")
        } else {
            println("Error, la pregunta no se ha añadido.")
        }
    }
    fun registrarUsuario(nom: String, contrasena: String) {
        val idNuevoUsuario = usuarios.size + administradores.size + 1
        val nuevoUsuario = Usuario(nom = nom, id = idNuevoUsuario, contrasena = contrasena)
        if (anadirListaUsuarios(nuevoUsuario)) {
            println("El usuario ${nuevoUsuario.nom} con id ${nuevoUsuario.id} se agregó correctamente.")
        }
    }
    fun registrarAdmin(nom: String, contrasena: String) {
        val idAdministrador = usuarios.size + administradores.size + 1
        val nuevoAdministrador = UsuarioAdmin(nom, idAdministrador, contrasena = contrasena)
        if (anadirListaAdministradores(nuevoAdministrador)) {
            println("El administrador ${nuevoAdministrador.nom} se agregó correctamente.")
        }
    }

    fun responderPregunta(idPregunta: Int, idAutor: Int, nombreAutor: String, respuesta: String) {
        val idSRespuestas = respuestas.size + 1
        val pregunta = preguntas.find { idPregunta == it.id }
        if (pregunta != null) {
            val nuevaRespuesta = Respuesta(nombreAutor, idAutor, idPregunta, respuesta, idSRespuestas)
            respuestas.add(nuevaRespuesta)
            repoRespuestas.saveAll(respuestas) // Guardar respuesta nueva
            println("La respuesta se ha añadido correctamente.")
        } else {
            println("No se ha encontrado ninguna pregunta con el id: $idPregunta")
        }
    }

    fun quitarEscritura(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        if (usuario == null) return false
        usuario.quitarEscritura()
        repoUsuarios.saveAll(usuarios) // Guardar el cambio de estado
        println("Permiso de escritura quitado.")
        return true
    }

    fun darEscritura(id: Int): Boolean {
        val usuario = usuarios.find { it.id == id }
        if (usuario == null) return false
        usuario.devolverEscritura()
        repoUsuarios.saveAll(usuarios) // Guardar el cambio de estado
        println("Permiso de escritura devuelto.")
        return true
    }

    fun buscarUsuarioInicio(id: Int, contrasena: String): Usuario? {
        return usuarios.find { it.id == id && it.contrasena == contrasena }
            ?: administradores.find { it.id == id && it.contrasena == contrasena }
    }

    fun iniciarSesion(id: Int, contrasena: String): Boolean {
        val user = buscarUsuarioInicio(id, contrasena)
        return if (user != null) {
            user.iniciarSesion()
            println("Bienvenido ${user.nom}")
            true
        } else {
            println("Credenciales incorrectas")
            false
        }
    }

    fun existeMail(mail: String): Usuario? {
        return usuarios.find { it.mail == mail } ?: administradores.find { it.mail == mail }
    }

    // --- MÉTODOS DE MOSTRAR (Se quedan igual) ---
    fun listarUsuarios() {
        println("Usuarios:"); usuarios.forEach { println(it.identificarse()) }
        println("Admins:"); administradores.forEach { println(it.identificarse()) }
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
                println(
                    """
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
            """.trimIndent()
                )
            }
        }
    }
}