package com.dev.buivanphuc.appfoody.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.dev.buivanphuc.appfoody.Adapter.AdapterBinhLuan;
import com.dev.buivanphuc.appfoody.Controller.ChiTietQuanAnController;
import com.dev.buivanphuc.appfoody.Controller.ThucDonController;
import com.dev.buivanphuc.appfoody.Model.QuanAnModel;
import com.dev.buivanphuc.appfoody.Model.ThucDonModel;
import com.dev.buivanphuc.appfoody.Model.TienIchModel;
import com.dev.buivanphuc.appfoody.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ChiTietQuanAnActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    TextView txtTenQuanAn, txtDiaChi, txtThoiGianHoatDong, txtTrangThaiHoatDong, txtTongSoBinhLuan;
    TextView txtTongSoHinhAnh, txtTongSoCheckIn, txtTongSoLuuLai, txtTieuDeToolbar, txtGioiHanGia;
    TextView txtTenWifi, txtMatKhauWifi, txtNgayDangWifi;
    RecyclerView recyclerBinhLuanChiTietQuanAn,recyclerThucDon;
    ImageView imHinhQuanAn, imgPLayTrailer;
    LinearLayout khungTienTich, khungWifi;
    QuanAnModel quanAnModel;
    Toolbar toolbar;
    NestedScrollView nestScrollViewChiTiet;
    GoogleMap googleMap;
    MapFragment mapFragment;
    Button btnBinhLuan;
    View khungTinhNang;
    ChiTietQuanAnController chiTietQuanAnController;
    VideoView videoTrailer;
    ThucDonController thucDonController;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_chitietquanan);
        addControls();
        quanAnModel = getIntent().getParcelableExtra("quanan");
        chiTietQuanAnController = new ChiTietQuanAnController();
        thucDonController = new ThucDonController();
        HienThiChiTietQuanAn();
    }

    private void addControls() {
        txtTenQuanAn = findViewById(R.id.txtTenQuanAn);
        txtTongSoBinhLuan = findViewById(R.id.tongSoBinhLuan);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChiQuanAn);
        txtThoiGianHoatDong = findViewById(R.id.txtThoiGianHoatDong);
        txtTrangThaiHoatDong = findViewById(R.id.txtTrangThaiHoatDong);
        txtTongSoHinhAnh = findViewById(R.id.tongSoHinhAnh);
        txtTongSoCheckIn = findViewById(R.id.tongSoCheckIn);
        txtTongSoLuuLai = findViewById(R.id.tongSoLuuLai);
        imHinhQuanAn = findViewById(R.id.imHinhQuanAn);
        txtTieuDeToolbar = findViewById(R.id.txtTieuDeToolbar);
        txtGioiHanGia = findViewById(R.id.txtGioiHanGia);
        khungTienTich = findViewById(R.id.khungTienTich);
        recyclerBinhLuanChiTietQuanAn = findViewById(R.id.recyclerBinhLuanChiTietQuanAn);
        nestScrollViewChiTiet = findViewById(R.id.nestScrollViewChiTiet);
        txtTenWifi = findViewById(R.id.txtTenWifi);
        txtMatKhauWifi = findViewById(R.id.txtMatKhauWifi);
        txtNgayDangWifi = findViewById(R.id.txtNgayDangWifi);
        khungWifi = findViewById(R.id.khungWifi);
        khungTinhNang = findViewById(R.id.khungTinhNang);
        videoTrailer = findViewById(R.id.videoTrailer);
        imgPLayTrailer = findViewById(R.id.imgPLayTrailer);
        recyclerThucDon = findViewById(R.id.recyclerThucDon);
        setTitle("");
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        nestScrollViewChiTiet.smoothScrollTo(0, 0);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        btnBinhLuan = findViewById(R.id.btnBinhLuan);
        btnBinhLuan.setOnClickListener(this);


    }

    private void HienThiChiTietQuanAn() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String gioHienTai = dateFormat.format(calendar.getTime());
        String gioMoCua = quanAnModel.getGiomocua();
        String gioDongCua = quanAnModel.getGiodongcua();

        try {
            Date dateHienTai = dateFormat.parse(gioHienTai);
            Date dateMoCua = dateFormat.parse(gioMoCua);
            Date dateDongCua = dateFormat.parse(gioDongCua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                // Giờ mở cửa
                txtTrangThaiHoatDong.setText("Đang mở cửa");
                txtTrangThaiHoatDong.setTextColor(Color.RED);
            } else {
                //giờ đóng cưa
                txtTrangThaiHoatDong.setText("Đã đóng cửa");
                txtTrangThaiHoatDong.setTextColor(Color.GREEN);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtTenQuanAn.setText(quanAnModel.getTenquanan());
        txtDiaChi.setText(quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
        txtThoiGianHoatDong.setText(quanAnModel.getGiomocua() + "-" + quanAnModel.getGiodongcua());
        txtTieuDeToolbar.setText(quanAnModel.getTenquanan());
        txtThoiGianHoatDong.setText(gioMoCua + "-" + gioDongCua);
        if (quanAnModel.getHinhanhquanans().size() > 0) {
            txtTongSoHinhAnh.setText(quanAnModel.getHinhanhquanans().size() + "");
        } else {
            txtTongSoHinhAnh.setText("0");
        }
        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            txtTongSoBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
        } else {
            txtTongSoBinhLuan.setText("0");
        }

        if (quanAnModel.getGiatoida() != 0 && quanAnModel.getGiatoithieu() != 0) {
            Log.d("GIA", quanAnModel.getGiatoithieu() + "-" + quanAnModel.getGiatoida());
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String giatoithieu = numberFormat.format(quanAnModel.getGiatoithieu());
            String giatoida = numberFormat.format(quanAnModel.getGiatoida());

            txtGioiHanGia.setText("Giá: " + giatoithieu + "đ" + "-" + giatoida + "đ");
        } else {
            txtGioiHanGia.setVisibility(View.INVISIBLE);
        }
        DowloadHinhTienIch();

        StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanans().get(0));
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imHinhQuanAn.setImageBitmap(bitmap);
            }
        });
        if (quanAnModel.getVideogioithieu() != null) {
            videoTrailer.setVisibility(View.VISIBLE);
            imgPLayTrailer.setVisibility(View.VISIBLE);
            imHinhQuanAn.setVisibility(View.GONE);
            final StorageReference storageVideo = FirebaseStorage.getInstance().getReference("video").child(quanAnModel.getVideogioithieu());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoTrailer.setVideoURI(uri);
                    videoTrailer.seekTo(1);
                }
            });
            imgPLayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoTrailer.start();
                    MediaController mediaController = new MediaController(ChiTietQuanAnActivity.this);
                    videoTrailer.setMediaController(mediaController);

                    imgPLayTrailer.setVisibility(View.GONE);

                }
            });

        } else {

            imgPLayTrailer.setVisibility(View.GONE);
            imHinhQuanAn.setVisibility(View.VISIBLE);
        }

        // Load danh sách bình luận của quán ăn
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerBinhLuanChiTietQuanAn.setLayoutManager(layoutManager);

        AdapterBinhLuan adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, quanAnModel.getBinhLuanModelList());
        recyclerBinhLuanChiTietQuanAn.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();

        // Load Danh sách wifi quán ăn
        chiTietQuanAnController.HienThiDanhSachWifiQuanAn(quanAnModel.getMaquanan(), txtTenWifi, txtMatKhauWifi, txtNgayDangWifi);
        khungWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mkWifi = txtMatKhauWifi.getText().toString();
                Intent iDanhSachWifi = new Intent(ChiTietQuanAnActivity.this, CapNhatDanhSachWifiActivity.class);
                iDanhSachWifi.putExtra("maquanan", quanAnModel.getMaquanan());
                startActivity(iDanhSachWifi);

                if (mkWifi.isEmpty()) {
                    txtTenWifi.setText("K có wifi");
                }
            }
        });
        khungTinhNang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iDanDuong = new Intent(ChiTietQuanAnActivity.this, DanDuongToiQuanAnActivity.class);
                iDanDuong.putExtra("latitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude());
                iDanDuong.putExtra("longitude", quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude());
                startActivity(iDanDuong);

            }
        });
        thucDonController.getDanhSachThucDonQuanAnTheoMa(this,quanAnModel.getMaquanan(),recyclerThucDon );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        double latitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLatitude();
        double longitude = quanAnModel.getChiNhanhQuanAnModelList().get(0).getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(quanAnModel.getTenquanan());

        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);

    }

    private void DowloadHinhTienIch() {
        for (String maTienIch : quanAnModel.getTienich()) {
            DatabaseReference noteTienIch = FirebaseDatabase.getInstance().getReference().child("quanlytienichs").child(maTienIch);
            noteTienIch.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    TienIchModel tienIchModel = dataSnapshot.getValue(TienIchModel.class);
                    StorageReference storageHinhQuanAn = FirebaseStorage.getInstance().getReference().child("hinhtienich").child(tienIchModel.getHinhtienich());
                    long ONE_MEGABYTE = 1024 * 1024;
                    storageHinhQuanAn.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imgTienIch = new ImageView(ChiTietQuanAnActivity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(80, 80);
                            imgTienIch.setPadding(5, 5, 5, 5);
                            imgTienIch.setScaleType(ImageView.ScaleType.FIT_XY);
                            layoutParams.setMargins(10, 10, 10, 10);
                            imgTienIch.setLayoutParams(layoutParams);
                            imgTienIch.setImageBitmap(bitmap);
                            khungTienTich.addView(imgTienIch);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnBinhLuan:
                Intent iBinhLuan = new Intent(this, BinhLuanActivity.class);
                iBinhLuan.putExtra("tenquanan", quanAnModel.getTenquanan());
                iBinhLuan.putExtra("maquanan", quanAnModel.getMaquanan());
                iBinhLuan.putExtra("diachi", quanAnModel.getChiNhanhQuanAnModelList().get(0).getDiachi());
                startActivity(iBinhLuan);
                break;
        }
    }
}
