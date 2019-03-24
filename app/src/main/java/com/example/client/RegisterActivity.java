package com.example.client;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.example.server.Register;

public class RegisterActivity extends AppCompatActivity{

    EditText edtName;

    public final String REGISTER_SERVICE_URL = "http://192.168.1.19:8080/Facerecognizer/services/upload/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        this.edtName = (EditText) findViewById(R.id.name);
    }

    public void regiter(View v){
        new Register(new Register.AsyncResponse(){

            @Override
            public void processFinish(String output){
                Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
            }
        } ).execute(REGISTER_SERVICE_URL, edtName.getText().toString());
    }
}
