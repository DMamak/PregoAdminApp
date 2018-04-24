package com.damian.pregoadminapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.damian.pregoadminapp.Models.Orders;
import com.damian.pregoadminapp.Models.Requests;
import com.damian.pregoadminapp.R;

import java.util.List;

public class customerAdapterView extends RecyclerView.Adapter<customerAdapterView.myViewHolder> {
    private Context context;
    private List<Requests> requestsList;
    customItemClickListner listner;

    public customerAdapterView(List<Requests> ordersList,Context context,customItemClickListner listner){
        this.requestsList = ordersList;
        this.context=context;
        this.listner = listner;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_card_view,parent,false);
        final customerAdapterView.myViewHolder myViewHolder = new customerAdapterView.myViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                listner.onItemClick(view,myViewHolder.getPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Requests request = requestsList.get(position);
        String statusString = "";
        if(request.getStatus().equals("0")){
            statusString = "Placed";
        }else if (request.getStatus().equals("1")){
            statusString = "Received";
        }else if (request.getStatus().equals("2")){
            statusString="On Its Way!";
        }else{
            statusString="Delivered";
        }
        String name="";
        String pizzaName;
        String pizzaQuantity;

        for(int i=0;i<request.getFoods().size();i++){
            pizzaName = request.getFoods().get(i).getProductName();
            pizzaQuantity = request.getFoods().get(i).getQuantity();
            name = name + pizzaName + " " + "Quantity: " + pizzaQuantity + "\n";
        }

        holder.name.setText(request.getName());
        holder.address.setText(request.getAddress());
        holder.phone.setText(request.getPhone());
        holder.status.setText(statusString);
        holder.pizza.setText(name);

    }

    @Override
    public int getItemCount() {
        return requestsList.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView address;
        public TextView pizza;
        public TextView phone;
        public TextView status;


        public myViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.customerName);
            address=itemView.findViewById(R.id.customerAddress);
            pizza=itemView.findViewById(R.id.customerFoods);
            phone = itemView.findViewById(R.id.customerPhone);
            status=itemView.findViewById(R.id.customerStatus);

        }
    }


}
