package com.example.kevca.labsqlite.Utilidades;

/**
 * Created by kevca on 6/4/2018.
 */

public class Utilidades {
    public static final String TABLA_ESTUDIANTES= "estudiantes";
    public static final String CAMPO_ID= "id";
    public static final String CAMPO_NOMBRE= "nombre";
    public static final String CAMPO_APPELLIDOS= "apellidos";
    public static final String CAMPO_EDAD= "edad";
    public static final String CREAR_TABLA_ESTUDIANTE="CREATE TABLE "+TABLA_ESTUDIANTES+" ("+CAMPO_ID+" integer PRIMARY KEY, "+CAMPO_NOMBRE+" text, "+CAMPO_APPELLIDOS+" text, "+CAMPO_EDAD+" integer)";

}
