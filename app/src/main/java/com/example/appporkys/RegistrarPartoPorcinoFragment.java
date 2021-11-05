package com.example.appporkys;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.example.appporkys.repositories.local.ControlPorcino.ControlPorcinoSQLiteManager;
import com.example.appporkys.repositories.local.PartoPorcino.PartoPorcinoSQLiteManager;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrarPartoPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarPartoPorcinoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrarPartoPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarPartoPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarPartoPorcinoFragment newInstance(String param1, String param2) {
        RegistrarPartoPorcinoFragment fragment = new RegistrarPartoPorcinoFragment();
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
    private PartoPorcinoSQLiteManager partoPorcinoSQLiteManager;

    TextView txtFechaParto;
    TextView txtLechonesVivosMachos;
    TextView txtLechonesVivosHembras;
    TextView txtLechonesMuertosMachos;
    TextView txtLechonesMuertosHembras;
    TextView txtPromedioPeso;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registrar_parto_porcino, container, false);

        txtFechaParto = v.findViewById(R.id.textView23);
        txtLechonesVivosMachos = v.findViewById(R.id.editTextTextPersonName7);
        txtLechonesVivosHembras = v.findViewById(R.id.editTextTextPersonName10);
        txtLechonesMuertosMachos = v.findViewById(R.id.editTextTextPersonName9);
        txtLechonesMuertosHembras = v.findViewById(R.id.editTextTextPersonName8);
        txtPromedioPeso = v.findViewById(R.id.editTextTextPersonName11);

        Button btnRegistrar = v.findViewById(R.id.button4);
        partoPorcinoSQLiteManager = new PartoPorcinoSQLiteManager(getActivity());

        txtFechaParto.setOnClickListener(new View.OnClickListener(){
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
                txtFechaParto.setText(date);
            }
        };

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtFechaParto.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getActivity(),"El campo fecha de parto está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtLechonesVivosMachos.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones vivos machos está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtLechonesVivosHembras.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones vivos hembras está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtLechonesMuertosMachos.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones muertos machos está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtLechonesMuertosHembras.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo lechones muertos hembras está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtPromedioPeso.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"El campo promedio de peso está vacio.",Toast.LENGTH_LONG).show();
                }else{
                    showProgressDialog();
                    partoPorcinoSQLiteManager.registrarPartoPorcino(new PartoPorcino(
                            porcinoId,
                            txtFechaParto.getText().toString(),
                            Integer.parseInt(txtLechonesVivosMachos.getText().toString()),
                            Integer.parseInt(txtLechonesVivosHembras.getText().toString()),
                            Integer.parseInt(txtLechonesMuertosMachos.getText().toString()),
                            Integer.parseInt(txtLechonesMuertosHembras.getText().toString()),
                            Double.parseDouble(txtPromedioPeso.getText().toString())
                    ));
                    hideProgressDialog();
                    Toast.makeText(getActivity(),"Se registró correctamente",Toast.LENGTH_SHORT).show();
                    txtFechaParto.setText("Clic aquí");
                    txtLechonesVivosMachos.setText("");
                    txtLechonesVivosHembras.setText("");
                    txtLechonesMuertosMachos.setText("");
                    txtLechonesMuertosHembras.setText("");
                    txtPromedioPeso.setText("");

                    //Navigation.findNavController(v).navigate(R.id.action_registrarPartoPorcinoFragment_to_listarPartoPorcinoFragment);
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