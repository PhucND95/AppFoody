package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;
import com.dev.buivanphuc.appfoody.R;
import com.dev.buivanphuc.appfoody.View.HienThiChiTietBinhLuanActivity;

import java.util.List;

public class AdapterRecyclerViewHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerViewHinhBinhLuan.ViewHolderHinhBinhLuan> {
    Context context;
    int layout;
    List<Bitmap> listHinh;
    BinhLuanModel binhLuanModel;
    boolean isChiTietBinhLuan;

    public AdapterRecyclerViewHinhBinhLuan(Context context, int layout, List<Bitmap> listHinh, BinhLuanModel binhLuanModel,boolean isChiTietBinhLuan) {
        this.context = context;
        this.layout = layout;
        this.listHinh = listHinh;
        this.binhLuanModel = binhLuanModel;
        this.isChiTietBinhLuan = isChiTietBinhLuan;

    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imgHinhBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan,khungItemBinhLuan;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);
            imgHinhBinhLuan = itemView.findViewById(R.id.imageBinhLuan);
            txtSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan = itemView.findViewById(R.id.khungSoHinhBinhLuan);
            khungItemBinhLuan = itemView.findViewById(R.id.khungItemBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderHinhBinhLuan holder, final int position) {
        holder.imgHinhBinhLuan.setImageBitmap(listHinh.get(position));

        if(!isChiTietBinhLuan)
        {
            if (position == 3) {
                int soHinhConLai = listHinh.size() - 4;
                if (soHinhConLai > 0) {
                    holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                    holder.txtSoHinhBinhLuan.setText("+" + soHinhConLai + "");

                    holder.imgHinhBinhLuan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent iChiTietBinhLuan = new Intent(context, HienThiChiTietBinhLuanActivity.class);
                            iChiTietBinhLuan.putExtra("binhluanmodel", binhLuanModel);
                            context.startActivity(iChiTietBinhLuan);
                        }
                    });
                }

            }
        }
    }

    @Override
    public int getItemCount() {
       if(!isChiTietBinhLuan)
       {
           if(listHinh.size()<4)
           {
               return listHinh.size();
           }else {
               return 4;
           }
       }else {
           return listHinh.size();
       }
    }


}
