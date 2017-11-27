package com.example.juan.proyecto;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juan.proyecto.Proveedor1.Contrato;
import com.example.juan.proyecto.Proveedor1.Proveedor;
import com.example.juan.proyecto.constantes.Utilidades;
import com.example.juan.proyecto.pojos.Profesor;

import java.io.IOException;

public class Activity4Formulario extends AppCompatActivity {

    private static final int PETICION_FOTO = 1;
    private static final int PETICION_GALERIA = 2;
    private static ImageView ivFoto;
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;
    private EditText txtEdad;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private ImageView imgSexo;
    private ImageView iv_detalle;
    private int llamada;
    Intent intent;
    int idRadio = -1;
    Profesor profesor;
    Context context = this;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_formulario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_guardar:
                intent = new Intent(Activity4Formulario.this, ActivityLista2.class);
                validar();
                break;
            case R.id.action_foto:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PETICION_FOTO);
                break;
            case R.id.action_galeria:
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i, PETICION_GALERIA);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case PETICION_FOTO:
                if (resultCode == RESULT_OK) {
                    Bitmap foto = (Bitmap) data.getExtras().get("data");
                    ivFoto.setImageBitmap(foto);
                    try {
                        Utilidades.storeImage(foto, this, "image.png");
                    } catch (IOException e) {
                        Toast.makeText(context, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // El usuario hizo click en cancelar
                }
                break;
            case PETICION_GALERIA:
                if (resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    ivFoto.setImageURI(uri);
                } else {
                    // El usuario hizo click en cancelar
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFormulario);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupSexo);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        imgSexo = (ImageView) findViewById(R.id.imgSexo);
        iv_detalle = (ImageView) findViewById(R.id.iv_detalle);
        ivFoto = (ImageView) findViewById(R.id.iv_foto);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                idRadio = radioGroup.getCheckedRadioButtonId();
                radioButton = (RadioButton) findViewById(idRadio);

                switch (checkedId) {
                    case R.id.radHombre:
                        imgSexo.setImageResource(R.mipmap.ic_male);
                        break;
                    case R.id.radMujer:
                        imgSexo.setImageResource(R.mipmap.ic_female);
                        break;
                    case R.id.radOtro:
                        imgSexo.setImageResource(R.mipmap.ic_transgender);
                        break;
                }
            }
        });

        /**  ////////////////////////////////// INSERT UPDATE ////////////////////////////////////*/
        Intent i = getIntent();
        llamada = i.getIntExtra("LLAMADA", 0); // 1: insertar 2: modificar 3: borrar


        switch(llamada){
            case 1:
                //txtNombre.setSelected(false);
                break;
            case 2:
                profesor = i.getParcelableExtra("profesor");
                Toast.makeText(context, "Profesor "+profesor.getNombre(), Toast.LENGTH_SHORT).show();
                if (profesor != null) {
                    // Encabezado con el ID del item seleccionado
                    //ivFoto.setImageResource(R.drawable. la foto);
                    iv_detalle.setImageResource(R.drawable.ic_teacher);
                    TextView label_id = findViewById(R.id.label_id);
                    label_id.setText("ID:  ");
                    label_id.setTextSize(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM, 20);
                    TextView item_id = findViewById(R.id.item_id);
                    item_id.setText(String.valueOf(profesor.getId()));
                    item_id.setTextSize(TextView.AUTO_SIZE_TEXT_TYPE_UNIFORM, 20);

                    txtNombre.setText(profesor.getNombre());
                    txtEdad.setText(String.valueOf(profesor.getEdad()));
                    txtEmail.setText(profesor.getEmail());
                    txtTelefono.setText(profesor.getTelefono());
                    String sexo = profesor.getSexo();
                    switch(profesor.getSexo()){
                        case "masculino":
                            radioGroup.check(R.id.radHombre);
                            //imgSexo.setImageAlpha(R.mipmap.ic_male);
                            break;
                        case "femenino":
                            radioGroup.check(R.id.radMujer);
                            //imgSexo.setImageAlpha(R.mipmap.ic_female);
                            break;
                        case "otro":
                            radioGroup.check(R.id.radOtro);
                            //imgSexo.setImageAlpha(R.mipmap.ic_transgender);
                        default:
                            break;
                    }
                }
                break;
            case 3:
                //Toast.makeText(context, "Llamada borrar", Toast.LENGTH_SHORT).show();
                break;
            default:
                //Toast.makeText(context, "Llamada desconocida", Toast.LENGTH_SHORT).show();
                break;
        }
        /**  /////////////////////////////////////////////////////////////////////////////////////*/

    }


    /*********************************** Validar inputs *******************************/
    void validar(){

        txtNombre.setError(null);
        txtEmail.setError(null);
        txtTelefono.setError(null);
        txtEdad.setError(null);

        String nombre = txtNombre.getText().toString();
        String edadS = txtEdad.getText().toString();
        String email = txtEmail.getText().toString();
        String telefono = txtTelefono.getText().toString();

        if(TextUtils.isEmpty(nombre)) {
            txtNombre.setError(getString(R.string.error_obligatorio));
            txtNombre.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(email)) {
            txtEmail.setError(getString(R.string.error_obligatorio));
            txtEmail.requestFocus();
            return;
        }
        if (!isValidEmail(email)){
            txtEmail.setError(getString(R.string.error_email));
            txtEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(telefono)) {
            txtTelefono.setError(getString(R.string.error_obligatorio));
            txtTelefono.requestFocus();
            return;
        }
        if (txtTelefono.length() != 9) {
            txtTelefono.setError(getString(R.string.error_telefono));
            txtTelefono.requestFocus();
            return;
        }
        if (idRadio == -1) {
            Toast.makeText(getApplicationContext(),getString(R.string.error_sexo), Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(edadS)) {
            txtEdad.setError(getString(R.string.error_obligatorio));
            txtEdad.requestFocus();
            return;
        }
        int edad = Integer.parseInt(edadS);
        if (edad<5 || edad>99) {
            txtEdad.setError(getString(R.string.error_edad));
            txtEdad.requestFocus();
            return;
        }

        switch (llamada){
            case 1:
                insertaItem();
                break;
            case 2:
                modificaItem();
                break;
            default:
                break;
        }

    }
    /**********************************************************************************************/


    private boolean isValidEmail(String target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    private void insertaItem() {
        profesor = new Profesor(
                txtNombre.getText().toString(),
                txtEmail.getText().toString(),
                txtTelefono.getText().toString(),
                Integer.parseInt(txtEdad.getText().toString()),
                radioButton.getText().toString(),
                "");

        intent.putExtra("profesor", profesor);
        Uri uri = getContentResolver().insert(Contrato.Profesores.CONTENT_URI, toContentValues());
        startActivity(intent);
    }


    private void modificaItem() {
        Proveedor pc = new Proveedor();
        Uri uri = Contrato.Profesores.CONTENT_URI;
        ContentValues values = new ContentValues();
            values.put("NOMBRE", String.valueOf(txtNombre.getText()));
            values.put("EMAIL", String.valueOf(txtEmail.getText()));
            values.put("TELEFONO", String.valueOf(txtTelefono.getText()));
            values.put("EDAD", String.valueOf(txtEdad.getText()));
            values.put("SEXO", radioButton.getText().toString());

        String selection = Contrato.Profesores._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(profesor.getId())};

        ContentResolver resolver = getContentResolver();
        //Cursor cursor = resolver.update(uri, values, selection, selectionArgs);
        resolver.update(uri, values, selection, selectionArgs);
        startActivity(intent); //Daba error porque método validar() estaba mal colocado

        //startActivityForResult(intent, -1); VER NOTA VERDE / NO HACE FALTA - SOLUCIONADO
    }


    /** No puedo poner startActivity(intent) en el método modificaItem() (UPDATE) porque da este error:
     * java.lang.NullPointerException: Attempt to invoke virtual method 'boolean android.content.
     * Intent.migrateExtraStreamToClipData()' on a null object reference
     * Seems like the error occurs on devices where Google Play Services are not installed, passed
     * intent will then be null.
     * You can make sure intent passed is not null by overriding startActivityForResult method in your Activity.
    @Override   // Evita el error con startActivity en el método update//
    public void startActivityForResult(Intent intent, int requestCode) {
        if (intent == null) {
            intent = new Intent();
        }
        try {
            super.startActivityForResult(intent, requestCode);
        } catch (Exception ignored){}
    }*/


    private ContentValues toContentValues() {

        ContentValues values = new ContentValues();
        values.put("NOMBRE", profesor.getNombre());
        values.put("EMAIL", profesor.getEmail());
        values.put("TELEFONO", profesor.getTelefono());
        values.put("EDAD", profesor.getEdad());
        values.put("SEXO", profesor.getSexo());
        values.put("FOTO", profesor.getFoto());
        return values;
    }

}
