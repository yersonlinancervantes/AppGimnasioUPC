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
import android.widget.TextView;
import android.widget.Toast;

import com.example.appporkys.entity.ControlPorcino;
import com.example.appporkys.repositories.local.ControlPorcino.ControlPorcinoSQLiteManager;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroControlPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroControlPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistroControlPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroControlPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroControlPorcinoFragment newInstance(String param1, String param2) {
        RegistroControlPorcinoFragment fragment = new RegistroControlPorcinoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    int porcinoId = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            porcinoId = Integer.parseInt(getArguments().getString("porcinoId"));
        }
    }
    ProgressDialog progressDialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ControlPorcinoSQLiteManager controlPorcinoSQLiteManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registro_control_porcino, container, false);

        TextView txtPeso = v.findViewById(R.id.editTextTextPersonName);
        TextView txtFechaDeVacunacion = v.findViewById(R.id.txtFechaDeVacunacion);
        TextView txtDosis = v.findViewById(R.id.editTextTextPersonName3);
        TextView txtObservacion = v.findViewById(R.id.editTextTextMultiLine);

        Button btnRegistrar = v.findViewById(R.id.button2);
        controlPorcinoSQLiteManager = new ControlPorcinoSQLiteManager(getActivity());
       txtFechaDeVacunacion.setOnClickListener(new View.OnClickListener(){
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
                String date = month + "/" + dayOfMonth + "/" + year;
                txtFechaDeVacunacion.setText(date);
            }
        };

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtPeso.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo peso está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtFechaDeVacunacion.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getActivity(),"El campo fecha de vacuación está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtDosis.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo dosis está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtObservacion.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo observación está vacio.",Toast.LENGTH_LONG).show();
                }else{
                    showProgressDialog();
                    controlPorcinoSQLiteManager.registerSegimiento(new ControlPorcino(
                            porcinoId,
                            Double.parseDouble(txtPeso.getText().toString()),
                            txtFechaDeVacunacion.getText().toString(),
                            txtDosis.getText().toString(),
                            txtObservacion.getText().toString()
                    ));
                    hideProgressDialog();
                    Toast.makeText(getActivity(),"Se registro correctamente",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return v;
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(getContext());

        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                R.color.transparent
        );
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void hideProgressDialog(){
        progressDialog.dismiss();
    }
}