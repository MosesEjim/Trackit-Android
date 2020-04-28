package com.jmabea.unicef;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
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

public class Availability extends AppCompatActivity implements LocationListener {
    String[] result = new String[11];
    Button submit;
    LocationManager locationManager;
    static double longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);
        TextView question1 = findViewById(R.id.question1);
        question1.setText(Question.availability[0]);
        TextView question2 = findViewById(R.id.question2);
        question2.setText(Question.availability[1]);
        TextView question3 = findViewById(R.id.question3);
        question3.setText(Question.availability[2]);
        TextView question4 = findViewById(R.id.question4);
        question4.setText(Question.availability[3]);
        TextView question5 = findViewById(R.id.question5);
        question5.setText(Question.availability[4]);
        TextView question6 = findViewById(R.id.question6);
        question6.setText(Question.availability[5]);
        TextView question7 = findViewById(R.id.question7);
        question7.setText(Question.availability[6]);
        TextView question8 = findViewById(R.id.question8);
        question8.setText(Question.availability[7]);
        TextView question9 = findViewById(R.id.question9);
        question9.setText(Question.availability[8]);
        TextView question10 = findViewById(R.id.question10);
        question10.setText(Question.availability[9]);
        TextView question11 = findViewById(R.id.question11);
        question11.setText(Question.availability[10]);

        final EditText editText1 = findViewById(R.id.editText1);
        final EditText editText5 = findViewById(R.id.editText5);
        final EditText editText8 = findViewById(R.id.editText8);
        final EditText editText11 = findViewById(R.id.editText11);

        final RadioGroup radioGroup2 = findViewById(R.id.radiogroup2);
        final RadioGroup radioGroup3 = findViewById(R.id.radiogroup3);
        final RadioGroup radioGroup4 = findViewById(R.id.radiogroup4);
        final RadioGroup radioGroup6 = findViewById(R.id.radiogroup6);
        final RadioGroup radioGroup7 = findViewById(R.id.radiogroup7);
        final RadioGroup radioGroup9 = findViewById(R.id.radiogroup9);
        final RadioGroup radioGroup10 = findViewById(R.id.radiogroup10);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);


        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result[0] = editText1.getText().toString();

                if(""+radioGroup2.getCheckedRadioButtonId()=="true2"){
                    result[1] = "1";
                }
                else {
                    result[1] = "0";
                }
                if(""+radioGroup3.getCheckedRadioButtonId()=="true3"){
                    result[2] = "1";
                }
                else {
                    result[2] = "0";
                }
                if(""+radioGroup4.getCheckedRadioButtonId()=="true4"){
                    result[3] = "1";
                }
                else {
                    result[3] = "0";
                }

                result[4] = editText5.getText().toString();

                if(""+radioGroup6.getCheckedRadioButtonId()=="true6"){
                    result[5] = "1";
                }
                else {
                    result[5] = "0";
                }

                if(""+radioGroup7.getCheckedRadioButtonId()=="true7"){
                    result[6] = "1";
                }
                else {
                    result[6] = "0";
                }

                result[7] = editText8.getText().toString();

                if(""+radioGroup9.getCheckedRadioButtonId()=="true9"){
                    result[8] = "1";
                }
                else {
                    result[8] = "0";
                }

                if(""+radioGroup10.getCheckedRadioButtonId()=="true10"){
                    result[9] = "1";
                }
                else {
                    result[9] = "0";
                }

                result[10] = editText11.getText().toString();






                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    OnGPS();
                }else {
                    getLocation();
                }


                JSONObject jsonObject = new JSONObject();
                try{

                    jsonObject.put("name_of_data_collector",MainActivity.email);
                    jsonObject.put("facility_id","1");
                    jsonObject.put("no_of_usable_RTUF",result[0]);
                    jsonObject.put("usable_RTUF",result[1]);
                    jsonObject.put("expired_RTUF",result[2]);
                    jsonObject.put("damaged_RTUF",result[3]);
                    jsonObject.put("no_of_damaged_RTUF",result[4]);
                    jsonObject.put("sc_available",result[5]);
                    jsonObject.put("complete_sc_record",result[6]);
                    jsonObject.put("stock_out_days",result[7]);
                    jsonObject.put("dispensed_RTUF_record",result[8]);
                    jsonObject.put("record_of_distributed_RTUF", result[9]);
                    jsonObject.put("no_of_dispensed_RTUF",result[10]);
                    jsonObject.put("longitude",longitude);
                    jsonObject.put("latitude",latitude);
                }catch (Exception e){

                }
                trial(jsonObject);
//
            }
        });




    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(Availability.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
         ActivityCompat.checkSelfPermission(Availability.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        }else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,this);



            Location gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location passiveLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(gpsLocation != null){
                 longitude = gpsLocation.getLongitude();
                 latitude = gpsLocation.getLatitude();
                Toast.makeText(this,"Successfully Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Availability.this,Questioniare.class));
            }else if(networkLocation != null){
                 longitude = networkLocation.getLongitude();
                 latitude = networkLocation.getLatitude();
                Toast.makeText(this,"Successfully Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Availability.this,Questioniare.class));
            }else  if(passiveLocation != null){
                 longitude = passiveLocation.getLongitude();
                 latitude = passiveLocation.getLatitude();
                Toast.makeText(this,"Successfully Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Availability.this,Questioniare.class));
            }else {
                Toast.makeText(this,"can't get your location", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("enable Gps").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog  alertDialog = builder.create();
        alertDialog.show();
    }

    public void  trial(JSONObject jsonObject){


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE,jsonObject.toString());



        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://uniceftrack.herokuapp.com/api/stock";
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
                    String webResponse = response.body().string();
                    System.out.println(webResponse);
                }
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
