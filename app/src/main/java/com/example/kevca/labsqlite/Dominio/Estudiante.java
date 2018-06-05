package com.example.kevca.labsqlite.Dominio;

import java.util.ArrayList;

/**
 * Created by kevca on 6/1/2018.
 */

public class Estudiante {
    private int Id;
    private String Nombre;
    private String Apellidos;
    private int Edad;
    private ArrayList<Cursos> cursosArrayList;

    public Estudiante(int id, String nombre, String apellidos, int edad) {
        Id = id;
        Nombre = nombre;
        Apellidos = apellidos;
        Edad = edad;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public int getEdad() {
        return Edad;
    }

    public void setEdad(int edad) {
        Edad = edad;
    }

    public ArrayList<Cursos> getCursosArrayList() {
        return cursosArrayList;
    }

    public void setCursosArrayList(ArrayList<Cursos> cursosArrayList) {
        this.cursosArrayList = cursosArrayList;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "Id=" + Id +
                ", Nombre='" + Nombre + '\'' +
                ", Apellidos='" + Apellidos + '\'' +
                ", Edad=" + Edad +
                ", Cantidad de Cursos=" + cursosArrayList.size() +
                '}';
    }
}
