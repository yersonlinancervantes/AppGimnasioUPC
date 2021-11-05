package com.example.appporkys;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appporkys.entity.FooRequest;
import com.example.appporkys.repositories.remote.ServiceFactory;
import com.example.appporkys.repositories.remote.request.PorcinoService;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistrarPorcinoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarPorcinoFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegistrarPorcinoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistrarPorcinoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistrarPorcinoFragment newInstance(String param1, String param2) {
        RegistrarPorcinoFragment fragment = new RegistrarPorcinoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener_2;
    ImageView imgPorcinoUpload;
    String rutaImagen = "";
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_registrar_porcino, container, false);

        TextView txtNombre = v.findViewById(R.id.ptNombre);
        TextView ptFechaNacimiento = v.findViewById(R.id.ptFechaNacimiento);
        Spinner spnTipo = v.findViewById(R.id.spnTipo);
        Spinner spnGenero = v.findViewById(R.id.spnGenero);
        Spinner spnRaza = v.findViewById(R.id.spnRaza);
        TextView ptFechaCompra = v.findViewById(R.id.ptFechaCompra);
        TextView txtPrecioCompra = v.findViewById(R.id.editTextNumber);


        Button button = v.findViewById(R.id.button);


        ptFechaNacimiento.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener_2,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        ptFechaCompra.setOnClickListener(new View.OnClickListener(){
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
                String date = year + "-" + month + "-" +dayOfMonth ;
                ptFechaCompra.setText(date);
            }
        };

        mDateSetListener_2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" +dayOfMonth ;
                ptFechaNacimiento.setText(date);
            }
        };



        ArrayAdapter<CharSequence> adapterSpnTipo = ArrayAdapter.createFromResource(getContext(),
                R.array.spnTipoOpciones, android.R.layout.simple_spinner_item);

        adapterSpnTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTipo.setAdapter(adapterSpnTipo);
        spnTipo.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapterSpnGenero = ArrayAdapter.createFromResource(getContext(),
                R.array.spnGeneroOpciones, android.R.layout.simple_spinner_item);

        spnGenero.setAdapter(adapterSpnGenero);
        spnGenero.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSpnRaza = ArrayAdapter.createFromResource(getContext(),
                R.array.spnRazaOpciones, android.R.layout.simple_spinner_item);

        spnRaza.setAdapter(adapterSpnRaza);
        spnRaza.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txtNombre.getText().toString().equals("")){
                    Toast.makeText(getContext(),"El campo nombre está vacio.",Toast.LENGTH_LONG).show();
                }else if(ptFechaNacimiento.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getContext(),"El campo fecha de nacimiento está vacio.",Toast.LENGTH_LONG).show();
                }else if(ptFechaCompra.getText().toString().equals("Clic aquí")){
                    Toast.makeText(getContext(),"El campo fecha de compra está vacio.",Toast.LENGTH_LONG).show();
                }else if(txtPrecioCompra.getText().toString().equals("")){
                    Toast.makeText(getContext(),"El campo precio de compra.",Toast.LENGTH_LONG).show();
                }else{
                    showProgressDialog();


                    String nombre = txtNombre.getText().toString();
                    String fechaNacimiento =  ptFechaNacimiento.getText().toString();
                    String tipo = spnTipo.getSelectedItem().toString();
                    String fechaCompra =  ptFechaCompra.getText().toString();
                    String genero = spnGenero.getSelectedItem().toString();
                    String raza = spnRaza.getSelectedItem().toString();
                    String precioCompra =  txtPrecioCompra.getText().toString();

                    PorcinoService jsonPlaceHolderApi = ServiceFactory.retrofit.create(PorcinoService.class);
                    Call<ResponseBody> call = jsonPlaceHolderApi.updateUserImage(new FooRequest(nombre,fechaNacimiento,tipo,fechaCompra,genero,raza,precioCompra));

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            System.out.println("Response =>"+response);
                            if(!response.isSuccessful()){
                                Toast.makeText(getContext(),"NO SE PUDO GUARDAR EL PORCINO",Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(),"SE GUARDO CORRECTAMENTE EL PORCINO!",Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(),
                                        MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                            hideProgressDialog();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            hideProgressDialog();
                            Toast.makeText(getContext(),"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
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

    private MultipartBody.Part prepareImagePart(String path, String partName){
        File file = new File(path);
        RequestBody requestBody =  RequestBody.create(MediaType.parse(getContext().getContentResolver().getType(Uri.fromFile(file))),file);
        return MultipartBody.Part.createFormData(partName,file.getName(),requestBody);
    }

    private void openCamara(){
        Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //if(intent.resolveActivity(getPackageManager()) != null){
        File imagenArchivo = null;
        try{
            imagenArchivo = crearImagen();
        }catch (IOException ex){
            Log.e("Error",ex.toString());
        }

        if(imagenArchivo != null){
            Uri fotoUri = FileProvider.getUriForFile(getContext(),"com.example.appporkys.fileprovider",imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
            startActivityForResult(intent,1);
        }
        //}
    }

    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgPorcinoUpload.setImageBitmap(imgBitmap);
        }else{
            rutaImagen = "";
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}