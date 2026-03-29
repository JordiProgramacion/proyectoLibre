package models

class UsuarioInvitado(nom: String = "invitado", id: Int = 0, sesion: Boolean, escritura: Boolean = false, contrasena: String = "invitado"):
    Usuario(nom, id, sesion, escritura, contrasena) {
}