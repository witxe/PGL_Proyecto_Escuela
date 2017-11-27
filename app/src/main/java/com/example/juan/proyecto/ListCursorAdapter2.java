package com.example.juan.proyecto;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.juan.proyecto.Proveedor1.Contrato;

/**
 * Created by juan on 19/11/17.
 */

public class ListCursorAdapter2 extends CursorAdapter {

    public ListCursorAdapter2(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        // Referencias UI.
        TextView detalle = (TextView) view.findViewById(R.id.tv_detalle);
        final ImageView imagen = (ImageView) view.findViewById(R.id.iv_foto);


        // Get valores.
        String datos =
                cursor.getString(cursor.getColumnIndex(Contrato.Profesores._ID)) + "\nNombre: " +
                        cursor.getString(cursor.getColumnIndex(Contrato.Profesores.NOMBRE)) + "   Edad: " +
                        cursor.getString(cursor.getColumnIndex(Contrato.Profesores.EDAD)) + "\nEmail: " +
                        cursor.getString(cursor.getColumnIndex(Contrato.Profesores.EMAIL)) + "   Tel: " +
                        cursor.getString(cursor.getColumnIndex(Contrato.Profesores.TELEFONO));

        String foto = cursor.getString(cursor.getColumnIndex(Contrato.Profesores.FOTO));

        // Setup.
        detalle.setText(datos);

        Glide
                .with(context)
                .load(Uri.parse(Contrato.Profesores.FOTO + foto))
                .asBitmap()
                .error(R.drawable.ic_menu_camera)
                .centerCrop()
                .into(new BitmapImageViewTarget(imagen) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable
                                = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                        drawable.setCircular(true);
                        imagen.setImageDrawable(drawable);
                    }
                });
    }
}