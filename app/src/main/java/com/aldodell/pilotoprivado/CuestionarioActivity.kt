package com.aldodell.pilotoprivado

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CuestionarioActivity : AppCompatActivity() {

    lateinit var tvPregunta: TextView
    lateinit var rvRespuestas: RecyclerView
    lateinit var preguntas: List<Pregunta>
    var indicePregunta = 0
    lateinit var registro: Registro


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuestionario)

        tvPregunta = findViewById(R.id.tvPregunta)
        rvRespuestas = findViewById(R.id.rvRespuestas)
        rvRespuestas.layoutManager = LinearLayoutManager(this)

        val materia = this.intent.getStringExtra("materia")!!
        preguntas = baseDatos.preguntaDao().preguntasPorMateria(materia)
        registro = Registro(this)
        siguiente()

    }

    fun siguiente() {

        runOnUiThread {
            if (!registro.puedeUsar) {
                val intento = Intent(this, RegistroActivity::class.java)
                startActivity(intento)
            }

            //Cuando alcanzamos el final Damos un mensaje y salimos de aqu'i
            if (indicePregunta == preguntas.count()) {

                AlertDialog
                    .Builder(this)
                    .setMessage(R.string.mensaje_fin_materia)
                    .setPositiveButton(
                        R.string.ok,
                        DialogInterface.OnClickListener { dialogInterface, i ->
                            finish()
                        })
                    .create()
                    .show()
            } else {
                tvPregunta.setText(preguntas[indicePregunta].pregunta)
                rvRespuestas.adapter = PreguntaAdaptador(preguntas[indicePregunta]) { siguiente() }
                indicePregunta += 1
            }
        }
    }


}