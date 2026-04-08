package automatizacion

import models.Usuario

class Automatizacion() {

    companion object {

        fun verInformacionCuenta(usuario: Usuario) {
            if (usuario.mail != null) {
                // ACCIÓN PARA ENVIAR EL CORREO
            } else {
                println("El usuario introducido no tiene correo electronico, puedes vincular uno a tu cuentan\ desde el menu principal.")
            }
        }
    }
}