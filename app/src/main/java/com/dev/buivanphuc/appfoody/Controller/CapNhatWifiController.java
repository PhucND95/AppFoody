package com.dev.buivanphuc.appfoody.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.dev.buivanphuc.appfoody.Adapter.AdapterDanhSachWifi;
import com.dev.buivanphuc.appfoody.Controller.Interfaces.ChiTietQuanAnInterface;
import com.dev.buivanphuc.appfoody.Model.WifiQuanAnModel;
import com.dev.buivanphuc.appfoody.R;

import java.util.ArrayList;
import java.util.List;

public class CapNhatWifiController {

    WifiQuanAnModel wifiQuanAnModel;
    Context context;
    List<WifiQuanAnModel> wifiQuanAnModelList;
    public CapNhatWifiController(Context context)
    {
        this.context = context;
        wifiQuanAnModel = new WifiQuanAnModel();
    }
    public void HienThiDanhSachWifi(String maquan, final RecyclerView recyclerView)
    {
        wifiQuanAnModelList = new ArrayList<>();
        ChiTietQuanAnInterface chiTietQuanAnInterface = new ChiTietQuanAnInterface() {
            @Override
            public void HienThiDanhSachWifi(WifiQuanAnModel wifiQuanAnModel) {
                wifiQuanAnModelList.add(wifiQuanAnModel);
                AdapterDanhSachWifi adapterDanhSachWifi = new AdapterDanhSachWifi(context, R.layout.layout_wifi_chitietquanan,wifiQuanAnModelList);
                recyclerView.setAdapter(adapterDanhSachWifi);
                adapterDanhSachWifi.notifyDataSetChanged();
            }
        };
        wifiQuanAnModel.LayDanhSachWifiQuanAn(maquan,chiTietQuanAnInterface);
    }
    public void ThemWifi(Context context,WifiQuanAnModel wifiQuanAnModel,String maquanan)
    {
        wifiQuanAnModel.ThemWifiQuanAn(context,wifiQuanAnModel,maquanan);
    }
}
