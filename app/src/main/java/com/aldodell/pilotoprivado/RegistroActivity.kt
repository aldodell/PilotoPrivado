package com.aldodell.pilotoprivado

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RegistroActivity : AppCompatActivity() {

    lateinit var etCodigoRegistro: EditText
    lateinit var btProcesarRegistro: Button
    lateinit var tvCodigoSemilla: TextView
    lateinit var preferencias: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val registro = Registro(this)

        etCodigoRegistro = findViewById(R.id.etCodigoRegistro)
        btProcesarRegistro = findViewById(R.id.btProcesarRegistro)
        tvCodigoSemilla = findViewById(R.id.tvCodigoSemilla)
        preferencias = this.getPreferences(MODE_PRIVATE)
        tvCodigoSemilla.text = registro.generador()

        btProcesarRegistro.setOnClickListener {
            if (registro.procesarRegistro(etCodigoRegistro.text.toString().trim())) {
                finish()
            }
        }
    }


}