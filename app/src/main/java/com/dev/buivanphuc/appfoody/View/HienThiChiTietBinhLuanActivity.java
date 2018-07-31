package com.dev.buivanphuc.appfoody.View;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Adapter.AdapterRecyclerViewHinhBinhLuan;
import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;
import com.dev.buivanphuc.appfoody.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HienThiChiTietBinhLuanActivity extends AppCompatActivity {
    CircleImageView circleImageView;
    TextView txtTieuDeBinhLuan, txtNoiDungBinhLuan, txtSoDiem;
    RecyclerView recyclerViewHinhBinhLuan;
    List<Bitmap> bitmapList;
    BinhLuanModel binhLuanModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_binhluan);
        addControls();
        bitmapList = new ArrayList<>();
        binhLuanModel = getIntent().getParcelableExtra("binhluanmodel");

        txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());
        txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        txtSoDiem.setText(binhLuanModel.getChamdiem() + "");
        setHinhAnhBinhLuan(circleImageView, binhLuanModel.getThanhVienModel().getHinhanh());

        for (String linkhinh : binhLuanModel.getHinhAnhBinhLuanList()) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh").child(linkhinh);
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);
                    if (bitmapList.size() == binhLuanModel.getHinhAnhBinhLuanList().size()) {
                        AdapterRecyclerViewHinhBinhLuan adapterRecyclerViewHinhBinhLuan = new AdapterRecyclerViewHinhBinhLuan(HienThiChiTietBinhLuanActivity.this, R.layout.custom_layout_hinhbinhluan, bitmapList, binhLuanModel, true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(HienThiChiTietBinhLuanActivity.this, 2);
                        recyclerViewHinhBinhLuan.setLayoutManager(layoutManager);
                        recyclerViewHinhBinhLuan.setAdapter(adapterRecyclerViewHinhBinhLuan);
                        adapterRecyclerViewHinhBinhLuan.notifyDataSetChanged();
                    }

                }
            });
        }
        Log.d("Ronado",bitmapList.size()+"");

    }

    private void addControls() {
        circleImageView = findViewById(R.id.cicleImageUser);
        txtTieuDeBinhLuan = findViewById(R.id.txtTieudebinhluan);
        txtNoiDungBinhLuan = findViewById(R.id.txtNodungbinhluan);
        txtSoDiem = findViewById(R.id.txtChamDiemBinhLuan);
        recyclerViewHinhBinhLuan = findViewById(R.id.recyclerHinhBinhLuan);
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
