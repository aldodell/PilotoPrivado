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
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

lateinit var baseDatos: BaseDatos
const val nombreArchivoBaseDatos = "raw/ppa.db3"
lateinit var rvMaterias: RecyclerView
lateinit var btReiniciarRespuestas: Button
lateinit var btMostrarResultados: Button
lateinit var tvVersion: TextView

var usuario: FirebaseUser? = null


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

    override fun onStart() {
        super.onStart()

        if (usuario == null) {
            //Vamos a establecer la l'ogica de autenticacion
            val lanzador =
                registerForActivityResult(FirebaseAuthUIActivityResultContract()) { result ->

                    val respuesta = result.idpResponse
                    if (result.resultCode == RESULT_OK) {
                        usuario = FirebaseAuth.getInstance().currentUser

                        if (respuesta!!.isNewUser) {

                            val datos = mutableMapOf<String, Any>(
                                "email" to usuario!!.email!!,
                                "creacion" to usuario!!.metadata!!.creationTimestamp!!
                            )

                            FirebaseFirestore.getInstance()
                                .collection("piloto_privado")
                                .add(datos)
                        }

                    } else {
                        AlertDialog.Builder(this)
                            .setMessage(respuesta!!.error!!.message)
                            .setPositiveButton(R.string.si) { _, _ -> }
                            .create()
                            .show()
                    }
                }
            val intento = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build()
            lanzador.launch(intento)

        }
    }
}