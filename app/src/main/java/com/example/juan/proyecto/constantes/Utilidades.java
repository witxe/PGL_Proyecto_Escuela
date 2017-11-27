package com.example.juan.proyecto.constantes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by juan on 24/11/17.
 */

public class Utilidades {

    public static void loadImageStorage (Context contexto, String imagenFichero, ImageView img) throws FileNotFoundException {

        File f = contexto.getFileStreamPath(imagenFichero);
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
        img.setImageBitmap(b);

    }

    public static void storeImage(Bitmap image, Context contexto, String fileName) throws IOException {

        FileOutputStream fos = contexto.openFileOutput(fileName, Context.MODE_PRIVATE);
        image.compress(Bitmap.CompressFormat.PNG,100,fos);
        fos.close();

    }
}
