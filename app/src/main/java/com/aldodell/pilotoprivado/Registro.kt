package com.aldodell.pilotoprivado

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Registro(context: Context) {
    val preferencias = context.getSharedPreferences("registro", AppCompatActivity.MODE_PRIVATE)

    fun generador(): String {
        var r = preferencias.getString("semilla", null)
        if (r == null) {
            r = ""
            for (i in 0..3) {
                val d = Random.nextInt(15)
                r += d.toString(16)
            }
            preferencias.edit().putString("semilla", r).apply()
        }
        return r
    }

    fun procesarRegistro(codigo: String): Boolean {

        val semilla = generador()
        var paso = true

        for (i in 0..3) {
            val d = codigo.substring(i, i + 1)
            val n = d.toInt(16)

            val s = semilla.substring(i, i + 1)
            val m = s.toInt(16)
            if ((n + m) != 15) {
                paso = false
            }
        }

        if (paso) {
            preferencias.edit().putBoolean("activo", true).apply()
        } else {
            preferencias.edit().putBoolean("activo", false).apply()
        }

        return paso
    }

    init {
        generador()
    }

    val valido: Boolean
        get() {

            return preferencias.getBoolean("activo", false)
        }

}