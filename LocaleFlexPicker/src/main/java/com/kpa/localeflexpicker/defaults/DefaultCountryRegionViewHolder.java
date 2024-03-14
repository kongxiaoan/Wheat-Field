package com.kpa.localeflexpicker.defaults;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.kpa.localeflexpicker.R;


public class DefaultCountryRegionViewHolder extends RecyclerView.ViewHolder {

    public TextView tvName;
    public TextView tvCode;
    public DefaultCountryRegionViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.tv_name);
        tvCode = itemView.findViewById(R.id.tv_code);
    }
}
