package com.example.juan.proyecto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Activity5Niveles extends AppCompatActivity {

    CheckBox chkStarters;
    CheckBox chkMovers;
    CheckBox chkFlyers;
    CheckBox chkKET;
    CheckBox chkPET;
    CheckBox chkFCE;
    CheckBox chkCAE;
    CheckBox chkCPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.juan.proyecto.R.layout.activity_5_niveles);

        chkStarters = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkStarters);
        chkMovers = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkMovers);
        chkFlyers = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkFlyers);
        chkKET = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkKET);
        chkPET = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkPET);
        chkFCE = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkFCE);
        chkCAE = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkCAE);
        chkCPE = (CheckBox) findViewById(com.example.juan.proyecto.R.id.chkCPE);

        chkKET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkKET.isChecked()) chkKET.setText(com.example.juan.proyecto.R.string.ket);
                else chkKET.setText(getString(com.example.juan.proyecto.R.string.k));
            }
        });
        chkPET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkPET.isChecked()) chkPET.setText(com.example.juan.proyecto.R.string.pet);
                else chkPET.setText(getString(com.example.juan.proyecto.R.string.p));
            }
        });
        chkFCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkFCE.isChecked()) chkFCE.setText(com.example.juan.proyecto.R.string.fce);
                else chkFCE.setText(getString(com.example.juan.proyecto.R.string.f));
            }
        });
        chkCAE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkCAE.isChecked()) chkCAE.setText(com.example.juan.proyecto.R.string.cae);
                else chkCAE.setText(getString(com.example.juan.proyecto.R.string.ca));
            }
        });
        chkCPE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chkCPE.isChecked()) chkCPE.setText(com.example.juan.proyecto.R.string.cpe);
                else chkCPE.setText(getString(com.example.juan.proyecto.R.string.cp));
            }
        });

    }
}
