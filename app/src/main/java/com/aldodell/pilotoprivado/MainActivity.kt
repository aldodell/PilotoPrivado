package com.aldodell.pilotoprivado

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room


lateinit var baseDatos: BaseDatos
const val nombreArchivoBaseDatos = "raw/ppa.db3"
lateinit var rvMaterias: RecyclerView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMaterias = findViewById(R.id.rvMaterias)

        baseDatos = Room.databaseBuilder(this.baseContext, BaseDatos::class.java, "preguntas")
            .createFromInputStream { resources.openRawResource(R.raw.ppa) }
            .allowMainThreadQueries()
            .build()

        baseDatos.preguntaDao().reiniciar()

        val materias = baseDatos.preguntaDao().materias()
        rvMaterias.adapter = MateriasAdaptador(materias)
        rvMaterias.layoutManager = LinearLayoutManager(this)

    }
}