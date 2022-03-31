package com.example.ejerciciosqlite.database;


import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
import androidx.annotation.Nullable;

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {

    private static String NOMBRE_BD= "base_banck_net";
    private static int VERSION_BD= 1;
    private static String TABLA_CLIENTE= "create table cliente (id int primary key, cedula text, nombre text, apellido text, telefono text, email text)";

    public SQLiteOpenHelper(@Nullable Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);

    }

    @Override
    public void onCreate(SQLiteDatabase base) {

        base.execSQL(TABLA_CLIENTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLA_CLIENTE);
        sqLiteDatabase.execSQL(TABLA_CLIENTE);
    }

    public boolean agregarCliente(int id, String cedula, String nombre, String apellido, String telefono, String email){
        SQLiteDatabase bd= getWritableDatabase();
        if (bd!=null){
            try{
                bd.execSQL("INSERT INTO cliente VALUES('"+id+"','"+cedula+"','"+nombre+"','"+apellido+"','"+telefono+"','"+email+"')");
                bd.close();
                return true;
            }catch (SQLiteConstraintException e){
                return false;
            }

        }else{
            return false;
        }
    }

    public void editarCliente(String id, String cedula, String nombre, String apellido, String email, String telefono){
        SQLiteDatabase bd= getWritableDatabase();
        bd.execSQL("UPDATE cliente SET cedula='"+cedula+"', nombre='"+nombre+"', apellido='"+apellido+"', email='"+email+"', telefono='"+telefono+"' WHERE id="+id);
        bd.close();
    }

    public void elimkinarCliente(String id){
        SQLiteDatabase bd= getWritableDatabase();
        bd.execSQL("DELETE FROM cliente WHERE id="+id);
        bd.close();
    }
}
