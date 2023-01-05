package com.aldodell.pilotoprivado

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class PreguntaAdaptador(var pregunta: Pregunta, val siguiente: () -> Unit) :
    RecyclerView.Adapter<PreguntaAdaptador.ViewHolder>() {
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
                0 -> r = "a"
                1 -> r = "b"
                2 -> r = "c"
                3 -> r = "d"
                4 -> r = "e"
            }
            pregunta.respuesta = r
            baseDatos.preguntaDao().actualizar(pregunta)
            siguiente()
        }
    }

    override fun getItemCount(): Int {
        return respuestas.count()
    }
}