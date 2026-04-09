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
        val contrasenya = utils.Contra.obtenerContrasena()
        // contra: EwnizEv5EwnizEv5
        fun verInformacionCuenta(usuario: Usuario) {
            val props = Properties().apply {
                put("mail.smtp.host", "smtp.gmail.com")
                put("mail.smtp.port", "587")
                put("mail.smtp.auth", "true")
                put("mail.smtp.starttls.enable", "true")
            }
            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(correoAdmin, contrasenya)
                }
            })
            try {
                val message = MimeMessage(session).apply {
                    setFrom(InternetAddress(correoAdmin))
                    addRecipient(Message.RecipientType.TO, InternetAddress(usuario.mail!!))
                    setSubject("Recuperación de cuenta - Foro StackOverflow 2.0")
                    setText("""
                Hola ${usuario.nom},

                Has solicitado tus datos de acceso al Foro.
                Tu ID es: ${usuario.id}
                Tu contraseña es: ${usuario.contrasena}

                ¡Nos vemos en el foro!
            """.trimIndent())
                }
                Thread {
                    try {
                        Transport.send(message)
                        println("\n[SISTEMA] Correo enviado a ${usuario.nom} desde $correoAdmin")
                    } catch (e: Exception) {
                        println("\n[ERROR CRÍTICO] El servidor SMTP rechazó el envío: ${e.message}")
                    }
                }.start()
                println("\nHola ${usuario.nom}, en breve recibirá un correo electrónico con información de su cuenta. " +
                        "Si no le llega el correo en 5 minutos vuelva a intentarlo, si el error persiste contacta con un administrador.")
            } catch (e: Exception) {
                println("\n[ERROR] Lo sentimos, ha ocurrido un error al procesar el envío: ${e.message}")
                println("Por favor, inténtelo de nuevo más tarde o contacte con un administrador.")
            }
        }
    }
}