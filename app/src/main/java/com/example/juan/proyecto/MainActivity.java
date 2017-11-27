package com.example.juan.proyecto;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout inputUser;
    private TextInputLayout inputPassword;
    private EditText editTxtUser;
    private EditText editTxtPasswd;
    private Button btnComenzar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.juan.proyecto.R.layout.activity_main);

        inputUser = (TextInputLayout) findViewById(com.example.juan.proyecto.R.id.inputUser);
        inputPassword = (TextInputLayout) findViewById(com.example.juan.proyecto.R.id.inputPassword);
        editTxtUser = (EditText) findViewById(com.example.juan.proyecto.R.id.editTxtUser);
        editTxtPasswd = (EditText) findViewById(com.example.juan.proyecto.R.id.editTxtPassword);
        btnComenzar = (Button) findViewById(com.example.juan.proyecto.R.id.btnComenzar);

        inputUser.setErrorEnabled(true);
        inputPassword.setErrorEnabled(true);

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(MainActivity.this, MenuDrawerActivity.class);
                validar();
            }

        });
    }

    private void validar() {

            /************************** Validar nombre y contraseña *****************************
             Se puede asignar los errores al TextInputLayout en vez de al EditText */
            String user = editTxtUser.getText().toString();
            String password = editTxtPasswd.getText().toString();
            inputUser.setError(null);
            inputPassword.setError(null);

            if (user.isEmpty()) {
                inputUser.setError(getString(com.example.juan.proyecto.R.string.error_obligatorio));
                editTxtUser.requestFocus();
                return;
            } else if (password.isEmpty()) {
                inputPassword.setError(getString(com.example.juan.proyecto.R.string.error_obligatorio));
                editTxtPasswd.requestFocus();
                return;
            } else {

                if(!password(editTxtPasswd.getText().toString())) {
                    inputPassword.setError(getString(com.example.juan.proyecto.R.string.error_password));
                    editTxtPasswd.requestFocus();
                } else {
                    inputPassword.setError(null);
                    //Toast.makeText(this, "Password O.K.", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        }


    /** Comprobar que la contraseña incluya al menos una mayúscula y un número. */
    boolean password (String p){

        boolean digito = false, letra = false;

        for (char c : p.toCharArray()){

            if(Character.isDigit(c)) {
                digito = true;
            }
            if(Character.isUpperCase(c)) {
                letra = true;
            }
        }

        if (!digito || !letra) return false;
        else return true;
    }





/*
    private void validar() {
        // Otra forma de hacerlo
        editTxtUser.setError(null);
        editTxtPasswd.setError(null);
        if (TextUtils.isEmpty(editTxtUser.getText().toString())) {
            editTxtUser.setError(getString(R.string.error));
            editTxtUser.requestFocus();
            return;
        } else if (TextUtils.isEmpty(editTxtPasswd.getText().toString())) {
            editTxtPasswd.setError(getString(R.string.error));
            editTxtPasswd.requestFocus();
            return;



        //************************* Validar nombre y contraseña *****************************
        // Opté por asignar los errores al TextInputLayout. En mi opinión mejora la visualización del mensaje de error.
        // En otras actividades lo hago desde el propio EditText para practicar ambos.
        String user = editTxtUser.getText().toString();
        String password = editTxtPasswd.getText().toString();
        inputUser.setError(null);
        inputPassword.setError(null);

        if (user.isEmpty()) {
            inputUser.setError(getString(R.string.error));
            editTxtUser.requestFocus();
            return;
        } else if (password.isEmpty()) {
            inputPassword.setError(getString(R.string.error));
            editTxtPasswd.requestFocus();
            return;
        } else {
            startActivity(intent);
        }

    }*/


}
