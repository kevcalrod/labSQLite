package com.example.kevca.labsqlite.Dominio;

/**
 * Created by kevca on 6/1/2018.
 */

public class Cursos {
    private int Id;
    private  String Descripción;
    private int Créditos;

    public Cursos(int id, String descripción, int créditos) {
        Id = id;
        Descripción = descripción;
        Créditos = créditos;
    }

    public Cursos(){

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getDescripción() {
        return Descripción;
    }

    public void setDescripción(String descripción) {
        Descripción = descripción;
    }

    public int getCréditos() {
        return Créditos;
    }

    public void setCréditos(int créditos) {
        Créditos = créditos;
    }

    @Override
    public String toString() {
        return "Cursos{" +
                "Id=" + Id +
                ", Descripción='" + Descripción + '\'' +
                ", Créditos=" + Créditos +
                '}';
    }
}
