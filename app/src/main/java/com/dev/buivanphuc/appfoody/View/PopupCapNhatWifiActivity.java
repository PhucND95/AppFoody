package com.dev.buivanphuc.appfoody.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.buivanphuc.appfoody.Controller.CapNhatWifiController;
import com.dev.buivanphuc.appfoody.Model.WifiQuanAnModel;
import com.dev.buivanphuc.appfoody.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PopupCapNhatWifiActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edTenWifi, edNhapKhauWifi;
    Button btnDongYCatNhatWifi;

    CapNhatWifiController capNhatWifiController;
    String maquanan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_catnhatwifi);

        maquanan = getIntent().getStringExtra("maquanan");
        setTitle("Thêm wifi");
        edTenWifi = findViewById(R.id.edTenWifi);
        edNhapKhauWifi = findViewById(R.id.edNhapKhauWifi);
        btnDongYCatNhatWifi = findViewById(R.id.btnDongYCatNhatWifi);

        btnDongYCatNhatWifi.setOnClickListener(this);
        capNhatWifiController = new CapNhatWifiController(this);

    }

    @Override
    public void onClick(View v) {
        String tenWifi = edTenWifi.getText().toString().trim();
        String matKhau = edNhapKhauWifi.getText().toString().trim();

        if (!tenWifi.isEmpty() && !matKhau.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String ngayDang = simpleDateFormat.format(calendar.getTime());

            WifiQuanAnModel wifiQuanAnModel = new WifiQuanAnModel();

            wifiQuanAnModel.setTen(tenWifi);
            wifiQuanAnModel.setMatkhau(matKhau);
            wifiQuanAnModel.setNgaydang(ngayDang);

            capNhatWifiController.ThemWifi(this, wifiQuanAnModel,maquanan);
            finish();

        } else {
            Toast.makeText(this, "wifi k được để trống", Toast.LENGTH_LONG).show();
        }
    }
}
