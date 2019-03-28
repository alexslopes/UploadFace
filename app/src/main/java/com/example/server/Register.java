package com.example.server;

import android.os.AsyncTask;

import com.example.uploadface.UploadFile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.http.entity.ContentType.APPLICATION_FORM_URLENCODED;

public class Register extends AsyncTask<String, Void, String> {

    public static final String TAG = "Register";

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public Register.AsyncResponse delegate = null;

    public Register(Register.AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... params) {

        String service = params[0];
        String login = params[1];
        String password = params[2];
        String name = params[3];
        String responseString = null;


        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("login", login));
            nameValuePairs.add(new BasicNameValuePair("password", password));
            nameValuePairs.add(new BasicNameValuePair("name", name));

            // server back-end URL
            HttpPost httppost = new HttpPost(service);

            // set the file input stream and file name as arguments
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // execute the request
            HttpResponse response = httpclient.execute(httppost);

            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity responseEntity = response.getEntity();
            responseString = EntityUtils.toString(responseEntity, "UTF-8");


        } catch (ClientProtocolException e) {
            System.err.println("Unable to make connection");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Unable to read file");
            e.printStackTrace();
        }

        return responseString;
    }

    @Override
    protected void onPostExecute(String response) {
        delegate.processFinish(response);
    }
}
