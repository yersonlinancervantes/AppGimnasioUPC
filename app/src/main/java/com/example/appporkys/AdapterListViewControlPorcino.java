package com.example.appporkys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.appporkys.entity.ControlPorcino;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListViewControlPorcino extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<ControlPorcino> listControlPorcino;

    public AdapterListViewControlPorcino(Context context,List<ControlPorcino> pListProduct){
        this.context = context;
        this.listControlPorcino = pListProduct;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent){

        final View vista = inflater.inflate(R.layout.row_listview_control_porcino,null);

        TextView txtPesoControl = vista.findViewById(R.id.txtPesoControl);
        TextView txtFechaVacunacion = vista.findViewById(R.id.txtFechaVacunacion);
        TextView txtDosis = vista.findViewById(R.id.txtDosis);
        TextView txtObservacion = vista.findViewById(R.id.txtObservacion);
        Button btnModificarControlPorcino = vista.findViewById(R.id.btnModificarControlPorcino);

        txtPesoControl.setText("Peso: "+listControlPorcino.get(i).getPeso());
        txtFechaVacunacion.setText("Fecha Vacunacion: "+listControlPorcino.get(i).getFechaVacunacion());
        txtDosis.setText("Dosis: "+listControlPorcino.get(i).getDosis());
        txtObservacion.setText("Observación: "+listControlPorcino.get(i).getObservacion());

        btnModificarControlPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(listControlPorcino.get(i).getId()));
                Navigation.findNavController(v).navigate(R.id.action_listarControlPorcinoFragment_to_modificarControlPorcinoFragment,bundle);
            }
        });

        return vista;
    }

    @Override
    public int getCount(){
        return listControlPorcino.size();
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
