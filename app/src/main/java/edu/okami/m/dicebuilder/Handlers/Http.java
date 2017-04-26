package edu.okami.m.dicebuilder.Handlers;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by M on 4/19/2017.
 */

public class Http {

    private static final String TAG = Http.class.getSimpleName();

    public Http(){

    }

    public String makePostCall(String reqUrl, JSONObject json){
        HttpsURLConnection conn;
        String data = json.toString();
        String result = null;

        try{
            conn = (HttpsURLConnection) ((new URL(reqUrl).openConnection()));
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");
            conn.connect();

            OutputStream outputStream = conn.getOutputStream();
            //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            outputStream.write(data.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            //Read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            result = stream_to_string(in);

        }catch(MalformedURLException e){
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch(ProtocolException e){
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch(IOException e){
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch(Exception e){
            Log.e(TAG, "Exception: " + e.getMessage());
        }

        return result;

    }

    private String stream_to_string(InputStream is){
        BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        } finally{
            try{
                is.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void register_user(JSONObject registerObject){
        new Register().execute(registerObject);
    }

    public void send_msg(){
        new SendMessage().execute();
    }

    private class Register extends AsyncTask<JSONObject, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(JSONObject...args){

             String resp = makePostCall("https://kamorris.com/temple/fcmhelper/register.php", args[0]);

            Log.d(TAG, "Register User Response: " + resp);
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }

    private class SendMessage extends AsyncTask<JSONObject, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(JSONObject...args){

            JSONObject json = new JSONObject();
            try{
                json.put("app_key", "AIzaSyDAtOzYH8tgR7nYXSxiSnr2oozDF8yv4mY");
                json.put("recipient_id", "mjdev3030@gmail.com");
                json.put("message", "Hello");
            } catch(JSONException e){
                e.printStackTrace();
            }

            String resp = makePostCall("https://kamorris.com/temple/fcmhelper/send_message.php", json);

            Log.d(TAG, "Send Message Response: " + resp);

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
        }
    }
}
