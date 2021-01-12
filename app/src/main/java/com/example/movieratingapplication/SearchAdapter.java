/*
package com.example.movieratingapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

private class SearchAdapter extends ArrayAdapter<Film> {

    private ArrayList<Film> filmList;
    private ArrayList<Film> filteredList;
    private FilmFilter filter;

    public SearchAdapter(Context context, null,
                           ArrayList<Film> filteredList) {
        super(context, 0, filteredList);
        this.countryList = new ArrayList<Film>();
        this.countryList.addAll(countryList);
        this.originalList = new ArrayList<Film>();
        this.originalList.addAll(countryList);
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter  = new CountryFilter();
        }
        return filter;
    }


    private class ViewHolder {
        TextView code;
        TextView name;
        TextView continent;
        TextView region;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.country_info, null);

            holder = new ViewHolder();
            holder.code = (TextView) convertView.findViewById(R.id.code);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.continent = (TextView) convertView.findViewById(R.id.continent);
            holder.region = (TextView) convertView.findViewById(R.id.region);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Country country = countryList.get(position);
        holder.code.setText(country.getCode());
        holder.name.setText(country.getName());
        holder.continent.setText(country.getContinent());
        holder.region.setText(country.getRegion());

        return convertView;

    }

    private class CountryFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if(constraint != null && constraint.toString().length() > 0)
            {
                ArrayList<Country> filteredItems = new ArrayList<Country>();

                for(int i = 0, l = originalList.size(); i < l; i++)
                {
                    Country country = originalList.get(i);
                    if(country.toString().toLowerCase().contains(constraint))
                        filteredItems.add(country);
                }
                result.count = filteredItems.size();
                result.values = filteredItems;
            }
            else
            {
                synchronized(this)
                {
                    result.values = originalList;
                    result.count = originalList.size();
                }
            }
            return result;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            countryList = (ArrayList<Country>)results.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = countryList.size(); i < l; i++)
                add(countryList.get(i));
            notifyDataSetInvalidated();
        }
    }


}
}
*/
