package com.almacen.keplerclientesapp.includes;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpHandlerpost {

    private static final String TAG = HttpHandlerpost.class.getSimpleName();

    public HttpHandlerpost() {

    }

    public String makeServiceCall(String reqUrl,String user,String pass) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("user", user);
            conn.setRequestProperty("pass", pass);

            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            response="MalformedURLException: " + e.getMessage();
        } catch (ProtocolException e) {
            response="ProtocolException: " + e.getMessage();
        } catch (IOException e) {
            response="IOException: " + e.getMessage();
        } catch (Exception e) {
            response="Exception: " + e.getMessage();
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }
}