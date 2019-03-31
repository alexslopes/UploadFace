package com.example.client;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.uploadface.UploadFile;
import com.example.user.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TrainFaceActivity extends AppCompatActivity implements View.OnClickListener{

    //private EditText name;
    private final int IMG_REQUEST = 1;
    public Uri path;
    private Bitmap bitmap;
    private ImageView imgView;
    private Button trainBtn, ChooseBnt;

    public final String TRAIN_SERVICE_URL = "http://192.168.1.19:8080/Facerecognizer/services/upload/training";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_face);
        trainBtn = (Button) findViewById(R.id.trainBtn);
        ChooseBnt = (Button) findViewById(R.id.chooseBtn);
        //name = findViewById(R.id.name);
        imgView = (ImageView) findViewById(R.id.imageView);
        ChooseBnt.setOnClickListener(this);
        trainBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chooseBtn:
                selectImage();
                break;

            case R.id.trainBtn:
                try {
                    trainImage();
                } catch (FileNotFoundException e){
                    Toast.makeText(getBaseContext(), "Imagem não encontrada", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data!=null)
        {
            path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgView.setImageBitmap(bitmap);
                imgView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void trainImage() throws FileNotFoundException {
        String id = DocumentsContract.getDocumentId(path);
        InputStream inputStream = getContentResolver().openInputStream(path);
        File file = new File(getCacheDir().getAbsolutePath()+"/"+id);
        writeFile(inputStream, file);
        String filePath = file.getAbsolutePath();
        new UploadFile(new UploadFile.AsyncResponse(){

            @Override
            public void processFinish(String output){
                Toast.makeText(getBaseContext(), output, Toast.LENGTH_SHORT).show();
            }
        } ).execute(TRAIN_SERVICE_URL, filePath, Person.getLogin(), Person.getPassword());
    }

    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( out != null ) {
                    out.close();
                }
                in.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }
}
