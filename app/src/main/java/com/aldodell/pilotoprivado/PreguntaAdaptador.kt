package com.aldodell.pilotoprivado

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.concurrent.schedule

class PreguntaAdaptador(var pregunta: Pregunta, val siguiente: () -> Unit) :
    RecyclerView.Adapter<PreguntaAdaptador.ViewHolder>() {

    val tiempoTransicion: Long = 1000


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boton: Button

        init {
            boton = itemView.findViewById(R.id.btRespuesta)
        }
    }

    var respuestas = ArrayList<String>()

    init {
        respuestas.add(pregunta.a!!)
        respuestas.add(pregunta.b!!)
        if (!pregunta.c.isNullOrEmpty()) respuestas.add(pregunta.c!!)
        if (!pregunta.d.isNullOrEmpty()) respuestas.add(pregunta.d!!)
        if (!pregunta.e.isNullOrEmpty()) respuestas.add(pregunta.e!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.respuesta_fila, parent, false)
        return (PreguntaAdaptador.ViewHolder(view))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.boton.setText(respuestas[position])
        holder.boton.setOnClickListener {
            var r = ""

            when (position) {
                0 -> r = "A"
                1 -> r = "B"
                2 -> r = "C"
                3 -> r = "D"
                4 -> r = "E"
            }
            pregunta.respuesta = r
            baseDatos.preguntaDao().actualizar(pregunta)


            if (pregunta.correcta != pregunta.respuesta) {
                holder.boton.setBackgroundColor(Color.RED)

            } else {
                holder.boton.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.boton.context,
                        com.google.android.material.R.color.design_default_color_secondary
                    )
                )
            }

            Timer().schedule(tiempoTransicion) {
                holder.boton.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.boton.context,
                        com.google.android.material.R.color.design_default_color_primary

                    )
                )
                siguiente()
            }

        }
    }

    override fun getItemCount(): Int {
        return respuestas.count()
    }
}