package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;
import com.dev.buivanphuc.appfoody.R;
import com.dev.buivanphuc.appfoody.View.HienThiChiTietBinhLuanActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {
    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;


    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList) {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
        RecyclerView recyclerViewHinhBinhLuan;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.cicleImageUser);
            txtTieuDeBinhLuan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNoiDungBinhLuan = itemView.findViewById(R.id.txtNodungbinhluan);
            txtSoDiem = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            recyclerViewHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final List<Bitmap> bitmapList = new ArrayList<>();
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        String noiDungBinhLuan = binhLuanModel.getNoidung();
        if(noiDungBinhLuan.length()>150)
        {
            holder.txtNoiDungBinhLuan.setText(noiDungBinhLuan.substring(0,150)+"...");
        }else {
            holder.txtNoiDungBinhLuan.setText(noiDungBinhLuan);
        }
        holder.txtSoDiem.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(holder.circleImageView, binhLuanModel.getThanhVienModel().getHinhanh());

        for (String linkhinh : binhLuanModel.getHinhAnhBinhLuanList()) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getHinhAnhBinhLuanList().size()) {
                        AdapterRecyclerViewHinhBinhLuan adapterRecyclerViewHinhBinhLuan = new AdapterRecyclerViewHinhBinhLuan(context, R.layout.custom_layout_hinhbinhluan, bitmapList, binhLuanModel,false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerViewHinhBinhLuan);
                        adapterRecyclerViewHinhBinhLuan.notifyDataSetChanged();
                    }


                }
            });
            Log.d("linkhinh",linkhinh);
        }
        holder.txtNoiDungBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietBinhLuan = new Intent(context, HienThiChiTietBinhLuanActivity.class);
                iChiTietBinhLuan.putExtra("binhluanmodel", binhLuanModel);
                context.startActivity(iChiTietBinhLuan);
            }
        });


    }

    @Override
    public int getItemCount() {
        int soBinhLuan = binhLuanModelList.size();
        if (soBinhLuan > 0 && soBinhLuan >= 5) {
            return 5;
        } else {
            return binhLuanModelList.size();
        }

    }

    private void setHinhAnhBinhLuan(final CircleImageView circleImageView, String linkHinh) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien").child(linkHinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }


}
