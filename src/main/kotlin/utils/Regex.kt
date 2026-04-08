package utils

class Regex {

    companion object {
        // Para contraseñas utilizamos este
        fun validarRegex(contra: String): Boolean {
            val contraRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
            return contraRegex.matches(contra)
        }
        // Otro regex pero este para validar un correo electronico
        fun mailRegex(mail: String): Boolean {
            val mailRegex = "^[a-zA-Z0-9._%+-]+@insdanielblanxart\.cat$".toRegex()
            return mailRegex.matches(mail)
        }
    }
}