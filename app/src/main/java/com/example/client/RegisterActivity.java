package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.example.server.Register;

public class RegisterActivity extends AppCompatActivity{

    EditText edtName;
    EditText edtLogin;
    EditText edtPassword;

    public final String REGISTER_SERVICE_URL = "http://192.168.1.19:8080/Facerecognizer/services/upload/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        this.edtName = (EditText) findViewById(R.id.name);
        this.edtLogin = (EditText) findViewById(R.id.login);
        this.edtPassword = (EditText) findViewById(R.id.password);
    }

    public void register(View v){
        new Register(new Register.AsyncResponse(){

            @Override
            public void processFinish(String output){

                if(output.equals("true")) {
                    Toast.makeText(getBaseContext(), "Cadastro realizado com sucesso", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(getBaseContext(), "Não foi possível realizar o cadastro", Toast.LENGTH_SHORT).show();
                }
            }
        } ).execute(REGISTER_SERVICE_URL, edtLogin.getText().toString(), edtPassword.getText().toString(), edtName.getText().toString());
    }
}
