package com.example.appporkys;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;

import com.example.appporkys.entity.PartoPorcino;

import java.util.List;

public class AdapterListViewPartoPorcino extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context;
    List<PartoPorcino> listPartoPorcino;

    public AdapterListViewPartoPorcino(Context context,List<PartoPorcino> pListPartoPorcino){
        this.context = context;
        this.listPartoPorcino = pListPartoPorcino;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return listPartoPorcino.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final View vista = inflater.inflate(R.layout.row_listview_parto_porcino,null);

        TextView txtFechaParto = vista.findViewById(R.id.txtFechaParto);
        TextView txtLechonesVivosMachos = vista.findViewById(R.id.txtLechonesVivosMachos);
        TextView txtLechonesVivosHembras = vista.findViewById(R.id.txtLechonesVivosHembras);
        TextView txtLechonesMuertosMachos = vista.findViewById(R.id.txtLechonesMuertosMachos);
        TextView txtLechonesMuertosHembras = vista.findViewById(R.id.txtLechonesMuertosHembras);
        TextView txtPromedioPeso = vista.findViewById(R.id.txtPromedioPeso);
        Button btnModificarPartoPorcino = vista.findViewById(R.id.btnModificarPartoPorcino);

        txtFechaParto.setText("Fecha Parto: "+listPartoPorcino.get(i).getFechaParto());
        txtLechonesVivosMachos.setText("Lechones Vivos Machos: "+listPartoPorcino.get(i).getLechonesVivosMachos());
        txtLechonesVivosHembras.setText("Lechoes Vivos Hembras: "+listPartoPorcino.get(i).getLechonesVivosHembras());
        txtLechonesMuertosMachos.setText("Lechones Muertos Machos: "+listPartoPorcino.get(i).getLechonesMuertosMachos());
        txtLechonesMuertosHembras.setText("Lechones Muertos Hembras: "+listPartoPorcino.get(i).getLechonesMuertosHembras());
        txtPromedioPeso.setText("Promedio peso: "+listPartoPorcino.get(i).getPromedioPeso());

        btnModificarPartoPorcino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id", String.valueOf(listPartoPorcino.get(i).getId()));
                Navigation.findNavController(v).navigate(R.id.action_listarPartoPorcinoFragment_to_modificarPartoPorcinoFragment,bundle);
            }
        });

        return vista;
    }
}
