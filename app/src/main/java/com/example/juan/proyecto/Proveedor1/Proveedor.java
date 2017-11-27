package com.example.juan.proyecto.Proveedor1;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by juan on 27/10/17.
 */

public class Proveedor extends ContentProvider {

    final String DB_NAME = "DB_ESCUELA.db";
    final int DB_VERSION = 6;
    SqlHelper dbHelper;
    //Context context;

    final static int ALUMNOS_ALL_REGS = 1;
    final static int ALUMNOS_ONE_REG = 2;
    final static int PROFESORES_ALL_REGS = 3;
    final static int PROFESORES_ONE_REG = 4;

    //private static final String ALUMNOS_TABLE_NAME = Contrato.Alumnos.NOMBRE_TABLA;
    //private static final String PROFESORES_TABLE_NAME = Contrato.Profesores.NOMBRE_TABLA;

    private static final UriMatcher mUriMatcher =  new UriMatcher(0);
    //private static final SparseArray<String> sMimeTypes;
    static{
        mUriMatcher.addURI(
                Contrato.AUTORIDAD,
                Contrato.Alumnos.NOMBRE_TABLA,
                ALUMNOS_ALL_REGS
        );
        mUriMatcher.addURI(
                Contrato.AUTORIDAD,
                Contrato.Alumnos.NOMBRE_TABLA + "/#",
                ALUMNOS_ONE_REG
        );
        mUriMatcher.addURI(
                Contrato.AUTORIDAD,
                Contrato.Profesores.NOMBRE_TABLA,
                PROFESORES_ALL_REGS
        );
        mUriMatcher.addURI(
                Contrato.AUTORIDAD,
                Contrato.Profesores.NOMBRE_TABLA + "/#",
                PROFESORES_ONE_REG
        );
/***
        sMimeTypes = new SparseArray<String>();

        sMimeTypes.put(
                ALUMNOS_ALL_REGS,
                "vnd.android.cursor.dir/vnd." +
                        Contrato.AUTORIDAD + "." + Contrato.Alumnos.NOMBRE_TABLA);
        sMimeTypes.put(
                ALUMNOS_ONE_REG,
                "vnd.android.cursor.item/vnd."+
                        Contrato.AUTORIDAD + "." + Contrato.Alumnos.NOMBRE_TABLA);     *****/
    }

    public Proveedor() {}

    @Override
    public boolean onCreate() {
        dbHelper = new SqlHelper(getContext(), DB_NAME, null, DB_VERSION);
        //dbHelper.getWritableDatabase();
        //if (dbHelper != null) return true;
        //else return false;
        Log.e("HELPER: ", "dbHelper es:   "+ dbHelper.toString());
        //return (dbHelper == null ? false : true);
        return dbHelper != null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = mUriMatcher.match(uri);

        switch (match)
        {
            case ALUMNOS_ALL_REGS:
                return "vnd.android.cursor.dir/vnd."+Contrato.AUTORIDAD+"."+ Contrato.Alumnos.NOMBRE_TABLA;
            case ALUMNOS_ONE_REG:
                return "vnd.android.cursor.item/vnd."+Contrato.AUTORIDAD+"."+ Contrato.Alumnos.NOMBRE_TABLA;
            case PROFESORES_ALL_REGS:
                return "vnd.android.cursor.dir/vnd."+Contrato.AUTORIDAD+"."+ Contrato.Profesores.NOMBRE_TABLA;
            case PROFESORES_ONE_REG:
                return "vnd.android.cursor.item/vnd."+Contrato.AUTORIDAD+"."+ Contrato.Profesores.NOMBRE_TABLA;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //qb.setTables();
        //qb.setTables(String.valueOf(mUriMatcher.match(uri)));

        //Toast.makeText(getContext(), "URI: "+mUriMatcher.toString(), Toast.LENGTH_SHORT).show();

        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        String query = null;

        switch (mUriMatcher.match(uri)){
            case ALUMNOS_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Alumnos._ID + " = "
                        + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                qb.setTables(Contrato.Alumnos._ID);
                break;
            case ALUMNOS_ALL_REGS:
                if(TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.Alumnos._ID + " ASC";
                qb.setTables(Contrato.Alumnos.NOMBRE_TABLA);
                break;
            case PROFESORES_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Profesores._ID + " = "
                        + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                qb.setTables(Contrato.Profesores._ID);
                break;
            case PROFESORES_ALL_REGS:
                if(TextUtils.isEmpty(sortOrder)) sortOrder = Contrato.Profesores._ID + " ASC";
                qb.setTables(Contrato.Profesores.NOMBRE_TABLA);
                break;
        }
        Cursor c;
        c = qb.query(db, projection, selection, selectionArgs,null,null, sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        String table = "";
        switch (mUriMatcher.match(uri)) {
            case ALUMNOS_ALL_REGS:
                table = Contrato.Alumnos.NOMBRE_TABLA;
                break;
            case PROFESORES_ALL_REGS:
                table = Contrato.Profesores.NOMBRE_TABLA;
                break;
            default:
                throw new IllegalArgumentException("URI desconocida : " + uri);
        }

        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        //Ejecución de la inserción de datos en un registro de la tabla
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long rowId = db.insert(table, null, values);

        if (rowId > 0){
            Uri rowUri = ContentUris.appendId(uri.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into "+uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String table = "";
        switch (mUriMatcher.match(uri)){
            case ALUMNOS_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Alumnos._ID + " = " + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                table = Contrato.Alumnos.NOMBRE_TABLA;
            case ALUMNOS_ALL_REGS:
                table = Contrato.Alumnos.NOMBRE_TABLA;
                break;
            case PROFESORES_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Profesores._ID + " = " + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                table = Contrato.Profesores.NOMBRE_TABLA;
            case PROFESORES_ALL_REGS:
                table = Contrato.Profesores.NOMBRE_TABLA;
                break;
        }

        //Ejecución del borrado de en un registro de la tabla o todos los registros de ésta
        int rows = db.delete(table, selection, selectionArgs); // selection equivale al where en SQL
        if(rows > 0){
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into "+uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db  = dbHelper.getReadableDatabase();
        String table = "";

        switch (mUriMatcher.match(uri)){
            case ALUMNOS_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Alumnos._ID + " = " + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                table = Contrato.Alumnos.NOMBRE_TABLA;
            case ALUMNOS_ALL_REGS:
                table = Contrato.Alumnos.NOMBRE_TABLA;
                break;
            case PROFESORES_ONE_REG:
                if (null == selection) selection ="";
                selection += Contrato.Profesores._ID + " = " + uri.getLastPathSegment(); // último segmento de la uri que es el que indica un registro
                table = Contrato.Profesores.NOMBRE_TABLA;
            case PROFESORES_ALL_REGS:
                table = Contrato.Profesores.NOMBRE_TABLA;
                break;
        }

        //Ejecución del borrado de en un registro de la tabla o todos los registros de ésta
        int rows = db.update(table, values, selection, selectionArgs); // selection equivale al where en SQL
        if(rows > 0){
            getContext().getContentResolver().notifyChange(uri, null);
            return rows;
        }
        throw new SQLException("Failed to delete row into "+uri);
    }

    /*
    class AlumnosProveedor extends Proveedor {

        public String getTableName() {
            return Contrato.Alumnos.NOMBRE_TABLA;
        }
    }
    class ProfesoresProveedor extends Proveedor {

        public String getTableName() {
            return Contrato.Profesores.NOMBRE_TABLA;
        }
    }
    */

}