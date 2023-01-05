package com.aldodell.pilotoprivado

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class MateriasAdaptador(val materias: List<String>) :
    RecyclerView.Adapter<MateriasAdaptador.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val boton: Button

        init {
            boton = itemView.findViewById(R.id.btMateria)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.materia_fila, parent, false)
        return (ViewHolder(view))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.boton.setText(materias[position])
        holder.boton.setOnClickListener {
            val mIntent = Intent(it.context, CuestionarioActivity::class.java)
            mIntent.putExtra("materia", materias[position])
            it.context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return materias.count()
    }
}