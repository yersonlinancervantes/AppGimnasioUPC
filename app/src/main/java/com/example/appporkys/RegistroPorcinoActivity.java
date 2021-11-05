package com.example.appporkys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
import retrofit2.Retrofit;
import retrofit2.http.Multipart;

public class RegistroPorcinoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener_2;
    ImageView imgPorcinoUpload;
    String rutaImagen = "";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_porcino);

        TextView txtNombre = findViewById(R.id.ptNombre);
        TextView ptFechaNacimiento = findViewById(R.id.ptFechaNacimiento);
        Spinner spnTipo = findViewById(R.id.spnTipo);
        Spinner spnGenero = findViewById(R.id.spnGenero);
        Spinner spnRaza = findViewById(R.id.spnRaza);
        TextView ptFechaCompra = findViewById(R.id.ptFechaCompra);
        TextView txtPrecioCompra = findViewById(R.id.editTextNumber);

        Button btnUploadImage = findViewById(R.id.btnUploadImage);
        Button button = findViewById(R.id.button);
        imgPorcinoUpload = findViewById(R.id.imgPorcinoUpload);

        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamara();
            }
        });

        ptFechaNacimiento.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegistroPorcinoActivity.this,
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
                        RegistroPorcinoActivity.this,
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



        ArrayAdapter<CharSequence> adapterSpnTipo = ArrayAdapter.createFromResource(this,
                R.array.spnTipoOpciones, android.R.layout.simple_spinner_item);

        adapterSpnTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnTipo.setAdapter(adapterSpnTipo);
        spnTipo.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> adapterSpnGenero = ArrayAdapter.createFromResource(this,
                R.array.spnGeneroOpciones, android.R.layout.simple_spinner_item);

        spnGenero.setAdapter(adapterSpnGenero);
        spnGenero.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterSpnRaza = ArrayAdapter.createFromResource(this,
                R.array.spnRazaOpciones, android.R.layout.simple_spinner_item);

        spnRaza.setAdapter(adapterSpnRaza);
        spnRaza.setOnItemSelectedListener(this);
    }

    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);

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
        RequestBody requestBody =  RequestBody.create(MediaType.parse(getContentResolver().getType(Uri.fromFile(file))),file);
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
                Uri fotoUri = FileProvider.getUriForFile(this,"com.example.appporkys.fileprovider",imagenArchivo);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,fotoUri);
                startActivityForResult(intent,1);
            }
        //}
    }

    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgPorcinoUpload.setImageBitmap(imgBitmap);
        }else{
            rutaImagen = "";
        }
    }

    private File crearImagen() throws IOException {
        String nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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