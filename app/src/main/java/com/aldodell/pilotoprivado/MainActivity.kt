package com.aldodell.pilotoprivado

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


lateinit var baseDatos: BaseDatos
const val nombreArchivoBaseDatos = "raw/ppa.db3"
lateinit var rvMaterias: RecyclerView
lateinit var btReiniciarRespuestas: Button
lateinit var btMostrarResultados: Button
lateinit var tvVersion: TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaterias = findViewById(R.id.rvMaterias)
        btReiniciarRespuestas = findViewById(R.id.btReiniciarRespuestas)
        btMostrarResultados = findViewById(R.id.btMostrarResultados)
        tvVersion = findViewById(R.id.tvVersion)

        baseDatos = Room.databaseBuilder(this.baseContext, BaseDatos::class.java, "preguntas")
            .createFromInputStream { resources.openRawResource(R.raw.ppa) }
            .allowMainThreadQueries()
            .build()

        val materias = baseDatos.preguntaDao().materias()
        rvMaterias.adapter = MateriasAdaptador(materias)
        rvMaterias.layoutManager = LinearLayoutManager(this)

        btReiniciarRespuestas.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage(R.string.mensaje_reinicio)
                .setPositiveButton(
                    R.string.si
                ) { dialogInterface, i ->
                    baseDatos.preguntaDao().reiniciar()
                }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create()
                .show()
        }

        btMostrarResultados.setOnClickListener {
            val intento = Intent(this, MostrarResultadosActivity::class.java)
            this.startActivity(intento)
        }

        val versionName = BuildConfig.VERSION_NAME
        tvVersion.text = versionName

    }
}