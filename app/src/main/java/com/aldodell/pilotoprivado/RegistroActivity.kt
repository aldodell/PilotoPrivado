package com.aldodell.pilotoprivado

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat

class RegistroActivity : AppCompatActivity() {

    lateinit var etCodigoRegistro: EditText
    lateinit var btProcesarRegistro: Button
    lateinit var tvCodigoSemilla: TextView
    lateinit var preferencias: SharedPreferences
    lateinit var tvMensajeRegistro: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val registro = Registro(this)

        etCodigoRegistro = findViewById(R.id.etCodigoRegistro)
        btProcesarRegistro = findViewById(R.id.btProcesarRegistro)
        tvCodigoSemilla = findViewById(R.id.tvCodigoSemilla)
        preferencias = this.getPreferences(MODE_PRIVATE)
        tvCodigoSemilla.text = registro.generador()
        tvMensajeRegistro = findViewById(R.id.tvMensajeRegistro)

        btProcesarRegistro.setOnClickListener {
            if (registro.procesarRegistro(etCodigoRegistro.text.toString().trim())) {
                finish()
            }
        }


       // val spanned = HtmlCompat.fromHtml(resources.getString(R.string.mensaje_registro),HtmlCompat.FROM_HTML_MODE_LEGACY)
    //    tvMensajeRegistro.text = spanned


    }


}