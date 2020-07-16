package com.example.proyectofinalmoviles.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectofinalmoviles.R;
import com.example.proyectofinalmoviles.logicaNegocio.Aeropuerto;

import java.util.List;

public class AeropuertoAdapter extends RecyclerView.Adapter<AeropuertoAdapter.MyViewHolder>{

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;

        MyViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName = (TextView)itemView.findViewById(R.id.titleFirstLbl);
            personAge = (TextView)itemView.findViewById(R.id.titleSecLbl);

        }
    }

    private List<Aeropuerto> aeropuertos;

    public AeropuertoAdapter(List<Aeropuerto> a){
        this.aeropuertos = a;
    }


    @Override
    public int getItemCount() {
        return aeropuertos.size();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_row, parent, false);

        return new MyViewHolder(itemView);
    }



    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.personName.setText(aeropuertos.get(i).getCódigo()+"  "+aeropuertos.get(i).getNombre()+"  "+aeropuertos.get(i).getTeléfono());
        personViewHolder.personAge.setText(aeropuertos.get(i).getDirección()+"  "+aeropuertos.get(i).getCorreo());
     }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}