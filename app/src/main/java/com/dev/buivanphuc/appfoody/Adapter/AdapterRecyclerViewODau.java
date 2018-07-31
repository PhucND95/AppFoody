package com.dev.buivanphuc.appfoody.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;
import com.dev.buivanphuc.appfoody.Model.ChiNhanhQuanAnModel;
import com.dev.buivanphuc.appfoody.Model.QuanAnModel;
import com.dev.buivanphuc.appfoody.R;
import com.dev.buivanphuc.appfoody.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterRecyclerViewODau extends RecyclerView.Adapter<AdapterRecyclerViewODau.ViewHolderODau> {
    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecyclerViewODau(List<QuanAnModel> quanAnModelList, int resource, Context context) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolderODau extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnODau, txtChamDiemBinhLuan, txtChamDiemBinhLuan2, txtTongBinhLuan, txtTongHinhAnhBinhLuan;
        TextView txtKhoangCach;
        Button btnDatMonODau;
        ImageView imageHinhQuanAnOdau;
        CircleImageView cicleImageUser, cicleImageUser2;
        TextView txtTieudebinhluan, txtTieudebinhluan2, txtNodungbinhluan, txtNodungbinhluan2, txtDiaChiQuanAnODau, txtDiemTrungBinhQuanAn;
        LinearLayout containerBinhLuan, containerBinhLuan2;
        CardView cardViewOdau;
        public ViewHolderODau(View itemView) {
            super(itemView);

            txtTenQuanAnODau = itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMonODau = itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);
            cicleImageUser = itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = itemView.findViewById(R.id.cicleImageUser2);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtTieudebinhluan2 = itemView.findViewById(R.id.txtTieudebinhluan2);
            txtNodungbinhluan = itemView.findViewById(R.id.txtNodungbinhluan);
            txtNodungbinhluan2 = itemView.findViewById(R.id.txtNodungbinhluan2);
            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = itemView.findViewById(R.id.txtChamDiemBinhLuan2);
            txtTongBinhLuan = itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhAnhBinhLuan = itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtDiaChiQuanAnODau = itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtDiemTrungBinhQuanAn = itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtKhoangCach = itemView.findViewById(R.id.txtKhoangCachQuanAnODau);
            cardViewOdau = itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerViewODau.ViewHolderODau onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolderODau viewHolderODau = new ViewHolderODau(view);
        return viewHolderODau;
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerViewODau.ViewHolderODau holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);

        holder.txtTenQuanAnODau.setText(quanAnModel.getTenquanan());

        if (quanAnModel.isGiaohang()) {
            holder.btnDatMonODau.setVisibility(View.VISIBLE);
        } else {
            holder.btnDatMonODau.setVisibility(View.GONE);
        }
        if (quanAnModel.getBitmapList().size() > 0) {
            holder.imageHinhQuanAnOdau.setImageBitmap(quanAnModel.getBitmapList().get(0));
        }
        // Lấy danh sách bình luận quán ăn
        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            holder.containerBinhLuan.setVisibility(View.VISIBLE);
            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
            BinhLuanModel binhLuanModel = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
            if(binhLuanModel.getNoidung().length()>100)
            {
                holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung().substring(0,100)+"...");
            }else {
                holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
            }

            setHinhAnhBinhLuan(holder.cicleImageUser, binhLuanModel.getThanhVienModel().getHinhanh());
            holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");
            if (quanAnModel.getBinhLuanModelList().size() >= 2) {
                holder.containerBinhLuan2.setVisibility(View.VISIBLE);
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                if(binhLuanModel2.getNoidung().length()>100)
                {
                    holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung().substring(0,100)+"...");
                }else {
                    holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
                }
                holder.txtChamDiemBinhLuan2.setText(binhLuanModel2.getChamdiem() + "");
                setHinhAnhBinhLuan(holder.cicleImageUser2, binhLuanModel2.getThanhVienModel().getHinhanh());
            }
            int tongsobinhluan = 0;
            double tongDiem = 0;
            // Tính tổng điểm trung bình của bình luận và đếm tổng số hình bình luận
            for (BinhLuanModel binhLuanModel1 : quanAnModel.getBinhLuanModelList()) {
                tongsobinhluan += binhLuanModel1.getHinhAnhBinhLuanList().size();
                tongDiem += binhLuanModel1.getChamdiem();
            }
            double diemTrungBinh = tongDiem / quanAnModel.getBinhLuanModelList().size();
            holder.txtDiemTrungBinhQuanAn.setText(String.format("%.1f", diemTrungBinh));
            if (tongsobinhluan > 0) {
                holder.txtTongHinhAnhBinhLuan.setText(tongsobinhluan + "");
            }

        } else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
            holder.txtTongBinhLuan.setText("0");
        }
        // Lấy chi nhánh quán ăn và hiển thị địa chỉ và khoảng cách
        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
                if (chiNhanhQuanAnModelTam.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()) {
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }
            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());
            holder.txtKhoangCach.setText(String.format("%.1f", chiNhanhQuanAnModelTam.getKhoangcach()) + "km");
        }
        holder.cardViewOdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan",quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });
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

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }
}
