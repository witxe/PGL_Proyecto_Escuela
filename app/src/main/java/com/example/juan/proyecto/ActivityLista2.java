package com.example.juan.proyecto;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.juan.proyecto.Proveedor1.Contrato;
import com.example.juan.proyecto.Proveedor1.Proveedor;
import com.example.juan.proyecto.pojos.Profesor;

import java.util.ArrayList;

public class ActivityLista2 extends AppCompatActivity {

    final Context context = this;
    Profesor profesor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_lista, menu);
        return true;
    }
    

    /************************************** UPDATE AND DELETE *************************************/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_editar:

                Intent intent = new Intent(context, Activity4Formulario.class);

                if (profesor != null) {
                    intent.putExtra("LLAMADA", 2);  // 1: insertar 2: modificar 3: borrar
                    intent.putExtra("profesor", profesor);
                    //Toast.makeText(this, "Id del Profesor: "+profesor.getId()+"  Email: "+profesor.getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return true;
                } else { return false; }


            case R.id.action_borrar:

                if(profesor != null) {
                    final long alarmId = profesor.getId();
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("CONFIRMA")
                            .setTitle("Borrar registro?")
                            .setCancelable(true)
                            .setNegativeButton("Cancel", null)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Proveedor pa = new Proveedor();
                                    Uri uri = Contrato.Profesores.CONTENT_URI;
                                    String[] projection = null;
                                    String selection = Contrato.Profesores._ID + " =? ";
                                    String[] selectionArgs = new String[]{String.valueOf(profesor.getId())};

                                    Log.e("------- Borrará a ",profesor.getNombre());
                                    ContentResolver resolver = getContentResolver();
                                    resolver.delete(uri, selection, selectionArgs);

                                    obtenerListado(); // Refresca listado en el método al que llama - NO ACTUALIZA LA VISTA

                                    /* Carga la misma actividad para refrescar la vista */
                                    //Intent i = new Intent(ActivityLista2.this, ActivityLista2.class);
                                    Intent i = new Intent(context, ActivityLista2.class);
                                    startActivity(i);
                                    //ActivityLista2.finish();
                                }
                                }).show();
                    return true;
                } else {
                    // No hacer nada.
                    return false;
                }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**********************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context contexto;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        contexto = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarLista);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        /**************************************** INSERT ******************************************/
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabLista);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Activity4Formulario.class);
                intent.putExtra("LLAMADA", 1);  // 1: insertar 2: modificar 3: borrar
                startActivity(intent);
            }
        });
        /******************************************************************************************/



        final Cursor cursor = obtenerListado();
        //cursor.moveToFirst();
        final ListView listview = findViewById(R.id.viewLista);
        //ListCursorAdapter listCursorAdapter = new ListCursorAdapter(this, cursor);
        ListCursorAdapter2 listCursorAdapter = new ListCursorAdapter2(this, cursor);
        listview.setAdapter(listCursorAdapter);

        /* No actualiza la lista
        listCursorAdapter.notifyDataSetChanged();
        list.remove(posicion);*/

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int _ID = cursor.getInt(cursor.getColumnIndex(Contrato.Profesores._ID));
                profesor = getProfesor(_ID);
            }
        });
    }

    private Cursor obtenerListado() {
        final ArrayList<String> listaDatos = new ArrayList<>();
        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Profesores.CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder= null;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        while(cursor.moveToNext()){
            String id  = cursor.getString(cursor.getColumnIndex(Contrato.Profesores._ID));
            String nom = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.NOMBRE));
            String eml = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.EMAIL));
            String tel = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.TELEFONO));
            String eda = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.EDAD));
            String sex = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.SEXO));
            String fot = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.FOTO));
            Log.i("app: ", "Nombre "+nom+"  "+"Email: "+eml+"  "+"Tel: "+tel+"  "+"Edad: "+eda+"  "+"Sexo: "+sex);
            listaDatos.add("Id: "+id+"   Nombre: "+nom+"   Edad: "+eda+"\n"+"email: "+eml+"\n"+"tel: "+tel+"\n");
        }
        return cursor;
    }

    Profesor getProfesor(int id) {
        //SQLiteDatabase db = this.getReadableDatabase();
        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Profesores.CONTENT_URI;
        String[] projection = new String[]{
                Contrato.Profesores._ID,
                Contrato.Profesores.NOMBRE,
                Contrato.Profesores.EMAIL,
                Contrato.Profesores.TELEFONO,
                Contrato.Profesores.EDAD,
                Contrato.Profesores.SEXO,
                Contrato.Profesores.FOTO};
        String selection = Contrato.Profesores._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = null;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) cursor.moveToFirst();

        profesor = new Profesor(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getString(5),
                cursor.getString(6)
                );
        profesor.setId(id);
        cursor.close();
        //Toast.makeText(this, "Id del Profesor: "+profesor.getId()+"  Email: "+profesor.getEmail(), Toast.LENGTH_SHORT).show();

        return profesor;
    }

}
