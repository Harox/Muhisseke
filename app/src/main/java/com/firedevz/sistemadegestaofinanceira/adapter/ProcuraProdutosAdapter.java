package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Produto;

import java.util.ArrayList;
import java.util.List;


public class ProcuraProdutosAdapter extends ArrayAdapter<Produto> {

    Context context;
    int resource, textViewResourceId;
    List<Produto> items, tempItems, suggestions;


    public ProcuraProdutosAdapter(Context context, int resource, int textViewResourceId, List<Produto> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Produto>(items); // this makes the difference.
        suggestions = new ArrayList<Produto>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_categoria, parent, false);
        }
        Produto pmodel = items.get(position);
        if (pmodel != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(pmodel.getNome());
        }
        return view;
    }


    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Produto) resultValue).getNome();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Produto pmodel : tempItems) {
                    if (pmodel.getNome().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(pmodel);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Produto> filterList = (ArrayList<Produto>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Produto pmodel : filterList) {
                    add(pmodel);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
