package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dev.buivanphuc.appfoody.R;

import java.util.List;

public class AdapterHienThiHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHienThiHinhBinhLuanDuocChon.ViewHolder> {
    Context context;
    int resource;
    List<String> list;

    public AdapterHienThiHinhBinhLuanDuocChon(Context context, int resource, List<String> list) {
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgChonHinhBinhLuan,imgeDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            imgeDelete = itemView.findViewById(R.id.imgeDelete);
            imgChonHinhBinhLuan = itemView.findViewById(R.id.imgChonHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String duongDan = list.get(position);
        Glide.with(context).load(duongDan).into(holder.imgChonHinhBinhLuan);
        holder.imgeDelete.setTag(position);

        holder.imgeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
