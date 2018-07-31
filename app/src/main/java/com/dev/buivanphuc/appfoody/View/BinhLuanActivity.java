package com.dev.buivanphuc.appfoody.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dev.buivanphuc.appfoody.Adapter.AdapterHienThiHinhBinhLuanDuocChon;
import com.dev.buivanphuc.appfoody.Controller.BinhLuanController;
import com.dev.buivanphuc.appfoody.Model.BinhLuanModel;
import com.dev.buivanphuc.appfoody.R;

import java.util.ArrayList;
import java.util.List;

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener {
    String tenquan;
    String diachi, maquanan;
    TextView txtTenQuanAn, txtDiaChiQuanAn, txtDangBinhLuan, edTieuDeBinhLuan, edNoiDungBinhLuan;
    Toolbar toolbar;
    ImageButton btnChonHinh;
    final int REQUEST_CHONHINHBINHLUAN = 123;
    RecyclerView recyclerChonHinhBinhLuan;
    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    List<String> listHinhDuocChon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);
        tenquan = getIntent().getStringExtra("tenquanan");
        diachi = getIntent().getStringExtra("diachi");
        maquanan = getIntent().getStringExtra("maquanan");
        addControls();


    }

    @SuppressLint("RestrictedApi")
    private void addControls() {
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtDiaChiQuanAn = findViewById(R.id.txtDiaChiQuanAn);
        btnChonHinh = findViewById(R.id.btnChonHinh);
        txtTenQuanAn.setText(tenquan);
        txtDiaChiQuanAn.setText(diachi);
        txtDangBinhLuan = findViewById(R.id.txtDangBinhLuan);
        edTieuDeBinhLuan = findViewById(R.id.edTieuDeBinhLuan);
        recyclerChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        edNoiDungBinhLuan = findViewById(R.id.edNoiDungBinhLuan);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);

        binhLuanController = new BinhLuanController();
        listHinhDuocChon = new ArrayList<>();
        sharedPreferences = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(this, ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan, REQUEST_CHONHINHBINHLUAN);
                break;
            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieuDe = edTieuDeBinhLuan.getText().toString().trim();
                String maUser = sharedPreferences.getString("mauser","");
                String noiDung = edNoiDungBinhLuan.getText().toString().trim();

                binhLuanModel.setTieude(tieuDe);
                binhLuanModel.setNoidung(noiDung);
                binhLuanModel.setLuotthich(0);
                binhLuanModel.setChamdiem(0.0);
                binhLuanModel.setMauser(maUser);


                Log.d("listHinhDuocChon",listHinhDuocChon.size()+"");
                binhLuanController.ThemBinhLuan(maquanan,binhLuanModel,listHinhDuocChon);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINHBINHLUAN) {
            if (resultCode == RESULT_OK) {
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
                AdapterHienThiHinhBinhLuanDuocChon adapter = new AdapterHienThiHinhBinhLuanDuocChon(BinhLuanActivity.this, R.layout.custom_layout_hienthibinhluanduocchon, listHinhDuocChon);
                recyclerChonHinhBinhLuan.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

    }
}
