package com.dev.buivanphuc.appfoody.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dev.buivanphuc.appfoody.Controller.Interfaces.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class ThucDonModel {
    String mathucdon;
    String tenthucdon;
    List<MonAnModel> monAnModelList;

    public ThucDonModel() {
    }

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public void getDanhSachThucDonQuanAnTheoMa(final String maQuanAn, final ThucDonInterface thucDonInterface) {
        final DatabaseReference nodeThucDonQuanAn = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maQuanAn);
        nodeThucDonQuanAn.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                final List<ThucDonModel> thucDonModelList = new ArrayList<>();
                for (DataSnapshot valueThucDon : dataSnapshot.getChildren()) {
                    final ThucDonModel thucDonModel = new ThucDonModel();
                    DatabaseReference nodeThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    nodeThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshotThucDon) {
                            String mathucdon = dataSnapshotThucDon.getKey();
                            thucDonModel.setMathucdon(mathucdon);
                            thucDonModel.setTenthucdon(dataSnapshotThucDon.getValue(String .class));

                            List<MonAnModel> monAnModelList = new ArrayList<>();
                            for (DataSnapshot valueMonAn : dataSnapshot.child(mathucdon).getChildren())
                            {
                                MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamon(valueMonAn.getKey());
                                monAnModelList.add(monAnModel);
                            }
                            thucDonModel.setMonAnModelList(monAnModelList);
                            thucDonModelList.add(thucDonModel);
                            thucDonInterface.getThucDonThanhCong(thucDonModelList);

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
