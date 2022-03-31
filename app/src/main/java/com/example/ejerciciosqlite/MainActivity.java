package com.example.ejerciciosqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ejerciciosqlite.adapter.ClienteAdapter;
import com.example.ejerciciosqlite.database.SQLiteOpenHelper;
import com.example.ejerciciosqlite.modelo.Cliente;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ClienteAdapter adapter;
    private RecyclerView recyclerView;

    private EditText idCliente;
    private EditText cedulaCliente;
    private EditText nombreCliente;
    private EditText apellidoCliente;
    private EditText emailCliente;
    private EditText telefonoCliente;
    private ImageView btn_registrar;
    private ImageView btn_refrescar;
    private ImageView btn_buscar;
    private ImageView btn_editar;
    private ImageView btn_eliminar;
    private ImageView btn_limpiarCampos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.listaClinetesVista);

        idCliente= findViewById(R.id.txt_idCliente);
        cedulaCliente= findViewById(R.id.txt_cedulaCliente);
        nombreCliente= findViewById(R.id.txt_nombreCliente);
        apellidoCliente= findViewById(R.id.txt_apellidoCliente);
        emailCliente= findViewById(R.id.txt_emailCliente);
        telefonoCliente= findViewById(R.id.txt_telefonoCliente);
        btn_refrescar= findViewById(R.id.btn_refrescar);
        btn_registrar= findViewById(R.id.btn_registrar);
        btn_buscar= findViewById(R.id.btn_buscar);
        btn_editar= findViewById(R.id.btn_editar);
        btn_eliminar= findViewById(R.id.btn_eliminar);
        btn_limpiarCampos= findViewById(R.id.btn_limpiarCampos);

        btn_refrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarClientes();
            }
        });

        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarCliente();
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarByid(idCliente.getText().toString());
            }
        });

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarCliente();
            }
        });

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarCliente();
            }
        });

        btn_limpiarCampos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idCliente.setText("");
                cedulaCliente.setText("");
                nombreCliente.setText("");
                apellidoCliente.setText("");
                emailCliente.setText("");
                telefonoCliente.setText("");
            }
        });

        listarClientes();

    }

    public void registrarCliente(){
        SQLiteOpenHelper base= new SQLiteOpenHelper(this);

        String id= idCliente.getText().toString();
        String cedula= cedulaCliente.getText().toString();
        String nombre= nombreCliente.getText().toString();
        String apellido= apellidoCliente.getText().toString();
        String email= emailCliente.getText().toString();
        String telefono= telefonoCliente.getText().toString();

        if (!id.isEmpty() && !cedula.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty() && !telefono.isEmpty()) {

            boolean bandera= base.agregarCliente(Integer.parseInt(id),cedula,nombre,apellido,telefono,email);

            if (bandera!=false){
                idCliente.setText("");
                cedulaCliente.setText("");
                nombreCliente.setText("");
                apellidoCliente.setText("");
                emailCliente.setText("");
                telefonoCliente.setText("");

                Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this, "El cliente con este id ya se encuentra registrado", Toast.LENGTH_LONG).show();
            }


        }else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }

    public void listarClientes(){
        SQLiteOpenHelper base= new SQLiteOpenHelper(this);
        SQLiteDatabase open= base.getReadableDatabase();

        Cursor fila = open.rawQuery("select * from cliente",null );
        ArrayList<Cliente> listaClientes= new ArrayList<>();

        if (fila.moveToFirst()){
            do{
                Cliente cliente= new Cliente();

                cliente.setId(fila.getInt(0));
                cliente.setCedula(fila.getString(1));
                cliente.setNombre(fila.getString(2));
                cliente.setApellido(fila.getString(3));
                cliente.setEmail(fila.getString(4));
                cliente.setTelefono(fila.getString(5));

                listaClientes.add(cliente);
            }while (fila.moveToNext());
            base.close();
            open.close();
            mostrarClientes(listaClientes);
            adapter.notifyDataSetChanged();

        }else{
            Toast.makeText(this, "No hay datos registrados", Toast.LENGTH_LONG).show();
        }
    }

    public void listarByid(String puntero){
        SQLiteOpenHelper base= new SQLiteOpenHelper(this);
        SQLiteDatabase open= base.getReadableDatabase();
        Cursor fila= open.rawQuery("select * from cliente where id="+puntero,null );

        ArrayList<Cliente> listaClientes= new ArrayList<>();

        if (fila.moveToFirst()){
            do{
                Cliente cliente= new Cliente();

                cliente.setId(fila.getInt(0));
                cliente.setCedula(fila.getString(1));
                cliente.setNombre(fila.getString(2));
                cliente.setApellido(fila.getString(3));
                cliente.setEmail(fila.getString(4));
                cliente.setTelefono(fila.getString(5));

                listaClientes.add(cliente);
            }while (fila.moveToNext());


            for (int i = 0; i < listaClientes.size(); i++) {
                idCliente.setText(String.valueOf(listaClientes.get(i).getId()));
                cedulaCliente.setText(listaClientes.get(i).getCedula());
                nombreCliente.setText(listaClientes.get(i).getNombre());
                apellidoCliente.setText(listaClientes.get(i).getApellido());
                emailCliente.setText(listaClientes.get(i).getTelefono());
                telefonoCliente.setText(listaClientes.get(i).getEmail());
            }

        }else{
            Toast.makeText(this, "No hay datos registrados", Toast.LENGTH_LONG).show();
        }
    }

    public void editarCliente(){
        SQLiteOpenHelper base= new SQLiteOpenHelper(this);

        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("¿Estas seguro de cambiar los datos a "
                        +nombreCliente.getText().toString()+" "
                        +apellidoCliente.getText().toString()
                        +"?")
                .setIcon(R.drawable.icon_warning)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        base.editarCliente(
                                idCliente.getText().toString().trim(),
                                cedulaCliente.getText().toString().trim(),
                                nombreCliente.getText().toString().trim(),
                                apellidoCliente.getText().toString().trim(),
                                emailCliente.getText().toString().trim(),
                                telefonoCliente.getText().toString().trim());

                        idCliente.setText("");
                        cedulaCliente.setText("");
                        nombreCliente.setText("");
                        apellidoCliente.setText("");
                        emailCliente.setText("");
                        telefonoCliente.setText("");

                        Toast.makeText(MainActivity.this, "Los cambios se han guardado exitosamente", Toast.LENGTH_LONG).show();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Se cancelo la operacion", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


    }

    public void eliminarCliente(){
        SQLiteOpenHelper base= new SQLiteOpenHelper(this);
        new AlertDialog.Builder(this)
                .setTitle("")
                .setMessage("¿Estas seguro de eliminar a "
                        +nombreCliente.getText().toString()+" "
                        +apellidoCliente.getText().toString()
                        +" del resgistro?")
                .setIcon(R.drawable.icon_warning)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        base.elimkinarCliente(idCliente.getText().toString().trim());
                        idCliente.setText("");
                        cedulaCliente.setText("");
                        nombreCliente.setText("");
                        apellidoCliente.setText("");
                        emailCliente.setText("");
                        telefonoCliente.setText("");
                        Toast.makeText(MainActivity.this, "Cliente eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Se cancelo la operacion", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    public void mostrarClientes(ArrayList<Cliente> listaClientes) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ClienteAdapter(this,listaClientes);
        recyclerView.setAdapter(adapter);
    }

}