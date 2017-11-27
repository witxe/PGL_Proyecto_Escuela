package com.example.juan.proyecto.Proveedor1;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by juan on 27/10/17.
 */

public class Contrato {
    public final static String AUTORIDAD ="com.example.juan.proyecto3.Proveedor1.Proveedor";

    public static class Alumnos implements BaseColumns {

        private Alumnos(){} //Constructor vacío

        public final static String NOMBRE_TABLA = "Alumno";
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD + "/" + NOMBRE_TABLA);

        public final static String ID = "id";
        public final static String NOMBRE = "nombre";
        public final static String EMAIL = "email";
        public final static String TELEFONO = "telefono";
        public final static String EDAD = "edad";
        public final static String SEXO = "sexo";
        public final static String FOTO = "foto";

    }

    public static class Profesores implements BaseColumns {

        private Profesores() {} //Constructor vacío

        public final static String NOMBRE_TABLA = "Profesor";
        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTORIDAD + "/" + NOMBRE_TABLA);

        public final static String ID = "id";
        public final static String NOMBRE = "nombre";
        public final static String EMAIL = "email";
        public final static String TELEFONO = "telefono";
        public final static String EDAD = "edad";
        public final static String SEXO = "sexo";
        public final static String FOTO = "foto";
    }

}
