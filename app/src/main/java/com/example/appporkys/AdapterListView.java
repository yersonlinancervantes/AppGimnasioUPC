package com.example.appporkys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.example.appporkys.entity.Porcino;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class AdapterListView extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<Porcino> listPorcino;

    public AdapterListView(Context context,List<Porcino> pListProduct){
        this.context = context;
        this.listPorcino = pListProduct;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent){

        final View vista = inflater.inflate(R.layout.row_listview,null);

        TextView txtNombre = vista.findViewById(R.id.txtNombre);
        TextView txtFechaCompra = vista.findViewById(R.id.txtFechaCompra);
        TextView txtGenero = vista.findViewById(R.id.txtGenero);
        TextView txtRaza = vista.findViewById(R.id.txtRaza);

        Button btnControlPorcino = vista.findViewById(R.id.btnControlPorcino);
        Button btnParto = vista.findViewById(R.id.btnParto);

        txtNombre.setText("Nombre: "+listPorcino.get(i).getNombre());
        txtFechaCompra.setText("Fecha Compra: "+listPorcino.get(i).getFechaCompra());
        txtGenero.setText("Genero: "+listPorcino.get(i).getGenero());
        txtRaza.setText("Raza: "+listPorcino.get(i).getRaza());

        btnControlPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("porcinoId", String.valueOf(listPorcino.get(i).getId()));

                Navigation.findNavController(v).navigate(R.id.action_listarPorcino_to_listarControlPorcinoFragment,bundle);
            }
        });

        btnParto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("porcinoId", String.valueOf(listPorcino.get(i).getId()));
                Navigation.findNavController(v).navigate(R.id.action_listarPorcino_to_listarPartoPorcinoFragment,bundle);
            }
        });

        return vista;
    }

    @Override
    public int getCount(){
        return listPorcino.size();
    }

    @Override
    public Object getItem(int position){

        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
