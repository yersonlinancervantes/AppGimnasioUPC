package com.example.appporkys;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.example.appporkys.repositories.local.ControlPorcino.ControlPorcinoSQLiteManager;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarControlPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarControlPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModificarControlPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarControlPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarControlPorcinoFragment newInstance(String param1, String param2) {
        ModificarControlPorcinoFragment fragment = new ModificarControlPorcinoFragment();
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
    private ControlPorcinoSQLiteManager controlPorcinoSQLiteManager;

    EditText txtPeso;
    TextView txtFechaVacunacion;
    EditText txtDosis;
    EditText txtObservacion;
    Button btnModificar;

    ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_modificar_control_porcino, container, false);

        controlPorcinoSQLiteManager = new ControlPorcinoSQLiteManager(getActivity());
        ControlPorcino controlPorcino = controlPorcinoSQLiteManager.getControlPorcinoById(id);

        txtPeso = v.findViewById(R.id.editTextTextPersonName4);
        txtFechaVacunacion = v.findViewById(R.id.textView38);
        txtDosis = v.findViewById(R.id.editTextTextPersonName6);
        txtObservacion = v.findViewById(R.id.editTextTextMultiLine2);
        btnModificar = v.findViewById(R.id.button3);

        txtPeso.setText(String.valueOf(controlPorcino.getPeso()));
        txtFechaVacunacion.setText(controlPorcino.getFechaVacunacion());
        txtDosis.setText(controlPorcino.getDosis());
        txtObservacion.setText(controlPorcino.getObservacion());

        txtFechaVacunacion.setOnClickListener(new View.OnClickListener(){
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
                txtFechaVacunacion.setText(date);
            }
        };

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPeso.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo peso está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtFechaVacunacion.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getActivity(),"El campo fecha de vacuación está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtDosis.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo dosis está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtObservacion.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo observación está vacio.",Toast.LENGTH_LONG).show();
                }else{
                    controlPorcinoSQLiteManager.actualizarControlPorcino(new ControlPorcino(
                            id,
                            0,
                            Double.parseDouble(txtPeso.getText().toString()),
                            txtFechaVacunacion.getText().toString(),
                            txtDosis.getText().toString(),
                            txtObservacion.getText().toString()
                    ));
                    Toast.makeText(getActivity(),"Se modificó correctamente",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}