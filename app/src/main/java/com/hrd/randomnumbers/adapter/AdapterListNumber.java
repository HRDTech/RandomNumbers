package com.hrd.randomnumbers.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrd.randomnumbers.R;

import java.util.ArrayList;

public class AdapterListNumber extends RecyclerView.Adapter <AdapterListNumber.NumberListViewHolder> {
    private ArrayList<String> tempArrayNumber;

    public AdapterListNumber(ArrayList<String> tempArrayNumber) {
        this.tempArrayNumber = tempArrayNumber;
    }

    @NonNull
    @Override
    public NumberListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View theView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_list, viewGroup, false);
        NumberListViewHolder viewHolder = new NumberListViewHolder(theView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberListViewHolder numberListViewHolder, int i) {
        numberListViewHolder.textNumber.setText(tempArrayNumber.get(i));
    }

    @Override
    public int getItemCount() {
        return tempArrayNumber.size();
    }

    public class NumberListViewHolder extends RecyclerView.ViewHolder{
        public TextView textNumber;

        public NumberListViewHolder(View itemView) {
            super(itemView);
            textNumber = itemView.findViewById(R.id.textItem);
        }
    }
}
