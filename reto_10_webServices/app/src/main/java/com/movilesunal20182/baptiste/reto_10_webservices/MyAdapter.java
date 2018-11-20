package com.movilesunal20182.baptiste.reto_10_webservices;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements Filterable{
    private ArrayList<Library> libraries;
    private ArrayList<Library> librariesListFiltered;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextViewName, mTextViewCity, mTextViewDep;


        public MyViewHolder(View v) {
            super(v);
            mTextViewName = (TextView) v.findViewById(R.id.name_text_view);
            mTextViewCity = (TextView) v.findViewById(R.id.city_text_view);
            mTextViewDep = (TextView) v.findViewById(R.id.department_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Library> libraries) {
        this.libraries = libraries;
        this.librariesListFiltered = libraries;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Library lib = librariesListFiltered.get(position);
        holder.mTextViewName.setText(lib.getName());
        holder.mTextViewCity.setText(lib.getCity());
        holder.mTextViewDep.setText(lib.getDepartment());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return librariesListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    librariesListFiltered = libraries;
                } else {
                    ArrayList<Library> filteredList = new ArrayList<>();
                    for (Library row : libraries) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getCity().toLowerCase().contains(charString.toLowerCase())
                                || row.getName().toLowerCase().contains(charSequence)
                                || row.getDepartment().toLowerCase().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    librariesListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = librariesListFiltered;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                librariesListFiltered = (ArrayList<Library>) filterResults.values;
                notifyDataSetChanged();
            }


        };
    }


}
