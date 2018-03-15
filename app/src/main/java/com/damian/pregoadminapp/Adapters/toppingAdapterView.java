package com.damian.pregoadminapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.damian.pregoadminapp.R;

import com.damian.pregoadminapp.Models.Topping;

import java.util.List;

/**
 * Created by damia on 08/03/2018.
 */

public class toppingAdapterView extends  RecyclerView.Adapter<toppingAdapterView.myViewHolder>{

    private List<Topping> toppingsList;
    private Context context;
    customItemClickListner listner;

    public toppingAdapterView(List<Topping> toppingsList, Context context,customItemClickListner listner) {
        this.toppingsList = toppingsList;
        this.context = context;
        this.listner=listner;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.topping_card_view,parent,false);
        final myViewHolder mViewHolder = new myViewHolder(v);
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
        Topping topping = toppingsList.get(position);
        holder.name.setText(topping.getName());


    }

    @Override
    public int getItemCount() {
        return toppingsList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder{
        public TextView name;

        public myViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.toppingNameTextView);
        }
    }


}
