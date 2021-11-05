package com.example.appporkys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appporkys.entity.Usuario;
import com.example.appporkys.entity.UsuarioRequest;
import com.example.appporkys.repositories.local.Usuario.SessionManagement;
import com.example.appporkys.repositories.remote.ServiceFactory;
import com.example.appporkys.repositories.remote.request.UsuarioService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText txtUsername;
    EditText txtPassword;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnIngresar);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        Intent intentBackgroundService = new Intent(this,MyFirebaseMessagingService.class);
        startService(intentBackgroundService);

    }

    protected void onStart(){
        super.onStart();
        checkSession();
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();
        if(userId != -1){
            moveToMainActivity();
        }
    }

    public void login(View view){

        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();

        if(username.equals("")){
            Toast.makeText(LoginActivity.this,"El campo email está vacio.",Toast.LENGTH_LONG).show();
        }else if(password.equals("")){
            Toast.makeText(LoginActivity.this,"El campo password está vacio.",Toast.LENGTH_LONG).show();
        }else{
            showProgressDialog();
            UsuarioService jsonPlaceHolderApi = ServiceFactory.retrofit.create(UsuarioService.class);
            //username,password
            Call<Usuario> call = jsonPlaceHolderApi.getUsuario(new UsuarioRequest(username,password));
            call.enqueue(new Callback<Usuario>() {
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    //Usuario usuario = new Usuario();

                    if(!response.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"OCURRIO UN ERROR EN EL SERVIDOR",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Usuario usuario = response.body();
                        //List<Usuario> userList = response.body();
                        //System.out.println("User => "+userList);
                        /*for(Usuario user: userList){
                            usuario.setId(user.getId());
                            usuario.setUsername(user.getUsername());
                            usuario.setPassword(user.getPassword());
                        }*/

                        if(usuario.getId()>0){
                            SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                            sessionManagement.saveSession(usuario);
                            moveToMainActivity();
                        }else{
                            Toast.makeText(LoginActivity.this,"USUARIO O PASSWORD INCORRECTO",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    hideProgressDialog();
                    Toast.makeText(LoginActivity.this,"ERROR: "+t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

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

    public void moveToMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        Toast.makeText(getApplicationContext(),"BIENVENIDO", Toast.LENGTH_SHORT).show();
    }

}