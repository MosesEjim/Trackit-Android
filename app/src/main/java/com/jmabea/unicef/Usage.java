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
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Usage extends AppCompatActivity implements LocationListener {
    String[] result = new String[14];
    LocationManager locationManager;
    static double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usage);

        TextView question1 = findViewById(R.id.question1);
        question1.setText(Question.usage[0]);
        TextView question2 = findViewById(R.id.question2);
        question2.setText(Question.usage[1]);
        TextView question3 = findViewById(R.id.question3);
        question3.setText(Question.usage[2]);
        TextView question4 = findViewById(R.id.question4);
        question4.setText(Question.usage[3]);
        TextView question5 = findViewById(R.id.question5);
        question5.setText(Question.usage[4]);
        TextView question6 = findViewById(R.id.question6);
        question6.setText(Question.usage[5]);
        TextView question7 = findViewById(R.id.question7);
        question7.setText(Question.usage[6]);
        TextView question8 = findViewById(R.id.question8);
        question8.setText(Question.usage[7]);
        TextView question9 = findViewById(R.id.question9);
        question9.setText(Question.usage[8]);
        TextView question10 = findViewById(R.id.question10);
        question10.setText(Question.usage[9]);
        TextView question11 = findViewById(R.id.question11);
        question11.setText(Question.usage[10]);
        TextView question12 = findViewById(R.id.question12);
        question12.setText(Question.usage[11]);
        TextView question13 = findViewById(R.id.question13);
        question13.setText(Question.usage[12]);
        TextView question14 = findViewById(R.id.question14);
        question14.setText(Question.usage[13]);

        final RadioGroup radioGroup1 = findViewById(R.id.radiogroup1);
        final RadioGroup radioGroup2 = findViewById(R.id.radiogroup2);
        final RadioGroup radioGroup3 = findViewById(R.id.radiogroup3);
        final RadioGroup radioGroup4 = findViewById(R.id.radiogroup4);
        final RadioGroup radioGroup5 = findViewById(R.id.radiogroup5);
        final RadioGroup radioGroup6 = findViewById(R.id.radiogroup6);
        final RadioGroup radioGroup12 = findViewById(R.id.radiogroup12);
        final RadioGroup radioGroup13 = findViewById(R.id.radiogroup13);

        final EditText editText7 = findViewById(R.id.editText7);
        final EditText editText8 = findViewById(R.id.editText8);
        final EditText editText9 = findViewById(R.id.editText9);
        final EditText editText10 = findViewById(R.id.editText10);
        final EditText editText11 = findViewById(R.id.editText11);
        final EditText editText14 = findViewById(R.id.editText14);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);

        Button submitUsage = findViewById(R.id.submitUsage);
        submitUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(""+radioGroup1.getCheckedRadioButtonId()=="true1"){
                    result[0] = "1";
                }else {
                    result[0] = "0";
                }
                if(""+radioGroup2.getCheckedRadioButtonId()=="true2"){
                    result[1] = "1";
                }else {
                    result[1] = "0";
                }
                if(""+radioGroup3.getCheckedRadioButtonId()=="true3"){
                    result[2] = "1";
                }else {
                    result[2] = "0";
                }
                if(""+radioGroup4.getCheckedRadioButtonId()=="true4"){
                    result[3] = "1";
                }else {
                    result[3] = "0";
                }
                if(""+radioGroup5.getCheckedRadioButtonId()=="true5"){
                    result[4] = "1";
                }else {
                    result[4] = "0";
                }
                if(""+radioGroup6.getCheckedRadioButtonId()=="true6"){
                    result[5] = "1";
                }else {
                    result[5] = "0";
                }
                result[6] = editText7.getText().toString();
                result[7] = editText8.getText().toString();
                result[8] = editText9.getText().toString();
                result[9] = editText9.getText().toString();
                result[10] = editText10.getText().toString();

                if(""+radioGroup12.getCheckedRadioButtonId()=="true12"){
                    result[11] = "1";
                }else {
                    result[11] = "0";
                }
                if(""+radioGroup13.getCheckedRadioButtonId()=="true13"){
                    result[12] = "1";
                }else {
                    result[12] = "0";
                }

                result[13] = editText14.getText().toString();

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
                    jsonObject.put("protocol_book",result[0]);
                    jsonObject.put("describe_dosage1",result[1]);
                    jsonObject.put("describe_dosage2",result[2]);
                    jsonObject.put("describe_dosage3",result[3]);
                    jsonObject.put("seller_at_home",result[4]);
                    jsonObject.put("frequency_of_distribution",result[5]);
                    jsonObject.put("no_of_patient_charts",result[6]);
                    jsonObject.put("sachets_dispensed",result[7]);
                    jsonObject.put("patient_entries_reviewed",result[8]);
                    jsonObject.put("child_weight_in_kg", result[9]);
                    jsonObject.put("days_in_treatment",result[10]);
                    jsonObject.put("child_recovered",result[11]);
                    jsonObject.put("child_transfered",result[12]);
                    jsonObject.put("final_weight_in_kg",result[13]);
                    jsonObject.put("longitude",longitude);
                    jsonObject.put("latitude",latitude);
                }catch (Exception e){

                }
                System.out.println(Arrays.toString(result));
                post(jsonObject);
            }
        });
    }

    public void  post(JSONObject jsonObject){


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(MEDIA_TYPE,jsonObject.toString());



        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "https://uniceftrack.herokuapp.com/api/usage";
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

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(Usage.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Usage.this,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
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
                startActivity(new Intent(Usage.this,Questioniare.class));
            }else if(networkLocation != null){
                longitude = networkLocation.getLongitude();
                latitude = networkLocation.getLatitude();
                Toast.makeText(this,"Successfully Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Usage.this,Questioniare.class));
            }else  if(passiveLocation != null){
                longitude = passiveLocation.getLongitude();
                latitude = passiveLocation.getLatitude();
                Toast.makeText(this,"Successfully Uploaded", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Usage.this,Questioniare.class));
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
