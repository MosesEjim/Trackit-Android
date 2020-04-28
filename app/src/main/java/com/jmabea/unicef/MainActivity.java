package com.jmabea.unicef;

import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

        static  String email;
        EditText usernameField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText passwordField  = findViewById(R.id.password);
         usernameField = findViewById(R.id.username);


        final Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!(usernameField.getText().toString().equals(null) || passwordField.getText().toString().equals(null))){
                    System.out.println("values: "+usernameField.getText()+" "+passwordField.getText());
                    login.setBackgroundColor(Color.parseColor("#ff8080"));
                    Toast.makeText(MainActivity.this, "Loging in ...", Toast.LENGTH_SHORT).show();
                    email = usernameField.getText().toString();
                    JSONObject jsonObject = new JSONObject();
                    try{

                        jsonObject.put("email",usernameField.getText().toString());
                        jsonObject.put("password",passwordField.getText().toString());

                    }catch (Exception e){

                    }
                    post(jsonObject);
                }else {
                    System.out.println("hello");
                    Toast.makeText(MainActivity.this, "please enter a Valid email and password", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }


    public String  post(JSONObject jsonObject){

        final String[] webResponse = new String[1];
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE,jsonObject.toString());

        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://uniceftrack.herokuapp.com/api/login";
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .header("Accept","application/json")
                .header("content-type", "application/json")
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    webResponse[0] = response.body().string();
                    System.out.println("Result "+ webResponse[0]);
                    if(webResponse[0].equals("1")){
                        startActivity(new Intent(MainActivity.this,Programs.class));
                    }else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "Cannot Login, Invalid password or email", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        return webResponse[0];
    }
}
