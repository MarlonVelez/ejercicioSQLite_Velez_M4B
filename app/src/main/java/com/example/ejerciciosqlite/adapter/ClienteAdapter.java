package com.example.ejerciciosqlite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ejerciciosqlite.MainActivity;
import com.example.ejerciciosqlite.R;
import com.example.ejerciciosqlite.modelo.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>{

    Context context;
    List<Cliente> clienteList;

    public ClienteAdapter(Context context, List<Cliente> clienteList) {
        this.context = context;
        this.clienteList = clienteList;
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cliente, parent, false);

        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.id.setText(String.valueOf(clienteList.get(position).getId()));
        holder.cedula.setText(clienteList.get(position).getCedula());
        holder.nombre.setText(clienteList.get(position).getNombre()+" "+clienteList.get(position).getApellido());
        holder.email.setText(clienteList.get(position).getEmail());
        holder.telefono.setText(clienteList.get(position).getTelefono());
        holder.img_user.setImageResource(R.drawable.user);

    }

    @Override
    public int getItemCount() {
        return clienteList.size();
    }

    public static class ClienteViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView cedula;
        TextView nombre;
        TextView email;
        TextView telefono;
        ImageView img_user;


        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.label_idCliente);
            cedula= itemView.findViewById(R.id.label_cedulaCliente);
            nombre= itemView.findViewById(R.id.label_nombreCliente);
            email= itemView.findViewById(R.id.label_correoCliente);
            telefono= itemView.findViewById(R.id.label_telefonoCliente);
            img_user= itemView.findViewById(R.id.imgUser);


        }
    }
}
