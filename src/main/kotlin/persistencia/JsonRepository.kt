package persistencia

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.lang.reflect.Type

interface Repository<T> {
    fun findAll(): List<T>
    fun saveAll(items: List<T>)
}
class JsonRepository<T>(
    private val archivoR: String,
    private val tipoClase: Type
) : Repository<T> {
    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val archivo = File(archivoR)
    override fun findAll(): List<T> {
        if (!archivo.exists()) return emptyList()
        return try {
            val contenido = archivo.readText()

            gson.fromJson(contenido, tipoClase) ?: emptyList()
        } catch (e: Exception) {
            println("Error al leer $archivoR: ${e.message}")
            emptyList()
        }
    }
    override fun saveAll(items: List<T>) {
        try {
            val jsonText = gson.toJson(items)
            archivo.writeText(jsonText)
        } catch (e: Exception) {
            println("Error al guardar en $archivoR: ${e.message}")
        }
    }
}