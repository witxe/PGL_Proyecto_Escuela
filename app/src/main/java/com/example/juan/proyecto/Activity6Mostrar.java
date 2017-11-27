package com.example.juan.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.juan.proyecto.pojos.Alumno;

public class Activity6Mostrar extends AppCompatActivity {

    TextView mostrarNombre;
    TextView mostrarSexo;
    TextView mostrarEdad;
    TextView mostrarEmail;
    TextView mostrarTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.juan.proyecto.R.layout.activity_6_mostrar);

        mostrarNombre = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarNombre);
        mostrarSexo = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarSexo);
        mostrarEdad = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarEdad);
        mostrarEmail = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarEmail);
        mostrarTelefono = (TextView) findViewById(com.example.juan.proyecto.R.id.mostrarTelefono);

        Intent i = this.getIntent();
        Alumno alumno = i.getParcelableExtra(String.valueOf("alumno"));

        mostrarNombre.setText("Nombre: "+alumno.getNombre());
        mostrarSexo.setText("Sexo: "+alumno.getSexo());
        mostrarEdad.setText("Edad: "+String.valueOf(alumno.getEdad()));
        mostrarEmail.setText("Email: "+alumno.getEmail());
        mostrarTelefono.setText("Teléfono: "+alumno.getTelefono());

    }
}
