package utils

class Regex {

    companion object {

        fun validarRegex(contra: String): Boolean {
            val contraRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$".toRegex()
            return contraRegex.matches(contra)
        }
    }
}