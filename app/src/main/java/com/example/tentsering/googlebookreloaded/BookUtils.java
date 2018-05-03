package com.example.tentsering.googlebookreloaded;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BookUtils {
    private static final String LOG_TAG = BookUtils.class.getSimpleName();
    private static final String[] noAuthor = {"[No Author]"};

    public static List<Ebooks> extractBookData(String requestData) {

        URL site = createUrl(requestData);

        String jsonResponse = null; //root
        try{
            jsonResponse = makehttpRequest(site);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error! making http request", e);
        }

        List<Ebooks> ebooks = extractFeatureFromJson(jsonResponse);
        return ebooks;
    }

    private static List<Ebooks> extractFeatureFromJson(String bookjsonResponse) {


        //if the json content is empty return early
        if(TextUtils.isEmpty(bookjsonResponse)){
            return null;
        }
        List<Ebooks> ebooks = new ArrayList<>();
        try {
            JSONObject bookObject = new JSONObject(bookjsonResponse);
            JSONArray bookitems = bookObject.getJSONArray("items");
            //this will be in the loop
            for (int i = 0; i < bookitems.length(); i++) {
                JSONObject bookindices = bookitems.getJSONObject(i);
                String title = null;
                String authors = null;
                String volumeLink = null;
                String thumbNail = null;

                JSONObject volumeInfo = bookindices.getJSONObject("volumeInfo");
                JSONObject saleInfo = bookindices.getJSONObject("saleInfo");
                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    volumeLink = volumeInfo.getString("canonicalVolumeLink");

                    JSONObject imageLink = volumeInfo.getJSONObject("imageLinks");
                    try{
                        thumbNail = imageLink.getString("thumbnail");
                    }catch(Exception e){
                        e.printStackTrace();

                    }
                }
                catch (Exception e){
                    thumbNail = "http://www.thesportsbank.net/core/wp-content/uploads/2012/04/cuban.jpg";
                    volumeLink = "https://play.google.com/store/books";
                    authors = "No Author";
                    e.printStackTrace();
                }
                try{

                }catch(Exception e){
                    e.printStackTrace();
                }

                Ebooks value = new Ebooks(title, authors, volumeLink, thumbNail);
                ebooks.add(value);
            }
            //Parse the response given by the SAMPLE_JSON_RESPONSE string and build up a list of ThreeColumn objects with the corresponding data.

        } catch (JSONException e) {
            //if an error is thrown when executing any of the above statements in the "try" block, catch the exception here, so the app doesn't crash. Print a log message
            //with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return ebooks;
    }

    private static String makehttpRequest(URL site) throws IOException {
        String jsonResponse = ""; //root

        if (site == null) {
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) site.openConnection();
            httpURLConnection.setReadTimeout(1000);
            httpURLConnection.setConnectTimeout(1500);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error! The response code is : " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error! Problem retrieving JSON DATA", e);
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder buildString = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));//read binary to human readle character
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);//from character to reading word by word
            String eachline = bufferedReader.readLine();
            while(eachline!=null){
                buildString.append(eachline);
                eachline = bufferedReader.readLine();
            }
        }
        return buildString.toString();

    }

    private static URL createUrl(String stringUrl) {
        URL urls = null;
        try{
            urls = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error! URL creation failed" ,e);
        }
        return urls;
    }
}