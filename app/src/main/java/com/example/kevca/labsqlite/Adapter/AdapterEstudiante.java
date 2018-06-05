package com.example.kevca.labsqlite.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kevca.labsqlite.Dominio.Estudiante;
import com.example.kevca.labsqlite.R;

import java.util.ArrayList;

/**
 * Created by kevca on 6/4/2018.
 */

public class AdapterEstudiante extends
        RecyclerView.Adapter<AdapterEstudiante.EstudianteViewHolder> implements
        View.OnClickListener{
    ArrayList<Estudiante> listaEstudiantes;
    private View.OnClickListener listener;

    public AdapterEstudiante(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    @Override
    public EstudianteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_estudiante,null,false);
        view.setOnClickListener(this);
        return new EstudianteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterEstudiante.EstudianteViewHolder holder, int position) {
        holder.tvNombreA.setText(listaEstudiantes.get(position).getNombre()+" "+listaEstudiantes.get(position).getApellidos());
        holder.tvCedulaA.setText(String.valueOf(listaEstudiantes.get(position).getId()));
        holder.tvEdadA.setText(String.valueOf(listaEstudiantes.get(position).getEdad()));

        holder.itemView.setTag(listaEstudiantes.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return listaEstudiantes.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class EstudianteViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombreA, tvCedulaA,tvEdadA;

        public EstudianteViewHolder(View itemView) {
            super(itemView);
            tvNombreA= (TextView) itemView.findViewById(R.id.tvNombreA);
            tvCedulaA= (TextView) itemView.findViewById(R.id.tvCedulaA);
            tvEdadA= (TextView) itemView.findViewById(R.id.tvEdadA);
        }
    }
    //Cambia los Alumno segun la busqueda y notifica al adaptador del cambio
    public void filterList(ArrayList<Estudiante> listaEstudiantesBusqueda){
        listaEstudiantes=listaEstudiantesBusqueda;
        notifyDataSetChanged();
    }
}
