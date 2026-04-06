package utils

import models.Pregunta
import core.Foro

class Utils() {
    companion object {
        // Ver número de preguntas en toda la aplicación
        fun contarPreguntas(preguntas: MutableList<Pregunta>): Int {
            return preguntas.size
        }
        // Buscar preguntas por ID
        fun buscarPorID(foro: Foro, id: Int) {
            foro.preguntas.forEach {
                if (it.id == id) {
                    println("""
                        ====================================================
                        |            INFORMACIÓN DE LA PREGUNTA            |
                        ====================================================
        
                        TÍTULO:      ${it.titulo}
                        CREADOR:     ${it.nombreAutor} ${it.idAutor}
                        ID PREGUNTA: ${it.id}
          
                        DESCRIPCIÓN:
                        ${it.descripcion}
          
                        ====================================================
                    """.trimIndent())
                }
            }
        }

    }
}