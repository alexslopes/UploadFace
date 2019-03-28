package com.example.client;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.server.Login;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText edtLogin;
    EditText edtPassword;
    JSONObject jObject = null;
    static String PASSWORD;
    static String LOGIN;

    public final String LOGIN_SERVICE_URL = "http://192.168.1.19:8080/Facerecognizer/services/upload/login";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        this.edtLogin = (EditText) findViewById(R.id.login);
        this.edtPassword = (EditText) findViewById(R.id.password);

    }

    public void login(View v){
        new Login(new Login.AsyncResponse(){

            @Override
            public void processFinish(String output){

                try {
                    jObject = new JSONObject(output);
                    //Toast.makeText(getBaseContext(), jObject.getString("id"), Toast.LENGTH_SHORT).show();

                    if(jObject != null) {
                        PASSWORD = edtPassword.getText().toString();
                        LOGIN = edtLogin.getText().toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } ).execute(LOGIN_SERVICE_URL, edtLogin.getText().toString(), edtPassword.getText().toString());

    }
}
