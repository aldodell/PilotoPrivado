package com.aldodell.pilotoprivado

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CuestionarioActivity : AppCompatActivity() {

    lateinit var tvPregunta: TextView
    lateinit var rvRespuestas: RecyclerView
    lateinit var preguntas: List<Pregunta>
    var indicePregunta = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)

        tvPregunta = findViewById(R.id.tvPregunta)
        rvRespuestas = findViewById(R.id.rvRespuestas)
        rvRespuestas.layoutManager = LinearLayoutManager(this)

        val materia = this.intent.getStringExtra("materia")!!
        preguntas = baseDatos.preguntaDao().preguntasPorMateria(materia)

        siguiente()

    }

    fun siguiente() {
        tvPregunta.setText(preguntas[indicePregunta].pregunta)
        rvRespuestas.adapter = PreguntaAdaptador(preguntas[indicePregunta]) { siguiente() }
        indicePregunta += 1
    }
}