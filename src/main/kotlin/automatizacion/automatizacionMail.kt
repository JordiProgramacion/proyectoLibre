package automatizacion

import models.Usuario

// Autentificación
import java.util.Properties
import jakarta.mail.*
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage

class automatizacionMail() {

    companion object {

        val correoAdmin = "automatizacionkotlingerard@gmail.com"

        // No tiene que ser muy buena guardar la contraseña en codigo pero no hay otra manera sencilla de hacerlo.
        val contraseña = "ecmf raub toaf kyci"
        // contra: EwnizEv5EwnizEv5
        // clave de acceso:

        fun verInformacionUsuario(usuario: Usuario) {
            // ... (toda la configuración de props y session se queda igual) ...
            /*
            try {
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(remitente))

                    // Accedemos directamente a las propiedades del objeto usuario
                    addRecipient(Message.RecipientType.TO, InternetAddress(usuario.mail))
                    setSubject("Recuperación de cuenta - Foro Blanxart")

                    setText("""
                Hola ${usuario.nom},

                Has solicitado tus datos de acceso al Foro.
                Tu contraseña es: ${usuario.contrasena}

                ¡Nos vemos en el foro!
            """.trimIndent())
                }

                Thread {
                    Transport.send(message)
                    println("\n[SISTEMA] Correo enviado a ${usuario.nom}")
                }.start()

            } catch (e: Exception) {
                println("[ERROR] No se pudo enviar el correo: ${e.message}")
            }
        }

 */
        }
        // Mensaje al enviar el correo:
        // println("Hola ${usuariosConMail.nom}, en breve recibirá un correo electronico con información de su cuenta si no le llega\nel correo en 5 minutos vuelva a intentarlo, si el error persiste contacta con un administrador.")
    }
}
// por hacer