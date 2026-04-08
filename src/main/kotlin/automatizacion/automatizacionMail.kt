package automatizacion

import models.Usuario

class Automatizacion() {

    companion object {

        val correoAdmin = "" // Poner el mail de la aplicación
        // No tiene que ser muy buena guardar la contraseña en codigo pero no hay otra manera sencilla de hacerlo.
        val contraseña = ""

        fun verInformacionCuenta(usuario: Usuario) {
            // AQUÍ VA LA AUTOMATIZACIÓN
        }
    }
    // Mensaje al enviar el correo: 
    // println("Hola ${usuariosConMail.nom}, en breve recibirá un correo electronico con información de su cuenta si no le llega\nel correo en 5 minutos vuelva a intentarlo, si el error persiste contacta con un administrador.")
}
// por hacer