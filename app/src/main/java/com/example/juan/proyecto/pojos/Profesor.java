package com.example.juan.proyecto.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Juan on 11/10/2016.
 */

public class Profesor implements Parcelable{

    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private int edad;
    private String sexo;
    private String foto;

    public Profesor(String nombre, String email, String telefono, int edad, String sexo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.edad = edad;
        this.sexo = sexo;
        this.foto = foto;
    }

    protected Profesor(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        email = in.readString();
        telefono = in.readString();
        edad = in.readInt();
        sexo = in.readString();
        foto = in.readString();
    }

    public static final Creator<Profesor> CREATOR = new Creator<Profesor>() {
        @Override
        public Profesor createFromParcel(Parcel in) {
            return new Profesor(in);
        }

        @Override
        public Profesor[] newArray(int size) {
            return new Profesor[size];
        }
    };

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getFoto() { return foto; }

    public void setFoto(String foto) { this.foto = foto; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(email);
        dest.writeString(telefono);
        dest.writeInt(edad);
        dest.writeString(sexo);
        dest.writeString(foto);
    }
}
