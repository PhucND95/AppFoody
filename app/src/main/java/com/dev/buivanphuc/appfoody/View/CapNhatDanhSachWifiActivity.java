package com.dev.buivanphuc.appfoody.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dev.buivanphuc.appfoody.Controller.CapNhatWifiController;
import com.dev.buivanphuc.appfoody.R;

public class CapNhatDanhSachWifiActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerDanhSachWifi;
    Button btnCapNhatWifi;
    CapNhatWifiController capNhatWifiController;
    String maquanan;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_capnhatdanhsachwifi);
        btnCapNhatWifi = findViewById(R.id.btnCapNhatWifi);
        recyclerDanhSachWifi = findViewById(R.id.recyclerDanhSachWifi);

        maquanan = getIntent().getStringExtra("maquanan");

        capNhatWifiController = new CapNhatWifiController(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDanhSachWifi.setLayoutManager(layoutManager);
        capNhatWifiController.HienThiDanhSachWifi(maquanan,recyclerDanhSachWifi);

        btnCapNhatWifi.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatWifiController = new CapNhatWifiController(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDanhSachWifi.setLayoutManager(layoutManager);
        capNhatWifiController.HienThiDanhSachWifi(maquanan,recyclerDanhSachWifi);
    }

    @Override
    public void onClick(View v) {
        Intent iPopup = new Intent(this,PopupCapNhatWifiActivity.class);
        iPopup.putExtra("maquanan",maquanan);
        startActivity(iPopup);
    }
}
