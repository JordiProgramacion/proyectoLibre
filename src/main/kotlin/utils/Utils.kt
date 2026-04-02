package utils

import models.Pregunta

class Utils() {
    companion object {
        // Ver número de preguntas en toda la aplicación
        fun contarPreguntas(preguntas: MutableList<Pregunta>): Int {
            return preguntas.size
        }
        // Siguiente
    }
}