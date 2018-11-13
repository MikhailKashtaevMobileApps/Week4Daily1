package com.example.mike.week4daily1.views;

import com.example.mike.week4daily1.base.BasePresenter;
import com.example.mike.week4daily1.base.BaseView;
import com.example.mike.week4daily1.model.Person;

import java.util.List;

public class MainContract {

    public interface View extends BaseView{
        void onLoad(List<Person> personList);
    }
    public interface Presenter extends BasePresenter{
        void attachView( View v );
        void detachView();
        void load( String gender, String nationality, boolean cleanSlate );
    }

}
