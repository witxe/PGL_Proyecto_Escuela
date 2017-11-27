package com.example.juan.proyecto.Proveedor1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by juan on 27/10/17.
 */

public class SqlHelper extends SQLiteOpenHelper {

    public SqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys = 'ON';"); // Activa la integridad referencial
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " +
                Contrato.Alumnos.NOMBRE_TABLA +
                "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, " +
                Contrato.Alumnos.NOMBRE+ " TEXT, " +
                Contrato.Alumnos.EMAIL+ " TEXT, " +
                Contrato.Alumnos.TELEFONO+ " TEXT, " +
                Contrato.Alumnos.EDAD+ " TEXT, " +
                Contrato.Alumnos.SEXO + " TEXT, " +
                Contrato.Alumnos.FOTO + " TEXT)" );

        db.execSQL("CREATE TABLE " +
                Contrato.Profesores.NOMBRE_TABLA +
                "( _id INTEGER PRIMARY KEY ON CONFLICT ROLLBACK AUTOINCREMENT, " +
                Contrato.Profesores.NOMBRE+ " TEXT, " +
                Contrato.Profesores.EMAIL+ " TEXT, " +
                Contrato.Profesores.TELEFONO+ " TEXT, " +
                Contrato.Profesores.EDAD+ " TEXT, " +
                Contrato.Profesores.SEXO + " TEXT, " +
                Contrato.Profesores.FOTO + " TEXT)" );
        inicializar(db);
    }

    private void inicializar(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " +
                Contrato.Alumnos.NOMBRE_TABLA + "( " +
                Contrato.Alumnos.NOMBRE + "," +
                Contrato.Alumnos.EMAIL + "," +
                Contrato.Alumnos.TELEFONO + "," +
                Contrato.Alumnos.EDAD + "," +
                Contrato.Alumnos.SEXO + "," +
                Contrato.Alumnos.FOTO +
                ") VALUES ('Juan', 'j@j.j', '111111111', '11', 'masculino', 'foto_juan.png' )," +
                         "('Alba', 'a@a.a', '222222222', '22', 'femenino', 'foto_alba.png' )," +
                         "('Cameron','c@c.c', '333333333', '33', 'otro', 'foto_cameron.png')"
        );

        db.execSQL("INSERT INTO " +
                Contrato.Profesores.NOMBRE_TABLA + "( " +
                Contrato.Profesores.NOMBRE + "," +
                Contrato.Profesores.EMAIL + "," +
                Contrato.Profesores.TELEFONO + "," +
                Contrato.Profesores.EDAD + "," +
                Contrato.Profesores.SEXO + "," +
                Contrato.Profesores.FOTO +
                ") VALUES ('John', 'jj@jj.jj', '111222333', '35', 'masculino', 'foto_john.png' )," +
                "('Caroline', 'aa@aa.aa', '222444666', '41', 'femenino', 'foto_caroline.png' )," +
                "('Mark','cc@cc.cc', '333555777', '51', 'masculino', 'foto_mark.png')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.Alumnos.NOMBRE_TABLA);
        db.execSQL("DROP TABLE IF EXISTS " + Contrato.Profesores.NOMBRE_TABLA);
        onCreate(db);
    }
}
