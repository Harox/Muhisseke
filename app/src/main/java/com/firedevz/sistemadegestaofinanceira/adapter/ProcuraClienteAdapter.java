package com.firedevz.sistemadegestaofinanceira.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.firedevz.sistemadegestaofinanceira.R;
import com.firedevz.sistemadegestaofinanceira.modelo.Clientes;
import com.firedevz.sistemadegestaofinanceira.modelo.Produtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PUDENTE on 2/25/2018.
 */

public class ProcuraClienteAdapter extends ArrayAdapter<Clientes> {

    Context context;
    int resource, textViewResourceId;
    List<Clientes> items, tempItems, suggestions;

    public ProcuraClienteAdapter(Context context, int resource, int textViewResourceId, List<Clientes> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Clientes>(items); // this makes the difference.
        suggestions = new ArrayList<Clientes>();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.linha_categoria, parent, false);
        }
        Clientes clientes = items.get(position);
        if (clientes != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(clientes.getNome());
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
            String str = ((Clientes) resultValue).getNome();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Clientes clientes : tempItems) {
                    if (clientes.getNome().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(clientes);
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
            List<Clientes> filterList = (ArrayList<Clientes>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Clientes clientes : filterList) {
                    add(clientes);
                    notifyDataSetChanged();
                }
            }
        }
    };





        /////FIM
}
