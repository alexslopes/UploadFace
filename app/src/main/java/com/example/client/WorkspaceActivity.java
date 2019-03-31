package com.example.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.Person;

import org.w3c.dom.Text;

public class WorkspaceActivity extends AppCompatActivity {
    TextView user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workspace);
        user = (TextView) findViewById(R.id.user);
        user.setText("Olá " + Person.getName());

        findViewById(R.id.train).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkspaceActivity.this, TrainFaceActivity.class));
            }
        });

        findViewById(R.id.detect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WorkspaceActivity.this, DetectFaceActivity.class));
            }
        });

        findViewById(R.id.signout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person.deleteUser();
                startActivity(new Intent(WorkspaceActivity.this, MainActivity.class));
            }
        });

    }
}
