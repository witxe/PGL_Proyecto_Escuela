package com.example.juan.proyecto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityDialog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        TextView tv = (TextView) findViewById(R.id.tv);
        //Button btnEditar = (Button) findViewById(R.id.btnEditar);
        //Button btnBorrar = (Button) findViewById(R.id.btnBorrar);

        Intent intent = getIntent();
        String val = intent.getStringExtra("detail");
        tv.setText(val);

        //borrarRegistro();
        //editarRegistro();
    }



}
