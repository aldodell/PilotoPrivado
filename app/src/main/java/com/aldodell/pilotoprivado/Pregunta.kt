package com.aldodell.pilotoprivado

import androidx.room.*

@Entity(tableName = "preguntas")
data class Pregunta(
    @PrimaryKey
    val id: Int,
    val pregunta: String,
    val correcta: String,
    val a: String?,
    val b: String?,
    val c: String?,
    val d: String?,
    val e: String?,
    val materia: String?,
    var usado: String?,
    var respuesta: String?
)

@Dao
interface PreguntaDao {
    @Query("select distinct materia from preguntas order by materia ")
    fun materias(): List<String>

    @Query("select * from preguntas where materia=:materia")
    fun preguntasPorMateria(materia: String): List<Pregunta>

    @Query("update preguntas set respuesta=''")
    fun reiniciar(): Unit

    @Update(entity = Pregunta::class)
    fun actualizar(pregunta: Pregunta)
}

@Database(entities = [Pregunta::class], version = 1)
abstract class BaseDatos : RoomDatabase() {
    abstract fun preguntaDao(): PreguntaDao
}