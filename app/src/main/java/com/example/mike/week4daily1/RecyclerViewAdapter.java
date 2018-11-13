package com.example.mike.week4daily1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mike.week4daily1.model.Person;
import com.example.mike.week4daily1.views.MainContract;
import com.example.mike.week4daily1.views.custom_image_view.ImageViewDown;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Person> mValues;
    private PersonListFragment.PersonListListener personListListener;
    MainContract.View mainView;

    public RecyclerViewAdapter(List<Person> items, PersonListFragment.PersonListListener personListListener) {
        mValues = items;
        this.personListListener = personListListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Person p = mValues.get(position);

        holder.mItem = p;

        holder.personName.setText( p.getName() );
        holder.personGender.setText( p.getGender() );
        holder.personNationality.setText( p.getNationality() );
        holder.personPhoto.setImageURL( p.getPhotoURL() );

        if ( mValues.size() - position < 2 ){
            // Need to set a timer. Loads too much too fast
            personListListener.loadMorePlease();
        }

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageViewDown personPhoto;
        public final TextView personName;
        public final TextView personGender;
        public final TextView personNationality;

        public final View mView;
        public Person mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            personPhoto = ( ImageViewDown ) view.findViewById( R.id.personPhoto );
            personName = ( TextView ) view.findViewById( R.id.personName );
            personGender = ( TextView ) view.findViewById( R.id.personGender );
            personNationality = ( TextView ) view.findViewById( R.id.personNationality );
        }
    }
}
