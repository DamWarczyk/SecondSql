package com.example.secondsql;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.secondsql.entities.Phone;
import com.example.secondsql.databinding.FragmentRowItemBinding;

import java.util.List;

public class MyPhoneRecyclerViewAdapter extends RecyclerView.Adapter<MyPhoneRecyclerViewAdapter.ViewHolder> {

    private List<Phone> phones;
    private final LayoutInflater layoutInflater;

    public MyPhoneRecyclerViewAdapter(Context context) {
        this.layoutInflater = LayoutInflater.from(context);
        this.phones = null;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mIdView.setText(phones.get(position).getModel());
        holder.mContentView.setText(phones.get(position).getProducer());
    }

    @Override
    public int getItemCount() {
        if (phones != null){
            return phones.size();
        }
        else {
            return 0;
        }
    }

    public void setPhonesList(List<Phone> phones){
        this.phones = phones;
        notifyDataSetChanged();
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public LayoutInflater getLayoutInflater() {
        return layoutInflater;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;


        public ViewHolder(FragmentRowItemBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}