package com.example.mike.week4daily1.views;

import android.content.Context;
import android.util.Log;

import com.example.mike.week4daily1.model.Person;
import com.example.mike.week4daily1.model.PersonLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter, Serializable {

    public static final String TAG = MainPresenter.class.getSimpleName()+"__TAG__";

    MainContract.View context;
    PersonLoader personLoader;
    ArrayList<Person> searchResults = new ArrayList<>();

    @Override
    public void attachView( MainContract.View v ) {
        context = v;
    }

    @Override
    public void detachView() {
        context = null;
    }

    public void load(){
        if ( personLoader == null ){
            load(null, null, true);
            return;
        }
        personLoader.load();
    }

    @Override
    public void load(String gender, String nationality, boolean cleanSlate) {
        if ( cleanSlate ){
            searchResults.clear();
            personLoader = new PersonLoader(new PersonLoader.OnLoadComplete() {
                @Override
                public void onLoadComplete(List<Person> personList) {
                    searchResults.addAll( personList );
                    MainPresenter.this.getView().onLoad(searchResults);
                }
            });
            personLoader.load(gender, nationality);
        }else{
            // Load cached params search
            load();
        }
    }

    public MainContract.View getView() {
        return context;
    }
}
