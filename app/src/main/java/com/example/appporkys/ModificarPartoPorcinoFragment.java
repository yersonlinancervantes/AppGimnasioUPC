package com.example.appporkys;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appporkys.entity.ControlPorcino;
import com.example.appporkys.entity.PartoPorcino;
import com.example.appporkys.repositories.local.PartoPorcino.PartoPorcinoSQLiteManager;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarPartoPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarPartoPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModificarPartoPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarPartoPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarPartoPorcinoFragment newInstance(String param1, String param2) {
        ModificarPartoPorcinoFragment fragment = new ModificarPartoPorcinoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    int id = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            id = Integer.parseInt(getArguments().getString("id"));
        }
    }

    private PartoPorcinoSQLiteManager partoPorcinoSQLiteManager;

    TextView fechaParto;
    EditText lechonesVivosMachos;
    EditText lechonesVivosHembras;
    EditText lechonesMuertosMachos;
    EditText lechonesMuertosHembras;
    EditText promedioPeso;
    Button btnModificar;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_modificar_parto_porcino, container, false);

        fechaParto = v.findViewById(R.id.textView31);
        lechonesVivosMachos = v.findViewById(R.id.editTextTextPersonName12);
        lechonesVivosHembras = v.findViewById(R.id.editTextTextPersonName13);
        lechonesMuertosMachos = v.findViewById(R.id.editTextTextPersonName14);
        lechonesMuertosHembras = v.findViewById(R.id.editTextTextPersonName15);
        promedioPeso = v.findViewById(R.id.editTextTextPersonName16);
        btnModificar = v.findViewById(R.id.button5);

        partoPorcinoSQLiteManager = new PartoPorcinoSQLiteManager(getActivity());
        PartoPorcino partoPorcino =  partoPorcinoSQLiteManager.getPartoPorcinoById(id);

        fechaParto.setText(partoPorcino.getFechaParto());
        lechonesVivosMachos.setText(String.valueOf(partoPorcino.getLechonesVivosMachos()));
        lechonesVivosHembras.setText(String.valueOf(partoPorcino.getLechonesVivosHembras()));
        lechonesMuertosMachos.setText(String.valueOf(partoPorcino.getLechonesMuertosMachos()));
        lechonesMuertosHembras.setText(String.valueOf(partoPorcino.getLechonesMuertosHembras()));
        promedioPeso.setText(String.valueOf(partoPorcino.getPromedioPeso()));


        fechaParto.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                fechaParto.setText(date);
            }
        };

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fechaParto.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo fecha de parto está vacio.",Toast.LENGTH_LONG).show();
                }else if(lechonesVivosMachos.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getActivity(),"El campo lechones vivos machos de vacuación está vacio.",Toast.LENGTH_LONG).show();
                }else if(lechonesVivosHembras.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones vivos hembras está vacio.",Toast.LENGTH_LONG).show();
                }else if(lechonesMuertosMachos.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones muertos machos está vacio.",Toast.LENGTH_LONG).show();
                }else if(lechonesMuertosHembras.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones muertos hembras está vacio.",Toast.LENGTH_LONG).show();
                }else if(promedioPeso.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo promedio está vacio.",Toast.LENGTH_LONG).show();
                }else{
                    partoPorcinoSQLiteManager.actualizarPartoPorcino(new PartoPorcino(
                            id,
                            0,
                            fechaParto.getText().toString(),
                            Integer.parseInt(lechonesVivosMachos.getText().toString()),
                            Integer.parseInt(lechonesVivosHembras.getText().toString()),
                            Integer.parseInt(lechonesMuertosMachos.getText().toString()),
                            Integer.parseInt(lechonesMuertosHembras.getText().toString()),
                            Double.parseDouble(promedioPeso.getText().toString())
                    ));
                    Toast.makeText(getActivity(),"Se modificó correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}