package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dev.buivanphuc.appfoody.Model.ChonHinhBinhLuanModel;
import com.dev.buivanphuc.appfoody.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolderChonHinhBinhLuan> {
    Context context;
    int resource;
    List<ChonHinhBinhLuanModel> listDuongDan;

    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinhBinhLuanModel> listDuongDan) {
        this.context = context;
        this.resource = resource;
        this.listDuongDan = listDuongDan;
    }

    public class ViewHolderChonHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imgChonHinhBinhLuan;
        CheckBox checkBoxChonHinhBinhLuan;

        public ViewHolderChonHinhBinhLuan(View itemView) {
            super(itemView);
            imgChonHinhBinhLuan = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBoxChonHinhBinhLuan = itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);
            //this.setIsRecyclable(false);
        }
    }

    @NonNull
    @Override
    public AdapterChonHinhBinhLuan.ViewHolderChonHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderChonHinhBinhLuan viewHolderChonHinhBinhLuan = new ViewHolderChonHinhBinhLuan(view);
        return viewHolderChonHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChonHinhBinhLuan.ViewHolderChonHinhBinhLuan holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);

        Glide.with(context).load(chonHinhBinhLuanModel.getDuongdan()).into(holder.imgChonHinhBinhLuan);
        holder.checkBoxChonHinhBinhLuan.setChecked(chonHinhBinhLuanModel.isCheck());
        holder.checkBoxChonHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                chonHinhBinhLuanModel.setCheck(checkBox.isChecked());
                listDuongDan.get(position).setCheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDuongDan.size();
    }


}
