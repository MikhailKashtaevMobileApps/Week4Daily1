package com.example.mike.week4daily1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mike.week4daily1.model.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PersonListFragment extends Fragment {

    // TODO: Customize parameters
    private PersonListListener personListListener;
    private View view;
    private RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Person> personArrayList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PersonListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_item_list, container, false);
        if (view instanceof RecyclerView) {
            recyclerViewAdapter = new RecyclerViewAdapter(personArrayList, personListListener);
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(recyclerViewAdapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void setPersonArrayList( List<Person> newPersonArrayList ){
        if( newPersonArrayList.size() > personArrayList.size() ){
            // Just add, dont clear
            personArrayList.addAll(newPersonArrayList.subList( personArrayList.size(), newPersonArrayList.size() ));
        }else{
            personArrayList.clear();
            personArrayList.addAll(newPersonArrayList);
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }

    public void setPersonListListener(PersonListListener personListListener){
        this.personListListener = personListListener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface PersonListListener{
        void loadMorePlease();
    }
}
