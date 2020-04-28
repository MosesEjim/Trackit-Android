package com.jmabea.unicef;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataSender extends AsyncTask<String,Void,Void> {
    @Override
    protected Void doInBackground(String... data) {


        try{

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name_of_data_collector","Ejim");
            jsonObject.put("facility_id","1");
            jsonObject.put("no_of_usable_RTUF","10");
            jsonObject.put("usable_RTUF","1");
            jsonObject.put("expired_RTUF","0");
            jsonObject.put("expired_RTUF","0");
            jsonObject.put("no_of_damaged_RTUF","1");
            jsonObject.put("sc_available","0");
            jsonObject.put("complete_sc_record","1");
            jsonObject.put("stock_out_days","0");
            jsonObject.put("record_of_distributed_RTUF","1");
            jsonObject.put("no_of_dispensed_RTUF","1");


            String url = data[0];
            //String postString = data[1];
            URL apiUrl = new URL(url);
            HttpURLConnection postconnection = (HttpURLConnection)apiUrl.openConnection();
            postconnection.setRequestMethod("POST");
            postconnection.setRequestProperty("content-type", "application/json");
            postconnection.setDoOutput(true);
            postconnection.setConnectTimeout(10000);
            postconnection.connect();

            PrintWriter writer = new PrintWriter(postconnection.getOutputStream());
            //OutputStream os = new BufferedOutputStream(postconnection.getOutputStream());
           // BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            //OutputStream os = postconnection.getOutputStream();
            writer.print(jsonObject);
            writer.flush();
            writer.close();
            //System.out.println(postString+" \n"+url);
           // os.close();

        }catch(Exception e){
            System.out.println("exceptio caught"+e.getMessage());

        }
        return null;
    }
}
