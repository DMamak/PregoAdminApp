package com.damian.pregoadminapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.R;

import java.util.List;

public class pizzaQuantityAdapterView extends RecyclerView.Adapter<pizzaQuantityAdapterView.myViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;

    public pizzaQuantityAdapterView(List<Pizza> pizzaList,Context context){
        this.pizzaList=pizzaList;
        this.context=context;

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_quantity_layout,parent,false);
        final myViewHolder myViewHolder = new myViewHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Pizza pizza = pizzaList.get(position);
        holder.name.setText(pizza.getName());
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public EditText quantity;

        public myViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pizzaQuantityTextView);
            quantity=itemView.findViewById(R.id.pizzaQuantityNumberPIcker);
        }
    }
}
