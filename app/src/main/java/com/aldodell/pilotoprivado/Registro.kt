package com.aldodell.pilotoprivado

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Registro(val context: Context, val mascara: Int = 0xFFFF, val limite: Int = 100) {

    //Referencia a las preferencias de registro
    val preferencias = context.getSharedPreferences("registro", AppCompatActivity.MODE_PRIVATE)

    //Genera una cadena aleatoria para ser validada
    fun generador(): String {


        var r = preferencias.getString("semilla", null)

        //La primera vez crea una semilla y habilita las pruebas
        if (r == null) {
            r = Random.nextInt(mascara).toString(16)
            preferencias.edit()
                .putString("semilla", r)
                .putBoolean("activo", false)
                .putInt("pruebas", limite)
                .apply()

        }
        return r
    }

    //Procesa un registro, si es correcto lo guarda
    fun procesarRegistro(codigo: String): Boolean {
        var paso = true
        val semilla = generador()
        val a = mascara - semilla.toInt(16)
        paso = codigo.trim() == a.toString(16)
        preferencias.edit()
            .putBoolean("activo", paso)
            .apply()
        return paso
    }


    val activo: Boolean
        get() {
            return preferencias.getBoolean("activo", false)
        }

    val puedeProbar: Boolean
        get() {
            pruebas -= 1
            return pruebas > 0
        }

    var pruebas: Int
        get() {
            val p = preferencias.getInt("pruebas", -1)
            if (p < 0) pruebas = limite
            return p
        }
        set(value) {
            var p = value
            if (p < 0) p = 0
            preferencias.edit().putInt("pruebas", p).apply()
        }

    val puedeUsar: Boolean
        get() = activo || puedeProbar

    init {
        generador()
    }


}