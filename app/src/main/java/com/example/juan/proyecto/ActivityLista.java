package com.example.juan.proyecto;

import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.juan.proyecto.Proveedor1.Contrato;
import com.example.juan.proyecto.Proveedor1.Proveedor;
import com.example.juan.proyecto.pojos.Alumno;

import java.util.ArrayList;

import static java.lang.String.valueOf;

public class  ActivityLista extends AppCompatActivity {

    final Context context = this;
    Alumno alumno;

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

                Intent intent = new Intent(context, Activity3Formulario.class);

                if (alumno != null) {
                    intent.putExtra("LLAMADA", 2);  // 1: insertar 2: modificar 3: borrar
                    intent.putExtra("alumno", alumno);
                    //Toast.makeText(this, "Id del Alumno: "+alumno.getId()+"  Email: "+alumno.getEmail(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    return true;
                } else { return false; }


            case R.id.action_borrar:

                if(alumno != null) {
                    final long alarmId = alumno.getId();
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
                                    Uri uri = Contrato.Alumnos.CONTENT_URI;
                                    String[] projection = null;
                                    String selection = Contrato.Alumnos._ID + " =? ";
                                    String[] selectionArgs = new String[]{String.valueOf(alumno.getId())};

                                    Log.e("------- Borrará a ",alumno.getNombre());
                                    ContentResolver resolver = getContentResolver();
                                    resolver.delete(uri, selection, selectionArgs);

                                    obtenerListado(); // Refresca listado en el método al que llama - NO ACTUALIZA LA VISTA

                                    /* Carga la misma actividad para refrescar la vista */
                                    //Intent i = new Intent(ActivityLista.this, ActivityLista.class);
                                    Intent i = new Intent(context, ActivityLista.class);
                                    startActivity(i);
                                    //ActivityLista.finish();
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

                //Snackbar.make(view, "Añadir nuevo alumno", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent intent = new Intent(getApplicationContext(), Activity3Formulario.class);
                intent.putExtra("LLAMADA", 1);  // 1: insertar 2: modificar 3: borrar
                intent.putExtra("item", "alumno");
                startActivity(intent);
            }
        });
        /******************************************************************************************/



        final Cursor cursor = obtenerListado();
        //cursor.moveToFirst();
        final ListView listview = findViewById(R.id.viewLista);
        ListCursorAdapter listCursorAdapter = new ListCursorAdapter(this, cursor);
        listview.setAdapter(listCursorAdapter);

        /* No actualiza la lista
        listCursorAdapter.notifyDataSetChanged();
        list.remove(posicion);*/

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int _ID = cursor.getInt(cursor.getColumnIndex(Contrato.Alumnos._ID));
                alumno = getAlumno(_ID);
            }
        });
    }

    private Cursor obtenerListado() {
        final ArrayList<String> listaDatos = new ArrayList<>();
        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Alumnos.CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder= null;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        while(cursor.moveToNext()){
            String id  = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos._ID));
            String nom = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.NOMBRE));
            String eml = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.EMAIL));
            String tel = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.TELEFONO));
            String eda = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.EDAD));
            String sex = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.SEXO));
            String fot = cursor.getString(cursor.getColumnIndex(Contrato.Alumnos.FOTO));
            Log.i("app: ", "Nombre "+nom+"  "+"Email: "+eml+"  "+"Tel: "+tel+"  "+"Edad: "+eda+"  "+"Sexo: "+sex);
            listaDatos.add("Id: "+id+"   Nombre: "+nom+"   Edad: "+eda+"\n"+"email: "+eml+"\n"+"tel: "+tel+"\n");
        }
        return cursor;
    }


        /** ///////////////////////////////     Array Adapter   ////////////////////////////////////

        final ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDatos);

        final ListView lista = (ListView) findViewById(R.id.viewLista);
        lista.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, listaDatos));

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                cursor.moveToPosition(position);
                int _ID = cursor.getInt(cursor.getColumnIndex(Contrato.Alumnos._ID));
                Toast.makeText(contexto, "Seleccionado el registro con ID:  "+String.valueOf(_ID), Toast.LENGTH_SHORT).show();
                Toast.makeText(contexto, "Alumno\n"+itemsAdapter.getItem(position), Toast.LENGTH_SHORT).show();

                final String s = lista.getItemAtPosition(position).toString();
                Toast.makeText(ActivityLista.this,"Alumno: "+s, Toast.LENGTH_LONG).show();
                Toast.makeText(ActivityLista.this, "Posicion: "+position+"   Id: "+id, Toast.LENGTH_SHORT).show();
                Alumno alumno;
                alumno = getAlumno(_ID);
                buscar llaves con barras
        });
    }   */



    Alumno getAlumno(int id) {
        //SQLiteDatabase db = this.getReadableDatabase();
        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Alumnos.CONTENT_URI;
        String[] projection = new String[]{
                Contrato.Alumnos._ID,
                Contrato.Alumnos.NOMBRE,
                Contrato.Alumnos.EMAIL,
                Contrato.Alumnos.TELEFONO,
                Contrato.Alumnos.EDAD,
                Contrato.Alumnos.SEXO,
                Contrato.Alumnos.FOTO};
        String selection = Contrato.Alumnos._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        String sortOrder = null;

        ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) cursor.moveToFirst();

        alumno = new Alumno(
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getString(5),
                ""
                );
        alumno.setId(id);
        cursor.close();
        //Toast.makeText(this, "Id del Alumno: "+alumno.getId()+"  Email: "+alumno.getEmail(), Toast.LENGTH_SHORT).show();

        return alumno;
    }

/*
    public void editar(View view) {
        Toast.makeText(this, "Editar pulsado", Toast.LENGTH_SHORT).show();

        //Hacer intent hacia ActivityAlumnos3

        //Proveedor pc = new Proveedor();
        //Uri uri = Contrato.Alumnos.CONTENT_URI;
        //String[] projection = null;
        //String selection = Contrato.Alumnos._ID + "=?";
        //String[] selectionArgs = new String[]{String.valueOf(id)};

        //ContentResolver resolver = getContentResolver();
        //Cursor cursor = resolver.update(uri, projection, selection, selectionArgs, sortOrder);

    }

    public void borrar(View view) {
        Toast.makeText(this, "Borrar pulsado", Toast.LENGTH_SHORT).show();
        //Hacer intent hacia ActivityAlumnos3

        //Proveedor pa = new Proveedor();
        //Uri uri = Contrato.Alumnos.CONTENT_URI;
        //String[] projection = null;
        //String selection = Contrato.Alumnos._ID + " =? ";
        //String[] selectionArgs = new String[]{String.valueOf(registro)};

        //ContentResolver resolver = getContentResolver();
        //resolver.delete(uri, selection, selectionArgs);
        //resolver.notifyChange(uri,null, 0);
    }

    public long getRowId() {

        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Alumnos.CONTENT_URI;

        String[] columns = new String[] {
                Contrato.Alumnos._ID,
                Contrato.Alumnos.NOMBRE,
                Contrato.Alumnos.EMAIL,
                Contrato.Alumnos.TELEFONO,
                Contrato.Alumnos.EDAD,
                Contrato.Alumnos.SEXO };

        ContentResolver resolver = getContentResolver();
        Cursor c = resolver.query(uri, columns, null, null, null, null);
        c.moveToFirst();
        int iRow = c.getColumnIndex(Contrato.Alumnos._ID);
        String ROWID = c.getString(iRow);
        long value = Long.parseLong(ROWID);
        c.close();
        return value;
    }*/

}
