package com.aldodell.pilotoprivado

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MostrarResultadosActivity : AppCompatActivity() {


    lateinit var btAceptar0: Button
    lateinit var tvResultados: TextView

    var totalCorrectas = 0
    var totalSobre = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_resultados)

        //
        btAceptar0 = findViewById(R.id.btAceptar0)
        tvResultados = findViewById(R.id.tvResultados)
        //

        tvResultados.setText(procesarResultados())

        btAceptar0.setOnClickListener {
            finish()
        }


    }

    fun procesarResultados(): String {
        val sb = StringBuilder()
        val materias = baseDatos.preguntaDao().materias()

        materias.forEach { materia ->

            val correctas = baseDatos.preguntaDao().respuestasCorrectas(materia)
            val sobre = baseDatos.preguntaDao().cantidadDePreguntasPorMateria(materia)

            totalCorrectas += correctas
            totalSobre += sobre

            sb.append(materia)
            sb.appendLine()
            sb.append(correctas)
            sb.append(" correctas de ")
            sb.append(sobre)
            sb.appendLine()
            sb.appendLine()

        }

        sb.append("Calificación relativa: ")
        sb.appendLine()
        sb.append(totalCorrectas)
        sb.append(" de ")
        sb.append(totalSobre)
        sb.appendLine()

        val puntuacion = 100 * (totalCorrectas / totalSobre)
        sb.append("Puntuación: ")
        sb.append(puntuacion).append(puntuacion)


        return sb.toString()
    }

}