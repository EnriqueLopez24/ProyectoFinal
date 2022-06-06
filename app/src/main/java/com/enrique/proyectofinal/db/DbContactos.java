package com.enrique.proyectofinal.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.enrique.proyectofinal.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper{
    Context context;
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }
    public long insertaContacto(String nombre, String telefono, String correo_electronico){
        long id=0;
        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);

            id = db.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }
    public ArrayList<Contactos> mostrarContactos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contactos = null;
        Cursor cursorContactos = null;
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);
        if (cursorContactos.moveToFirst()){
            do{
                contactos = new Contactos();
                contactos.setId(cursorContactos.getInt(0));
                contactos.setNombre(cursorContactos.getString(1));
                contactos.setTelefono(cursorContactos.getString(2));
                contactos.setCorreo_electronico(cursorContactos.getString(3));
                listaContactos.add(contactos);
            } while (cursorContactos.moveToNext());
        }
        cursorContactos.close();
        return listaContactos;
    }
    public Contactos verContactos(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contactos = null;
        Cursor cursorContactos;
        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1 ", null);
        if (cursorContactos.moveToFirst()){
                contactos = new Contactos();
                contactos.setId(cursorContactos.getInt(0));
                contactos.setNombre(cursorContactos.getString(1));
                contactos.setTelefono(cursorContactos.getString(2));
                contactos.setCorreo_electronico(cursorContactos.getString(3));
        }
        cursorContactos.close();
        return contactos;
    }
    public boolean editarContacto(int id, String nombre, String telefono, String correo_electronico){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL("UPDATE " + TABLE_CONTACTOS + " SET nombre = '" + nombre + "',telefono = '" + telefono + "',correo_electronico = '" + correo_electronico + "' WHERE id= '" + id + "' ");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
    public boolean eliminarContacto(int id){
        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.execSQL( "DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '"+ id +"'");
            correcto = true;
        } catch (Exception ex){
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
}
