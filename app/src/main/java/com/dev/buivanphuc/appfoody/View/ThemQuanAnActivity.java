package com.dev.buivanphuc.appfoody.View;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.dev.buivanphuc.appfoody.Model.ThucDonModel;
import com.dev.buivanphuc.appfoody.Model.TienIchModel;
import com.dev.buivanphuc.appfoody.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    Button btnGioMoCua, btnGioDongCua;
    String gioMoCua, gioDongCua;
    Spinner spinnerKhuVuc, spinnerThucDon;
    LinearLayout khungTienTich;

    List<ThucDonModel> thucDonModelList;
    List<String> khuVucList, thucDonList;
    ArrayAdapter<String> adapterKhuVuc, adapterThucDon;
    List<String > selectTienIch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);

        btnGioDongCua = findViewById(R.id.btnGioDongCua);
        btnGioMoCua = findViewById(R.id.btnGioMoCua);
        spinnerKhuVuc = findViewById(R.id.spinnerKhuVuc);
        spinnerThucDon = findViewById(R.id.spinnerThucDon);
        khungTienTich = findViewById(R.id.khungTienTich);
        khuVucList = new ArrayList<>();
        thucDonModelList = new ArrayList<>();
        thucDonList = new ArrayList<>();
        selectTienIch = new ArrayList<>();

        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        LayDanhSachKhuVuc();
        LayDanhSachThucDon();
        LayDanhSachTienIch();
        adapterKhuVuc = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, khuVucList);
        spinnerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();

        adapterThucDon = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, thucDonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();

        spinnerKhuVuc.setOnItemSelectedListener(this);
        spinnerThucDon.setOnItemSelectedListener(this);
    }

    private void LayDanhSachTienIch() {
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String maTienIch = snapshot.getKey();
                    TienIchModel tienIchModel = snapshot.getValue(TienIchModel.class);
                    tienIchModel.setMatienich(maTienIch);

                    final CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    khungTienTich.addView(checkBox);
                    checkBox.setTag(maTienIch);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String maTienIch = (String) buttonView.getTag();
                            if(checkBox.isChecked())
                            {
                                selectTienIch.add(maTienIch);
                            }else {
                                selectTienIch.remove(maTienIch);
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachThucDon() {
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ThucDonModel thucDonModel = new ThucDonModel();
                    String key = snapshot.getKey();
                    String value = snapshot.getValue(String.class);

                    thucDonModel.setTenthucdon(value);
                    thucDonModel.setMathucdon(key);

                    thucDonModelList.add(thucDonModel);
                    thucDonList.add(value);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachKhuVuc() {
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String tenKhuVuc = snapshot.getKey();
                    khuVucList.add(tenKhuVuc);
                }
                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);
        switch (id) {
            case R.id.btnGioDongCua:
                TimePickerDialog timePickerDialog = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioDongCua = hourOfDay + ":" + minute;
                        btnGioDongCua.setText(gioDongCua);
                    }
                }, gio, phut, true);
                timePickerDialog.show();
                break;
            case R.id.btnGioMoCua:

                TimePickerDialog timePickerDialogMoCua = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        gioMoCua = hourOfDay + ":" + minute;
                        btnGioMoCua.setText(gioMoCua);
                    }
                }, gio, phut, true);
                timePickerDialogMoCua.show();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinnerKhuVuc:
                break;
            case R.id.spinnerThucDon:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
