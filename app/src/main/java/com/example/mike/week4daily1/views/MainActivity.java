package com.example.mike.week4daily1.views;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.mike.week4daily1.PersonListFragment;
import com.example.mike.week4daily1.R;
import com.example.mike.week4daily1.model.Person;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View,PersonListFragment.PersonListListener {

    private LinearLayout listOfRandoms;
    private Spinner genderSelection;
    private Spinner nationalitySelection;
    private PersonListFragment personListFragment;
    private MainPresenter mainPresenter;
    public static final String TAG = "__TAG__";
    public HashMap<String, Bitmap> imageCache = new HashMap<>();
    boolean spinnerInstantiating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner.OnItemSelectedListener selectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onSelectedParams();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };

        spinnerInstantiating = true;

        listOfRandoms = findViewById( R.id.listOfRandoms );
        genderSelection = findViewById( R.id.genderSelection );
        genderSelection.setOnItemSelectedListener(selectedListener);
        nationalitySelection = findViewById( R.id.nationalitySelection );
        nationalitySelection.setOnItemSelectedListener( selectedListener );

        spinnerInstantiating = false;

        if ( savedInstanceState == null || !savedInstanceState.containsKey("presenter") ){
            mainPresenter = new MainPresenter();
        }else{
            mainPresenter = (MainPresenter) savedInstanceState.getSerializable("presenter");
        }
        mainPresenter.attachView( this );

        // Get Fragment
        personListFragment = (PersonListFragment) getSupportFragmentManager().findFragmentByTag( "PersonList" );

        if ( personListFragment == null ){
            personListFragment = new PersonListFragment();
            personListFragment.setRetainInstance(true);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.listOfRandoms, personListFragment, "PersonList")
                .commit();
        personListFragment.setPersonListListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable( "presenter", mainPresenter );
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

    private void onSelectedParams(){
        // When params are changed we load more
        mainPresenter.load( genderSelection.getSelectedItem().toString(), nationalitySelection.getSelectedItem().toString(), true );
    }

    public void loadMore(){
        mainPresenter.load( genderSelection.getSelectedItem().toString(), nationalitySelection.getSelectedItem().toString(), false );
    }

    @Override
    public void onLoad(List<Person> personList) {
        Log.d(TAG, "onLoad: "+personList.size());
        if (!spinnerInstantiating){
            personListFragment.setPersonArrayList( personList );
        }
    }

    @Override
    public void loadMorePlease() {
        loadMore();
    }
}
