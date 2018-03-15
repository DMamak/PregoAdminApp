package com.damian.pregoadminapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damian.pregoadminapp.Models.Pizza;
import com.damian.pregoadminapp.R;
import java.util.List;

/**
 * Created by damia on 08/03/2018.
 */

public class pizzaAdapterView extends RecyclerView.Adapter<pizzaAdapterView.myViewHolder> {
    private List<Pizza> pizzaList;
    private Context context;
    customItemClickListner listner;

    public pizzaAdapterView(List<Pizza> pizzaList, Context context, customItemClickListner listner) {
        this.pizzaList = pizzaList;
        this.context = context;
        this.listner=listner;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_card_view,parent,false);
        final pizzaAdapterView.myViewHolder mViewHolder = new pizzaAdapterView.myViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onItemClick(view,mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        String details="";
        String name;
        Pizza pizza = pizzaList.get(position);
        holder.price.setText(String.valueOf(pizza.getPrice()));
        holder.size.setText(pizza.getSize());
        holder.name.setText(pizza.getName());

//        ArrayAdapter<Topping> arrayAdapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,pizza.getToppings());
//        holder.toppings.setAdapter(arrayAdapter);
        for(int i = 0 ; i < pizza.getToppings().size();i++){
            name = pizza.getToppings().get(i).getName();
            details= details+name+",";
        }
        holder.toppings.setText(details);
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        public TextView price;
        public TextView size;
        public TextView name;
//        public ListView toppings;
        public TextView toppings;


        public myViewHolder(View itemView) {
            super(itemView);
            price=itemView.findViewById(R.id.pizzaCardViewPrice);
            size=itemView.findViewById(R.id.pizzaCardViewSize);
            name=itemView.findViewById(R.id.namePizzaCardView);
//            toppings=itemView.findViewById(R.id.orderPizzaListView);
            toppings=itemView.findViewById(R.id.toppingsViewPizzaCardView);
        }
    }

}
