package com.dev.buivanphuc.appfoody.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.dev.buivanphuc.appfoody.Adapter.AdapterThucDon;
import com.dev.buivanphuc.appfoody.Controller.Interfaces.ThucDonInterface;
import com.dev.buivanphuc.appfoody.Model.ThucDonModel;

import java.util.List;

public class ThucDonController {
    ThucDonModel thucDonModel;

    public ThucDonController() {
        thucDonModel = new ThucDonModel();
    }

    public void getDanhSachThucDonQuanAnTheoMa(final Context context, String maQuanAn, final RecyclerView recyclerThucDon) {
        recyclerThucDon.setLayoutManager(new LinearLayoutManager(context));


        ThucDonInterface thucDonInterface = new ThucDonInterface() {
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon adapterThucDon = new AdapterThucDon(context,thucDonModelList);
                recyclerThucDon.setAdapter(adapterThucDon);
                adapterThucDon.notifyDataSetChanged();
            }
        };
        thucDonModel.getDanhSachThucDonQuanAnTheoMa(maQuanAn,thucDonInterface);
    }
}
