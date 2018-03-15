package com.damian.pregoadminapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damian.pregoadminapp.Models.Order;
import com.damian.pregoadminapp.R;

import java.util.List;

/**
 * Created by damia on 13/03/2018.
 */

public class orderAdapterView extends RecyclerView.Adapter<orderAdapterView.myViewHolder>{
    private Context context;
    private List<Order> orderList;
    customItemClickListner listner;

    public orderAdapterView( List<Order> orderList,Context context,customItemClickListner listner) {
        this.context = context;
        this.orderList = orderList;
        this.listner=listner;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_card_view,parent,false);
        final orderAdapterView.myViewHolder mViewHolder = new orderAdapterView.myViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listner.onItemClick(view,mViewHolder.getLayoutPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        String details="";
        String name;
        Order order = orderList.get(position);
        holder.id.setText(Long.toString(order.getId()));
        holder.dateReceived.setText(order.getDateReceived());
        holder.timeReceived.setText(order.getTimeReceived());
        for(int i = 0 ; i < order.getPizzas().size();i++){
            name = order.getPizzas().get(i).getName();
            details= details+name+",";
        }
        holder.pizza.setText(details);
        holder.status.setText(order.getStatus());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }


    class myViewHolder extends RecyclerView.ViewHolder{
        public TextView id;
        public TextView dateReceived;
        public TextView pizza;
        public TextView timeReceived;
        public TextView status;


        public myViewHolder(View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.orderIdTextView);
            dateReceived=itemView.findViewById(R.id.dateOrderCardView);
            pizza=itemView.findViewById(R.id.pizzaOrderCardView);
            timeReceived = itemView.findViewById(R.id.orderTimeReceivedTextView);
            status=itemView.findViewById(R.id.statusOrderCardView);

        }
    }




}
