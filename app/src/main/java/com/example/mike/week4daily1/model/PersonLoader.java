package com.example.mike.week4daily1.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonLoader {
    public static final String TAG = PersonLoader.class.getSimpleName()+"__TAG__";

    private String DEFAULT_URL = "https://randomuser.me/api/";
    private String SEED = "qwerty123456asdfghvbnzxcqweasdzxccbvdfgert";

    private int pageCounter = 1;
    private int peoplePerPage = 20;
    private String url;
    private String cachedGender;
    private String cachedNationality;

    private OnLoadComplete resultGetter;
    private ArrayList<Person> searchResults = new ArrayList<>();

    public PersonLoader(OnLoadComplete resultGetter){
        this.resultGetter = resultGetter;
    }

    public void load(){
        load(cachedGender, cachedNationality);
    }

    @SuppressLint("StaticFieldLeak")
    public void load(String gender, String nationality){
        // Cache search params
        cachedGender = gender;
        cachedNationality = nationality;

        // Build url
        url = DEFAULT_URL;

        // Do pagination
        url += "?page="+String.valueOf(pageCounter)+"&results="+String.valueOf(peoplePerPage)+"&seed="+SEED;

        if (!gender.equals("-- All --")){
            // Add gender criterie
            url += "&gender="+gender;
        }

        if ( Person.encodeNat( nationality ) != null ){
            url += "&nat="+Person.encodeNat( nationality );
        }

        Log.d(TAG, "load: "+url);

        new AsyncTask<String, Void, List<Person>>() {
            @Override
            protected List<Person> doInBackground(String... strings) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response;
                String responseJSON;

                try {
                    response = client.newCall(request).execute();
                    responseJSON =response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }


                JSONArray results;

                try {
                    results = new JSONObject(responseJSON).getJSONArray("results");
                    ArrayList<Person> personArrayList = new ArrayList<>();

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject row = results.getJSONObject(i);
                        JSONObject name = row.getJSONObject("name");

                        Person p = new Person(
                                ""+name.getString("title")+" "+name.getString("first")+" "+name.getString("last"),
                                row.getString("gender"),
                                Person.decodeNat(row.getString("nat")),
                                row.getJSONObject("picture").getString("large")
                        );

                        personArrayList.add( p );
                    }

                    return personArrayList;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<Person> personList) {
                super.onPostExecute(personList);
                pageCounter++;
                resultGetter.onLoadComplete( personList );
            }
        }.execute();
    }

    private void addToStack(List<Person> people){
        searchResults.addAll( people );
    }

    public interface OnLoadComplete{
        void onLoadComplete(List<Person> personList);
    }
}
