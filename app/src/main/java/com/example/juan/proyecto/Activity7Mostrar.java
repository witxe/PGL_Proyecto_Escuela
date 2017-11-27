package com.example.juan.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.juan.proyecto.pojos.Profesor;

public class Activity7Mostrar extends AppCompatActivity {

    TextView mostrarNombre;
    TextView mostrarSexo;
    TextView mostrarEdad;
    TextView mostrarEmail;
    TextView mostrarTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.juan.proyecto.R.layout.activity_7_mostrar);

        mostrarNombre = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarNombre);
        mostrarSexo = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarSexo);
        mostrarEdad = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarEdad);
        mostrarEmail = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarEmail);
        mostrarTelefono = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarTelefono);

        Intent i = this.getIntent();

        Profesor profesor = i.getParcelableExtra(String.valueOf("profesor"));

        mostrarNombre.setText("Nombre: "+profesor.getNombre());
        mostrarSexo.setText("Sexo: "+profesor.getSexo());
        mostrarEdad.setText("Edad: "+String.valueOf(profesor.getEdad()));
        mostrarEmail.setText("Email: "+profesor.getEmail());
        mostrarTelefono.setText("Teléfono: "+profesor.getTelefono());

    }
}
